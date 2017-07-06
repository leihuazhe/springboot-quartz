# springboot-quartz
本项目是使用springboot + mybatis +quartz 实现的从数据库读取定时任务的demo
由于一般的quartz demo过于简单，在做复杂的定时任务时，需要考虑很多方面。
本demo考虑多方面后，实现了全面的quartz应用，
这也是目前我司项目中用到的quartz的简化，希望大家会喜欢
## 使用指南
1. eclipse导入项目方式，右键->import ->Maven -> Existing Maven Projects ->选择项目
2. idea导入项目方式，File->直接import
3. 找到工程resources目录下的，application-dev.yml，将数据库和email等信息设置为你自己的。
1. 找到启动类QuartzApplication ，右键run as -> Java Application 启动
1. 此时，在数据库中配置的任务就会就位，然后开始任务调度
1. 如果想看任务详情，打开浏览器，访问 localhost:9000/task/detail即可 