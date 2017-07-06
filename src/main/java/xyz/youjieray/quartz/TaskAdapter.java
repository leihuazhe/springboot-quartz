package xyz.youjieray.quartz;

import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.common.Constans;
import xyz.youjieray.model.TaskModel;

import java.lang.reflect.InvocationTargetException;

/**
 * desc
 *
 * @author leihz
 * @date 2017/7/5 15:53
 */
public class TaskAdapter implements InterruptableJob{

    private  static Logger logger = LoggerFactory.getLogger(TaskAdapter.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
        TaskModel taskModel = (TaskModel) jobDataMap.get(Constans.QUARTZ_TEST);

        Object executeObj =  taskModel.getTargetObject();
        String tarteMethod = taskModel.getTaskMethod();

        if(logger.isInfoEnabled()){
            logger.info("任务开始执行：{}.{}",taskModel.getTaskTarget(),tarteMethod);
        }

        try {
            executeObj.getClass().getDeclaredMethod(tarteMethod,Object.class).invoke(executeObj,jobDataMap);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            logger.error("IllegalAccessException",e);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            logger.error("InvocationTargetException",e);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            logger.error("NoSuchMethodException",e);
        }

    }


    @Override
    public void interrupt() throws UnableToInterruptJobException {

    }
}
