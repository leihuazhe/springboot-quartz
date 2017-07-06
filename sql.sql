/*
SQLyog Ultimate v11.24 (32 bit)
MySQL - 5.5.29 : Database - test
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`test` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `test`;

/*Table structure for table `nc_task` */

DROP TABLE IF EXISTS `nc_task`;

CREATE TABLE `nc_task` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `task_type` varchar(255) DEFAULT NULL COMMENT '任务分类   data_collect:截存 , data_summary:汇总 ',
  `task_group` varchar(255) DEFAULT NULL COMMENT '分组',
  `task_name` varchar(255) DEFAULT NULL COMMENT '名称',
  `task_aliasname` varchar(255) DEFAULT NULL COMMENT '别名',
  `task_status` varchar(255) DEFAULT NULL COMMENT '任务状态 0禁用 1启用 2删除',
  `task_cron` varchar(255) DEFAULT NULL COMMENT '任务表达式',
  `date_type` varchar(20) DEFAULT NULL COMMENT '时间类型: before_day:前一天 current_day 当天',
  `task_desc` varchar(255) DEFAULT NULL COMMENT '任务描述',
  `task_target` varchar(255) DEFAULT NULL COMMENT '任务 执行对象',
  `task_method` varchar(255) DEFAULT NULL COMMENT ' 任务执行方法',
  `opt_config` text COMMENT '处理配置',
  `datasql` text COMMENT 'sql 语句',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8;

/*Data for the table `nc_task` */

insert  into `nc_task`(`id`,`task_type`,`task_group`,`task_name`,`task_aliasname`,`task_status`,`task_cron`,`date_type`,`task_desc`,`task_target`,`task_method`,`opt_config`,`datasql`) values (33,'plan_data','data-gather','planDataTask1','planDataTask1','1','0 1/7 * * * ? ',NULL,'每7min执行','xyz.youjieray.plan.PlanDataTask','ncPlanDataTask',NULL,NULL),(44,'plan_data','data-gather','planDataTask2','planDataTask2','1','0 0/5 * * * ?',NULL,'每5min执行','xyz.youjieray.plan.PlanDataTask','ncPlanDataTask',NULL,NULL),(1007,'plan_data','data-gather','planDataTask3','planDataTask3','1','0 0/2 * * * ?','current_day','每2min执行','xyz.youjieray.plan.PlanDataTask','ncPlanDataTask',NULL,NULL),(9999,'test','data-gather','printTask','printTask','1','0 0/1 * * * ?',NULL,'打印任务信息','xyz.youjieray.plan.ExcuteTask','prinltTaskExecuteDetail',NULL,NULL);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
