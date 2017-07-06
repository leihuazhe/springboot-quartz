package xyz.youjieray.quartz.listenner;

import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.Trigger;
import org.quartz.TriggerListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import xyz.youjieray.model.TaskModel;
import xyz.youjieray.utils.DateConvertUtil;

import java.util.Date;

/**
 * MyTriggerListener 任务触发器监听器
 *
 * @author leihz
 * @date 2017/7/5 15:38
 */
@Component
public class MyTriggerListener implements TriggerListener{

    private  static Logger logger = LoggerFactory.getLogger(MyTriggerListener.class);

    @Override
    public String getName() {
        return "MyTriggerListener";
    }

    /**
     * (1)
     * Trigger被激发 它关联的job即将被运行
     * Called by the Scheduler when a Trigger has fired, and it's associated JobDetail is about to be executed.
     */
    @Override
    public void triggerFired(Trigger trigger, JobExecutionContext context) {
        JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
        TaskModel taskScheduled = (TaskModel) jobDataMap.get("1");
        if (logger.isInfoEnabled()) {
            logger.info("triggerFired-->Trigger被激发 它关联的job即将被运行：{}.{}    group[{}]   name[{}]", taskScheduled.getTaskTarget(), taskScheduled.getTaskMethod(), taskScheduled.getTaskGroup(), taskScheduled.getTaskName());
        }
    }
    /**
     * (2)
     * Trigger被激发 它关联的job即将被运行,先执行(1)，在执行(2) 如果返回TRUE 那么任务job会被终止
     * Called by the Scheduler when a Trigger has fired, and it's associated JobDetail is about to be executed
     */
    @Override
    public boolean vetoJobExecution(Trigger trigger, JobExecutionContext context) {
        JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
        TaskModel taskScheduled = (TaskModel) jobDataMap.get("1");
        if (logger.isInfoEnabled()) {
            logger.info(" vetoJobExecution--> Trigger被激发 如果返回TRUE 那么任务job会被终止：{}.{}    group[{}]   name[{}]", taskScheduled.getTaskTarget(), taskScheduled.getTaskMethod(), taskScheduled.getTaskGroup(), taskScheduled.getTaskName());
        }
        return false;
    }
    /**
     * (3) 当Trigger错过被激发时执行,比如当前时间有很多触发器都需要执行，但是线程池中的有效线程都在工作，
     * 那么有的触发器就有可能超时，错过这一轮的触发。
     * Called by the Scheduler when a Trigger has misfired.
     */
    @Override
    public void triggerMisfired(Trigger trigger) {
        JobDataMap jobDataMap = trigger.getJobDataMap();
        TaskModel taskScheduled = (TaskModel) jobDataMap.get("1");
        if (logger.isInfoEnabled()) {
            logger.info("triggerMisfired --> Trigger错过被激发时执行 {}.{}    group[{}]   name[{}]", taskScheduled.getTaskTarget(), taskScheduled.getTaskMethod(), taskScheduled.getTaskGroup(), taskScheduled.getTaskName());
        }

       // TaskLog log = (TaskLog) jobDataMap.get(Application.QUARTZ_LOG);
       // log.setLog_type("triger");
        //log.setExecute_starttime(new Date());
       // trigger.getJobDataMap().put("1", log);

        //发送邮件
        String info = "任务 taskGroup[" + taskScheduled.getTaskGroup() + "] , taskName[" + taskScheduled.getTaskName() + "] ,  任务描述[" + taskScheduled.getTaskDesc() + "] 于 "+ DateConvertUtil.generateDateTime(new Date(),DateConvertUtil.DATE_TIME_FORMAT)+"  错过触发时间(任务挤压)，请及时查看原因、进行调优或其他处理！\n\n可能原因： " +"当Trigger错过被激发时执行,比如当前时间有很多触发器都需要执行，但是线程池中的有效线程都在工作,那么有的触发器就有可能超时，错过这一轮的触发。";
       // log.setTask_desc(info);
        //sendEmail(info);

       // saveLog(log);
    }
    /**
     * (4) 任务完成时触发
     * Called by the Scheduler when a Trigger has fired, it's associated JobDetail has been executed
     * and it's triggered(xx) method has been called.
     */
    @Override
    public void triggerComplete(Trigger trigger, JobExecutionContext jobExecutionContext, Trigger.CompletedExecutionInstruction completedExecutionInstruction) {
        JobDataMap jobDataMap = trigger.getJobDataMap();
        TaskModel taskScheduled = (TaskModel) jobDataMap.get("1");
        if (logger.isInfoEnabled()) {
            logger.info("triggerComplete --> triggerComplete任务完成时触发  {}.{}    group[{}]   name[{}]", taskScheduled.getTaskTarget(), taskScheduled.getTaskMethod(), taskScheduled.getTaskGroup(), taskScheduled.getTaskName());
        }
    }
}
