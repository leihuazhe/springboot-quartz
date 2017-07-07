package xyz.youjieray.model;

import java.io.Serializable;
import java.util.Date;

/**
 * created by IntelliJ IDEA
 *
 * @author leihz
 * @date 2017/7/5 15:17
 */
@SuppressWarnings("serial")
public class TaskModel implements Serializable{


    public TaskModel() {
    }

    public TaskModel(String taskGroup, String taskName) {
        this.taskGroup = taskGroup;
        this.taskName = taskName;
    }


    /**
     * 任务名称
     */
    private String taskName;
    /**
     * 任务名称
     */
    private String taskAliasName;
    /**
     * 任务分组
     */
    private String taskGroup;
    /**
     * 任务状态 0禁用 1启用 2删除
     */
    private String taskStatus;
    /**
     * 任务运行时间表达式
     */
    private String taskCron;
    /**
     * 最后一次执行时间
     */
    private Date previousFireTime;
    /**
     * 下次执行时间
     */
    private Date nextFireTime;
    /**
     * 任务执行时间类型
     */
    private String dateType;
    /**
     * 任务描述
     */
    private String taskDesc;
    // 任务类型(是否阻塞)
    private String jobType;
    // 本地任务/dubbo任务
    private String taskType;
    // 运行系统(dubbo任务必须)
    private String targetSystem;
    // 任务对象
    private String taskTarget;
    // 任务方法
    private String taskMethod;
    // jobClass
    private String jobClass;


    /**************************************/
    private String id;
    private Object targetObject;
    private String optConfig;
    private String dataSql;


    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskAliasName() {
        return taskAliasName;
    }

    public void setTaskAliasName(String taskAliasName) {
        this.taskAliasName = taskAliasName;
    }

    public String getTaskGroup() {
        return taskGroup;
    }

    public void setTaskGroup(String taskGroup) {
        this.taskGroup = taskGroup;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }

    public String getTaskCron() {
        return taskCron;
    }

    public void setTaskCron(String taskCron) {
        this.taskCron = taskCron;
    }

    public Date getPreviousFireTime() {
        return previousFireTime;
    }

    public void setPreviousFireTime(Date previousFireTime) {
        this.previousFireTime = previousFireTime;
    }

    public Date getNextFireTime() {
        return nextFireTime;
    }

    public void setNextFireTime(Date nextFireTime) {
        this.nextFireTime = nextFireTime;
    }

    public String getDateType() {
        return dateType;
    }

    public void setDateType(String dateType) {
        this.dateType = dateType;
    }

    public String getTaskDesc() {
        return taskDesc;
    }

    public void setTaskDesc(String taskDesc) {
        this.taskDesc = taskDesc;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public String getTargetSystem() {
        return targetSystem;
    }

    public void setTargetSystem(String targetSystem) {
        this.targetSystem = targetSystem;
    }

    public String getTaskMethod() {
        return taskMethod;
    }

    public void setTaskMethod(String taskMethod) {
        this.taskMethod = taskMethod;
    }

    public String getJobClass() {
        return jobClass;
    }

    public void setJobClass(String jobClass) {
        this.jobClass = jobClass;
    }

    public String getTaskTarget() {
        return taskTarget;
    }

    public void setTaskTarget(String taskTarget) {
        this.taskTarget = taskTarget;
    }

    public Object getTargetObject() {
        return targetObject;
    }

    public void setTargetObject(Object targetObject) {
        this.targetObject = targetObject;
    }

    public String getOptConfig() {
        return optConfig;
    }

    public void setOptConfig(String optConfig) {
        this.optConfig = optConfig;
    }

    public String getDataSql() {
        return dataSql;
    }

    public void setDataSql(String dataSql) {
        this.dataSql = dataSql;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
