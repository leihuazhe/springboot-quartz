package xyz.youjieray.task.controller;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.common.utils.DataInfoFormat;
import xyz.common.utils.RespCode;
import xyz.youjieray.model.TaskModel;
import xyz.youjieray.task.service.TaskService;

import java.util.List;

/**
 * TaskController
 *
 * @author leihz
 * @date 2017/7/6 15:04
 */
@RestController
@RequestMapping("/task")
public class TaskController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    TaskService taskService;


    @RequestMapping("/detail")
    public Object showTaskDetail(){
       try {
           List<TaskModel> list =  taskService.getAllJobDetail();
           String jsonString = JSON.toJSONString(DataInfoFormat.printlnJobDetailFormat(list));
           return RespCode.SUCCESS("SUCCESS",jsonString);
       }catch (Exception e){
           e.printStackTrace();
           logger.error("查看任务队列消息失败", e.getMessage());
           return RespCode.ERROR("ERROR", "查看任务队列消息失败");
       }

    }

}
