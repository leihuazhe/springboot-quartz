package xyz.youjieray.task.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.youjieray.model.TaskModel;
import xyz.youjieray.quartz.SchedulerManager;
import xyz.youjieray.task.mapper.TaskMapper;

import java.util.HashMap;
import java.util.List;

/**
 * TaskService
 *
 * @author leihz
 * @date 2017/7/5 17:17
 */
@Service
public class TaskService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private TaskMapper taskMapper;
    @Autowired
    SchedulerManager schedulerManager;


    public List<TaskModel> getTaskList() {
        return taskMapper.getTaskList();
    }

    public List<TaskModel> getTaskListByParam(HashMap paramMap) {
        return taskMapper.getTaskListByParam(paramMap);
    }

    public List<TaskModel> getAllJobDetail(){return schedulerManager.getAllJobDetail();}
}
