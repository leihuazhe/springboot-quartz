package xyz.youjieray;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import xyz.youjieray.model.TaskModel;
import xyz.youjieray.quartz.SchedulerManager;
import xyz.youjieray.task.service.TaskService;
import xyz.common.utils.SpringContextUtil;

import java.util.HashMap;
import java.util.List;

/**
 * created by IntelliJ IDEA
 *
 * @author leihz
 * @date 2017/7/5 13:51
 */
@SpringBootApplication
//@MapperScan("xyz.youjieray")
public class QuartzApplication {

	public static void main(String[] args) throws Exception {
		ApplicationContext app = SpringApplication.run(QuartzApplication.class, args);
		/**将spring上下文set到SpringContextUtil中*/
		SpringContextUtil.setApplicationContext(app);

		SchedulerManager schedulerManager = (SchedulerManager)app.getBean("schedulerManager");
		TaskService taskService = (TaskService)app.getBean("taskService");
		HashMap<String,Object> paramMap = new HashMap<String, Object>();
		paramMap.put("task_status", "1");
		List<TaskModel> taskList = taskService.getTaskListByParam(paramMap);
		for (TaskModel task : taskList) {
			schedulerManager.createOrUpdateTask(task);
		}
	}
}
