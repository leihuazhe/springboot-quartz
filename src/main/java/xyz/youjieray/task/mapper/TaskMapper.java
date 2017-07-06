package xyz.youjieray.task.mapper;

import org.apache.ibatis.annotations.Mapper;
import xyz.youjieray.model.TaskModel;

import java.util.HashMap;
import java.util.List;

/**
 * desc
 *
 * @author leihz
 * @date 2017/7/5 17:18
 */
@Mapper
public interface TaskMapper {

     List<TaskModel> getTaskList();

     List<TaskModel> getTaskListByParam(HashMap paramMap);
}
