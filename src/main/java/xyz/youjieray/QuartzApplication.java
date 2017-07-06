package xyz.youjieray;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import xyz.youjieray.model.TaskModel;
import xyz.youjieray.quartz.SchedulerManager;
import xyz.youjieray.task.service.TaskService;

import java.util.HashMap;
import java.util.List;

@SpringBootApplication
//@MapperScan("xyz.youjieray")
public class QuartzApplication {

	public static void main(String[] args) throws Exception {
		ApplicationContext app = SpringApplication.run(QuartzApplication.class, args);

		SchedulerManager schedulerManager = (SchedulerManager)app.getBean("schedulerManager");
		TaskService taskService = (TaskService)app.getBean("taskService");
		HashMap<String,Object> paramMap = new HashMap<String, Object>();
		paramMap.put("task_status", "1");
		//paramMap.put("task_group", Constants.SYSTEM_TASK_GROUP);
		List<TaskModel> taskList = taskService.getTaskListByParam(paramMap);
		for (TaskModel task : taskList) {
			schedulerManager.createOrUpdateTask(task);
		}
	}
}
