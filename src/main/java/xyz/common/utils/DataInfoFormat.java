package xyz.common.utils;

import org.apache.commons.lang3.StringUtils;
import xyz.youjieray.model.TaskModel;

import java.util.List;

/**
 * desc
 *
 * @author leihz
 * @date 2017/7/6 14:01
 */
public class DataInfoFormat {
    public static String printlnJobDetailFormat(List<TaskModel> scheduledList) {
        StringBuilder sb = new StringBuilder();
        int count = 0;
        sb.append("\n\n------ -------------------------------任务队列信息------------------------------------------------------------------------").append("\n");
        for (TaskModel scheduled : scheduledList) {
            count++;
            sb.append("\n\n**** 任务" + count + " **** " + String.format("%-38s", "任务名称[taskName]:" + scheduled.getTaskName()) + String.format("%-38s", "任务分组[taskGroup]:" + scheduled.getTaskGroup())).append("*************");

            // 任务别名
            sb.append("\n---------- " + String.format("%-35s", "任务别名[taskAliasName]:") + String.format("%-30s", scheduled.getTaskAliasName()));
            //  任务状态 0禁用 1启用 2删除
            sb.append("\n---------- " + String.format("%-35s", "任务状态[taskStatus]:") + String.format("%-30s", scheduled.getTaskStatus()));
            //  任务运行时间表达式
            sb.append("\n---------- " + String.format("%-35s", "任务触发[taskCron]:") + String.format("%-30s", scheduled.getTaskCron()));
            // 最后一次执行时间
            sb.append("\n---------- " + String.format("%-35s", "最后执行[previousFireTime]:") + String.format("%-30s", (scheduled.getPreviousFireTime() != null && StringUtils.isNotBlank(scheduled.getPreviousFireTime().toString()) ? DateConvertUtil.generateDateTime(scheduled.getPreviousFireTime(), DateConvertUtil.DATE_TIME_FORMAT) : "")));
            // 下次执行时间
            sb.append("\n---------- " + String.format("%-35s", "下次执行[nextFireTime]:") + String.format("%-30s", (scheduled.getNextFireTime() != null && StringUtils.isNotBlank(scheduled.getNextFireTime().toString()) ? DateConvertUtil.generateDateTime(scheduled.getNextFireTime(), DateConvertUtil.DATE_TIME_FORMAT) : "")));
            // 任务描述
            sb.append("\n---------- " + String.format("%-35s", "任务描述[taskDesc]:") + String.format("%-30s", scheduled.getTaskDesc()));
            // 任务类型(是否阻塞)
            //sb.append("\n---------- "+String.format("%-35s", "任务类型(是否阻塞)[jobType]:") + String.format("%-30s",scheduled.getJobType()));
            //sb.append("\n---------- " + String.format("%-35s", "是否阻塞[jobType]:") + String.format("%-30s", scheduled.getJobType()));
            // 本地任务/dubbo任务
            //sb.append("\n---------- "+String.format("%-35s", "本地任务/dubbo任务[taskType]:") + String.format("%-30s",scheduled.getTaskType()));
            sb.append("\n---------- " + String.format("%-35s", "任务类型[taskType]:") + String.format("%-30s", scheduled.getTaskType()));
            //运行系统
            // sb.append("\n---------- "+String.format("%-35s", "运行系统(dubbo任务必须)[targetSystem]:") + String.format("%-30s",scheduled.getTargetSystem()));
            //sb.append("\n---------- " + String.format("%-35s", "运行系统[targetSystem]:") + String.format("%-30s", scheduled.getTargetSystem()));
            // Jobclass
            //sb.append("\n---------- " + String.format("%-35s", "调度适配[Jobclass]:") + String.format("%-30s", scheduled.getJobClass()));
            // 任务对象
            sb.append("\n---------- " + String.format("%-35s", "任务对象[targetObjectName]:") + String.format("%-30s", scheduled.getTaskTarget()));
            // 对象实例
            //sb.append("\n---------- " + String.format("%-35s", "对象实例[targetObject]:") + String.format("%-30s", scheduled.getTargetObject()));
            // 执行方法
            sb.append("\n---------- " + String.format("%-35s", "执行方法[targetMethod]:") + String.format("%-30s", scheduled.getTaskMethod()));

            //任务执行需要的参数
            // sb.append("\n---------- " + String.format("%-35s", "执行参数[taskData]:") + String.format("%-30s", scheduled.getTaskData()));
        }
        sb.append("\n\n--------------------------------------------任务队列信息结束【共" + count + "个任务】--------------------------------------------------").append("\n\n");

        return sb.toString();
    }


    public static String printlnJobDetail(List<TaskModel> scheduledList) {
        StringBuilder sb = new StringBuilder();
        int count = 0;
        sb.append("\n\n------ -------------------------------任务队列信息------------------------------------------------------------------------\n");
        for (TaskModel scheduled : scheduledList) {
            count++;
            sb.append(String.format("***%-15s", "[任务-" + count + "***"));

            //  任务分组
            sb.append(String.format("%-28s", "[任务分组[taskGroup]:" + scheduled.getTaskGroup()));
            // 任务名称
            sb.append(String.format("%-28s", "[任务名称[taskName]:" + scheduled.getTaskName()));
            // 任务别名
            sb.append(String.format("%-38s", "[任务别名[taskAliasName]:" + scheduled.getTaskAliasName()));
            //  任务状态 0禁用 1启用 2删除
            sb.append(String.format("%-28s", "[任务状态[taskStatus]:" + scheduled.getTaskStatus()));
            //  任务运行时间表达式
            sb.append(String.format("%-38s", "[时间表达式[taskCron]:" + scheduled.getTaskCron()));
            // 最后一次执行时间
            sb.append(String.format("%-48s", "[最后一次执行时间[previousFireTime]:" + (scheduled.getPreviousFireTime() != null && StringUtils.isNotBlank(scheduled.getPreviousFireTime().toString()) ? DateConvertUtil.generateDateTime(scheduled.getPreviousFireTime(), DateConvertUtil.DATE_TIME_FORMAT) : "")));
            // 下次执行时间
            sb.append(String.format("%-48s", "[下次执行时间[nextFireTime]:" + (scheduled.getNextFireTime() != null && StringUtils.isNotBlank(scheduled.getNextFireTime().toString()) ? DateConvertUtil.generateDateTime(scheduled.getNextFireTime(), DateConvertUtil.DATE_TIME_FORMAT) : "")));
            // 任务描述
            sb.append(String.format("%-28s", "[任务描述[taskDesc]:" + scheduled.getTaskDesc()));
            // 任务类型(是否阻塞)
            //sb.append(String.format("%-28s", "[任务类型(是否阻塞)[jobType]:" + scheduled.getJobType()));
            // 本地任务/dubbo任务
            sb.append(String.format("%-38s", "[本地任务/dubbo任务[taskType]:" + scheduled.getTaskType()));
            //运行系统
            //sb.append(String.format("%-38s", "[运行系统(dubbo任务必须)[targetSystem]:" + scheduled.getTargetSystem()));
            // Jobclass
            // sb.append(String.format("%-38s", "[任务调度适配器[Jobclass]:" + scheduled.getJobClass()));
            // 任务对象
            sb.append("\n---------- " + String.format("%-35s", "任务对象[targetObjectName]:") + String.format("%-30s", scheduled.getTaskTarget()));
            // 对象实例
            // sb.append(String.format("%-70s", "[任务对象[targetObject]:" + scheduled.getTargetObject()));
            // 执行方法
            sb.append(String.format("%-38s", "[执行方法[targetMethod]:" + scheduled.getTaskMethod()));

            //任务执行需要的参数
            //sb.append(String.format("%-35s", "[任务执行参数[taskData]:" + scheduled.getTaskData()));
            sb.append("\n");
        }
        sb.append("------------------------------------------------------任务队列信息结束【共" + count + "个任务】--------------------------------------------------").append("\n\n");

        return sb.toString();
    }
}
