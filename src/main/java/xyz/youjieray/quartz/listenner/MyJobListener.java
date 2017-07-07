package xyz.youjieray.quartz.listenner;

import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import xyz.common.Constans;
import xyz.common.mail.MailSender;
import xyz.youjieray.model.TaskModel;
import xyz.common.utils.DateConvertUtil;

import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * created by IntelliJ IDEA
 *
 * @author leihz
 * @date 2017/7/5 15:06
 */
@Component
public class MyJobListener implements JobListener {

    private  static Logger logger = LoggerFactory.getLogger(MyJobListener.class);
    private static ExecutorService executorService = Executors.newCachedThreadPool();

    @Override
    public String getName() {
        return "MyJobListener";
    }

    /**
     *(1)
     * Called by the Scheduler when a JobDetail is about to be executed (an associated Trigger has occurred).
     * @param context
     */
    @Override
    public void jobToBeExecuted(JobExecutionContext context) {
        JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
        TaskModel taskModel = (TaskModel) jobDataMap.get(Constans.QUARTZ_TEST);

        if (logger.isInfoEnabled()) {
            logger.info("jobToBeExecuted --> 任务执行之前执行：{}.{}", taskModel.getTaskTarget(), taskModel.getTaskMethod());
        }
    }


     /**
     * (2)
     * 这个方法正常情况下不执行,但是如果当TriggerListener中的vetoJobExecution方法返回true时,那么执行这个方法.
     * 需要注意的是 如果方法(2)执行 那么(1),(3)这个俩个方法不会执行,因为任务被终止了嘛.
     * Called by the Scheduler when a JobDetail was about to be executed (an associated Trigger has occurred),
     * but a TriggerListener vetoed it's execution.
     */
    @Override
    public void jobExecutionVetoed(JobExecutionContext context) {
        JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
        TaskModel taskScheduled = (TaskModel) jobDataMap.get(Constans.QUARTZ_TEST);
        if (logger.isInfoEnabled()) {
            logger.info("jobExecutionVetoed -->定时任务vetoJobExecution：{}.{}    group[{}]   name[{}]", taskScheduled.getTaskTarget(), taskScheduled.getTaskMethod(), taskScheduled.getTaskGroup(), taskScheduled.getTaskName());
        }
    }


    /**
     * (3)
     * 任务执行完成后执行,jobException如果它不为空则说明任务在执行过程中出现了异常
     * Called by the Scheduler after a JobDetail has been executed, and be for the associated Trigger's triggered(xx) method has been called.
     */
    @Override
    public void jobWasExecuted(JobExecutionContext context, JobExecutionException exception) {
        JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
        TaskModel taskScheduled = (TaskModel) jobDataMap.get(Constans.QUARTZ_TEST);
        if (logger.isInfoEnabled()) {
            logger.info("jobWasExecuted --> 定时任务执行结束：{}.{}    group[{}]   name[{}]", taskScheduled.getTaskTarget(), taskScheduled.getTaskMethod(), taskScheduled.getTaskGroup(), taskScheduled.getTaskName());
        }
        if (exception != null) {//任务执行出错  发送邮件
            String info = "任务taskGroup[" + taskScheduled.getTaskGroup() + "] , taskName[" + taskScheduled.getTaskName() + "] ,  任务描述[" + taskScheduled.getTaskDesc() + "]   于 "+ DateConvertUtil.generateDateTime(new Date(),DateConvertUtil.DATE_TIME_FORMAT)+" 执行失败 ,\n 失败原因: " + exception.getMessage();
            //log.setTask_desc("jobWasExecuted "+ info); 日志记录
            sendEmail(info);// email发送
            logger.info(info);
        }
    }

    public void sendEmail(final String content){
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                MailSender mailSender = new MailSender();
                mailSender.sendEmail("定时任务测试",content);
            }
        });
    }
}
