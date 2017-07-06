package xyz.youjieray.plan;

import org.quartz.JobDataMap;
import xyz.common.Constans;
import xyz.common.utils.DateConvertUtil;
import xyz.youjieray.model.TaskModel;

/**
 * Created by Youjie on 2017/7/5.
 */
public class PlanDataTask {

    public void ncPlanDataTask(Object dataMap){
        TaskModel task = null;
        if (dataMap instanceof JobDataMap) {
            JobDataMap jobDataMap = (JobDataMap) dataMap;
            task = (TaskModel) jobDataMap.get(Constans.QUARTZ_TEST);
        } else if (dataMap instanceof TaskModel) {
            task = (TaskModel) dataMap;
        }
        String desc = task.getTaskDesc();

        StringBuilder sb = new StringBuilder();

        sb.append("\n\n------ -------------------------------Job start------------------------------------------------------------------------").append("\n");

        sb.append("         now i execute job is "+desc+ ",  time now is--->"+DateConvertUtil.generateDateTime("yyyy-MM-dd HH:mm:ss")).append("\n");

        sb.append("\n\n------ -------------------------------Job end------------------------------------------------------------------------").append("\n");

        System.out.println(sb.toString());

    }

}
