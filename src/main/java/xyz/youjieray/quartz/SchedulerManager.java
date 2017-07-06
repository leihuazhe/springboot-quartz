package xyz.youjieray.quartz;

import org.apache.commons.lang3.StringUtils;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import xyz.common.Constans;
import xyz.youjieray.model.TaskModel;

import javax.annotation.PostConstruct;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * 任务管理器
 *
 * @author leihz
 * @date 2017/7/5 16:03
 */
@Component
public class SchedulerManager {

        private Logger logger = LoggerFactory.getLogger(getClass());
        @Autowired
        @Qualifier("schedulerFactoryBean")
        private Scheduler scheduler;

        @Autowired
        private List<JobListener> jobListeners;
        @Autowired
        private List<TriggerListener> triggerListeners;

        public void setScheduler(Scheduler scheduler) {
            this.scheduler = scheduler;
        }

        public void setJobListeners(List<JobListener> jobListeners) {
            this.jobListeners = jobListeners;
        }

        public void setTriggerListeners(List<TriggerListener> triggerListeners) {
            this.triggerListeners = triggerListeners;
        }

        @PostConstruct
        public void postConstruct() throws Exception {
            if (this.jobListeners != null && this.jobListeners.size() > 0) {
                if (logger.isInfoEnabled()) {
                    logger.info("Initing quartz scheduler[" + this.scheduler.getSchedulerName() + "] , add Joblistener size ：" + this.jobListeners.size());
                }
                for (JobListener jobListener : this.jobListeners) {
                    if (logger.isInfoEnabled()) {
                        logger.info("Add JobListener : " + jobListener.getName());
                    }
                    //全局注册,所有Job都会起作用
                    this.scheduler.getListenerManager().addJobListener(jobListener);
                }

                if (this.triggerListeners != null && this.triggerListeners.size() > 0) {
                    if (logger.isInfoEnabled()) {
                        logger.info("Initing quartz scheduler[" + this.scheduler.getSchedulerName() + "] , add Triggerlistener size ：" + this.triggerListeners.size());
                    }
                    for (TriggerListener triggerListener : this.triggerListeners) {
                        if (logger.isInfoEnabled()) {
                            logger.info("Add Triggerlistener : " + triggerListener.getName());
                        }
                        this.scheduler.getListenerManager().addTriggerListener(triggerListener);
                    }
                }

            }
        }
            /**
             * 查看所有   任务
             */
            public List<TaskModel> getAllJobDetail () {
                List<TaskModel> result = new LinkedList<TaskModel>();
                try {
                    GroupMatcher<JobKey> matcher = GroupMatcher.jobGroupContains("");
                    Set<JobKey> jobKeys = scheduler.getJobKeys(matcher);
                    for (JobKey jobKey : jobKeys) {
                        JobDetail jobDetail = scheduler.getJobDetail(jobKey);
                        List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
                        for (Trigger trigger : triggers) {
                            JobDataMap jobDataMap = trigger.getJobDataMap();
                            TaskModel taskScheduled = (TaskModel) jobDataMap.get(Constans.QUARTZ_TEST);

                            //  任务状态 0禁用 1启用 2删除
                            Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
                            taskScheduled.setTaskStatus(triggerState.name());

                            // 最后一次执行时间
                            taskScheduled.setPreviousFireTime(trigger.getPreviousFireTime());
                            // 下次执行时间
                            taskScheduled.setNextFireTime(trigger.getNextFireTime());

                            String jobClass = jobDetail.getJobClass().getSimpleName();
                            taskScheduled.setJobClass(jobClass);
                            result.add(taskScheduled);
                        }
                    }
                } catch (Exception e) {
                    logger.error("Try to load All JobDetail cause error : ", e);
                }
                return result;
            }


        public JobDetail getJobDetailByTriggerName(Trigger trigger) {
            try {
                return this.scheduler.getJobDetail(trigger.getJobKey());
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
            return null;
        }

        /**
         * 创建更新  任务
         * <p>
         * param taskScheduled
         * return
         * throws Exception
         */
        public boolean createOrUpdateTask(TaskModel taskScheduled) throws Exception {
            String jobGroup = taskScheduled.getTaskGroup();
            if (StringUtils.isEmpty(jobGroup)) {
                jobGroup = "undefined_job_group";
            }
            String jobName = taskScheduled.getTaskName();
            if (StringUtils.isEmpty(jobName)) {
                jobName = "undefined_job_name_" + String.valueOf(System.currentTimeMillis());
            }

            JobDataMap jobDataMap = new JobDataMap();

            //设置任务执行类
            Object  targetObject = null;
            try {
                targetObject = Class.forName(taskScheduled.getTaskTarget()).newInstance();
            } catch (InstantiationException e) {
                logger.error("任务调度类映射错误", e);
                throw new InstantiationException(e.toString());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                logger.error("任务调度类映射错误", e);
                throw new IllegalAccessException(e.toString());
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                logger.error("没有找到任务调度类【 " + taskScheduled.getTaskTarget() + "】", e);
                throw new ClassNotFoundException(e.toString());
            }
            taskScheduled.setTargetObject(targetObject);


            taskScheduled.setJobClass(TaskAdapter.class.getSimpleName());
            //todo important this is setting the jobDataMap group
            jobDataMap.put(Constans.QUARTZ_TEST, taskScheduled);
           //日志 //jobDataMap.put("1" ,getTaskLog(taskScheduled));

            JobBuilder jobBuilder = null;
            //设置任务调度适配器
            jobBuilder = JobBuilder.newJob(TaskAdapter.class);

            if (jobBuilder != null) {
                JobDetail jobDetail = jobBuilder.withIdentity(jobName, jobGroup).withDescription(taskScheduled.getTaskDesc()).storeDurably(true).usingJobData(jobDataMap).build();
                Trigger trigger = TriggerBuilder.newTrigger().withSchedule(CronScheduleBuilder.cronSchedule(taskScheduled.getTaskCron()))
                        .withIdentity(jobName, jobGroup).withDescription(taskScheduled.getTaskDesc()).forJob(jobDetail)
                        .usingJobData(jobDataMap).build();

                try {
                    JobDetail detail = scheduler.getJobDetail(new JobKey(jobName, jobGroup));
                    if (detail == null) {
                        //添加
                        scheduler.scheduleJob(jobDetail, trigger);
                    } else {
                        //更新
                        scheduler.addJob(jobDetail, true);
                        scheduler.rescheduleJob(new TriggerKey(jobName, jobGroup), trigger);
                    }
                    return true;
                } catch (SchedulerException e) {
                    logger.error("SchedulerManagerException", e);
                    throw new SchedulerException(e);
                }
            }
            return false;
        }

        /**
         * 暂停所有触发器
         * return
         */
        public void pauseAllTrigger() throws Exception {
            try {
                scheduler.standby();
            } catch (SchedulerException e) {
                logger.error("SchedulerManagerException", e);
                throw new SchedulerException(e);
            }
        }

        /**
         * 启动所有触发器
         * return
         */
        public void startAllTrigger() throws Exception {
            try {
                if (scheduler.isInStandbyMode()) {
                    scheduler.start();
                }
            } catch (SchedulerException e) {
                logger.error("SchedulerManagerException", e);
                throw new SchedulerException(e);
            }
        }

        // 暂停任务
        public void stopJob(TaskModel scheduleJob) throws Exception {
            try {
                //JobKey jobKey = JobKey.jobKey(scheduleJob.getTaskName(), scheduleJob.getTaskGroup());
                JobKey jobKey = getJobKey(scheduleJob.getTaskName(), scheduleJob.getTaskGroup());
                scheduler.pauseJob(jobKey);
            } catch (Exception e) {
                logger.error("Try to stop Job cause error : ", e);
                throw new Exception(e);
            }
        }

        // 启动任务
        public void resumeJob(TaskModel scheduleJob) throws Exception {
            try {
                //JobKey jobKey = JobKey.jobKey(scheduleJob.getTaskName(), scheduleJob.getTaskGroup());
                JobKey jobKey = getJobKey(scheduleJob.getTaskName(), scheduleJob.getTaskGroup());
                scheduler.resumeJob(jobKey);
            } catch (Exception e) {
                logger.error("Try to resume Job cause error : ", e);
                throw new Exception(e);
            }
        }

        // 执行任务
        public void runJob(TaskModel scheduleJob) throws Exception {
            try {
                //JobKey jobKey = JobKey.jobKey(scheduleJob.getTaskName(), scheduleJob.getTaskGroup());
                JobKey jobKey = getJobKey(scheduleJob.getTaskName(), scheduleJob.getTaskGroup());
                scheduler.triggerJob(jobKey);
            } catch (Exception e) {
                logger.error("Try to resume Job cause error : ", e);
                throw new Exception(e);
            }
        }

        // 删除任务
        public void delJob(TaskModel scheduleJob) throws Exception {
            try {
                //JobKey jobKey = JobKey.jobKey(scheduleJob.getTaskName(), scheduleJob.getTaskGroup());
                //TriggerKey triggerKey = TriggerKey.triggerKey(scheduleJob.getTaskName(), scheduleJob.getTaskGroup());
                JobKey jobKey = getJobKey(scheduleJob.getTaskName(), scheduleJob.getTaskGroup());
                TriggerKey triggerKey = getTriggerKey(scheduleJob.getTaskName(), scheduleJob.getTaskGroup());
                scheduler.pauseTrigger(triggerKey);// 停止触发器
                scheduler.unscheduleJob(triggerKey);// 移除触发器
                scheduler.deleteJob(jobKey);// 删除任务
            } catch (Exception e) {
                logger.error("Try to resume Job cause error : ", e);
                throw new Exception(e);
            }
        }


        private JobDataMap getJobDataMap(Scheduler scheduler, TaskModel taskScheduled) throws Exception {
            JobDataMap jobDataMap = new JobDataMap();
            // 任务名称
            jobDataMap.put("taskName", taskScheduled.getTaskName());
            //任务名称
            jobDataMap.put("taskAliasName", taskScheduled.getTaskAliasName());
            // 任务分组
            jobDataMap.put("taskGroup", taskScheduled.getTaskGroup());
            //任务状态 0禁用 1启用 2删除
            jobDataMap.put("taskStatus", taskScheduled.getTaskStatus());
            // 任务运行时间表达式
            jobDataMap.put("taskCron", taskScheduled.getTaskCron());
            // 最后一次执行时间
            // jobDataMap.put("previousFireTime", getCronTrigger(scheduler, taskScheduled.getTaskName(), taskScheduled.getTaskGroup()).getPreviousFireTime());
            jobDataMap.put("previousFireTime", "");
            //下次执行时间
            //jobDataMap.put("nextFireTime", getCronTrigger(scheduler, taskScheduled.getTaskName(), taskScheduled.getTaskGroup()).getNextFireTime());
            jobDataMap.put("nextFireTime", "");
            // 任务描述
            jobDataMap.put("taskDesc", taskScheduled.getTaskDesc());
            // 任务类型(是否阻塞)
            jobDataMap.put("jobType", taskScheduled.getJobType());
            // 本地任务/dubbo任务
            jobDataMap.put("taskType", taskScheduled.getTaskType());
            // 运行系统(dubbo任务必须)
            jobDataMap.put("targetSystem", taskScheduled.getTargetSystem());

            // 任务对象
            //根据任务配置对象，创建任务类的对象实例，采用反射。
            //TaskExecute taskExecute = null;
            Object taskExecute = null;
            try {
                taskExecute = Class.forName(taskScheduled.getTaskTarget()).newInstance();
            } catch (InstantiationException e) {
                logger.error("任务调度类映射错误", e);
                throw new InstantiationException(e.toString());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                logger.error("任务调度类映射错误", e);
                throw new IllegalAccessException(e.toString());
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                logger.error("没有找到任务调度类【 " + taskScheduled.getTargetObject() + "】", e);
                throw new ClassNotFoundException(e.toString());
            }
            jobDataMap.put("targetObject", taskExecute);


            // 任务方法
            jobDataMap.put("targetMethod", taskScheduled.getTaskMethod());

            //任务执行需要的参数
            //jobDataMap.put("taskData", taskScheduled.getTaskData());

            return jobDataMap;
        }

        /**
         * 获取触发器key
         * <p>
         * param jobName
         * param jobGroup
         * return
         */
        private static TriggerKey getTriggerKey(String jobName, String jobGroup) throws Exception {
            return TriggerKey.triggerKey(jobName, jobGroup);
        }

        /**
         * 获取表达式触发器
         * <p>
         * param scheduler the scheduler
         * param jobName   the job name
         * param jobGroup  the job group
         * return cron trigger
         */
        private CronTrigger getCronTrigger(Scheduler scheduler, String jobName, String jobGroup) throws Exception {
            try {
                //TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroup);
                TriggerKey triggerKey = getTriggerKey(jobName, jobGroup);
                return (CronTrigger) scheduler.getTrigger(triggerKey);
            } catch (SchedulerException e) {
                logger.error("\"获取定时任务CronTrigger出现异常\"", e);
                throw new Exception(e);
            }
        }

        /**
         * 获取jobKey
         * <p>
         * param jobName  the job name
         * param jobGroup the job group
         * return the job key
         */
        private static JobKey getJobKey(String jobName, String jobGroup) {
            return JobKey.jobKey(jobName, jobGroup);
        }




    }


