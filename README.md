## 说明
**甲壳虫社区（Beetle Community）**  
一个开源的问答社区

## 使用技术
后端使用Spring Boot + Mybatis + Thymeleaf  
前端页面用BootStrap搭建  
数据库使用MySQL  
使用Github作为第三方登录  

## 工具
Git  
Lombok  
Flyway  
postman（插件版）(https://chrome.google.com/webstore/detail/tabbed-postman-rest-clien/coohjcphdfgbiolnekdpbcijmhambjff)网页版postman可以共享浏览器session，比较方便
[moment JavaScript日期处理类库](http://momentjs.cn/)
## 命令
运行Flyway：`mvn flyway:migrate`  
Mybatis Generator：`mvn -Dmybatis.generator.overwrite=true mybatis-generator:generate`

## 参考
- [Spring Boot官方文档](https://docs.spring.io/spring-boot/docs/2.2.4.RELEASE/reference/html/)  
- [BootStrap官方教程](https://v3.bootcss.com/components/)  
- [Github第三方登录官方教程](https://developer.github.com/apps/)  
- [Mybatis官方文档](https://mybatis.org/mybatis-3/zh/index.html)  
- [Thymeleaf官方文档](https://www.thymeleaf.org/doc/tutorials/3.0/usingthymeleaf.html)  
- [Mybatis Generator官方文档](http://mybatis.org/generator/)   
- [MySQL菜鸟教程](https://www.runoob.com/mysql/mysql-tutorial.html)  
- [Lombok官网](https://projectlombok.org/)  
- [springboot_learn，一个大佬写的关于Spring Boot各种实践demo](https://gitee.com/dalaoyang/springboot_learn)

## 常见问题解决
- [Github登录流程分析](https://www.cnblogs.com/songjilong/p/12309552.html)  
- [Github更新官方推荐的使用access_token安全访问API的方式，用Authorization HTTP header代替query paramet，旧方式即将被废弃](https://blog.csdn.net/kuaileky/article/details/104217757)  
- [Mybatis Generator配置文件内容详解](https://www.jianshu.com/p/a8bfc14a3534)     
- [Springboot mybatis集成 Invalid bound statement (not found)](https://blog.csdn.net/qq_35981283/article/details/78590090)    
- [MyBatis Generator实现MySQL分页插件](https://blog.csdn.net/xiao__gui/article/details/51333693)    
- [git push报错连接超时443](https://gist.github.com/laispace/666dd7b27e9116faece6)    
- [thymeleaf向fragment中传入具体值](https://blog.csdn.net/u010999809/article/details/80724076)

