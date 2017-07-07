package xyz.youjieray.plan;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import xyz.common.Constans;
import xyz.common.utils.DataInfoFormat;
import xyz.youjieray.model.TaskModel;
import xyz.youjieray.quartz.SchedulerManager;
import xyz.common.utils.SpringContextUtil;

/**
 * created by IntelliJ IDEA
 * 查看定时任务 执行情况
 *
 * @author leihz
 * @date 2017/7/6 13:51
 */
public class ExcuteTask {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SchedulerManager schedulerManager;

    public void prinltTaskExecuteDetail(Object data){
        ApplicationContext app = SpringContextUtil.getApplicationContext();
        // SchedulerManager schedulerManager = (SchedulerManager)app.getAutowireCapableBeanFactory().autowire(SchedulerManager.class,1,false);
        SchedulerManager schedulerManager = (SchedulerManager)app.getBean("schedulerManager");
        System.out.println(1);
        if(logger.isInfoEnabled()){
            logger.info(DataInfoFormat.printlnJobDetailFormat(schedulerManager.getAllJobDetail()));
        }
    }

    public void addTask(Object data){
        ApplicationContext app = SpringContextUtil.getApplicationContext();
        SchedulerManager schedulerManager = (SchedulerManager)app.getBean("schedulerManager");
        TaskModel task = new TaskModel();
        task.setTaskGroup("nc_report");
        task.setTaskName("look_task"/*+ Application.TASK_COUNT*/);
        task.setTaskAliasName("alias_addTask");
        task.setTaskTarget("com.hxqc.data.gather.core.quartz.TaskExecuteTest");
        task.setTaskMethod("prinltTaskExecuteDetail");
        task.setTaskDesc("查看所有任务信息");
        task.setTaskCron("0 15 0 * * ? *");//每分钟执行一次
        try {
            schedulerManager.createOrUpdateTask(task);
            Constans.TASK_COUNT++;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception",e.getMessage());
        }
    }

    public void removeTask(Object data){
        ApplicationContext app = SpringContextUtil.getApplicationContext();
        SchedulerManager schedulerManager = (SchedulerManager)app.getBean("schedulerManager");
        TaskModel task = new TaskModel();
        task.setTaskGroup("nc_report");
        task.setTaskName("addTask");
        try {
            schedulerManager.delJob(task);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception",e.getMessage());
        }
    }


}

