# 欢迎捐助

- 如果您觉得本项目对您有帮助，请捐助，谢谢。

- 您的捐助就是我最大的动力。

<p align=center>
  <a href="https://xuxiaowei.com.cn">
    <img src="https://cdn2.xuxiaowei.com.cn/img/QRCode.png/xuxiaowei.com.cn" alt="徐晓伟工作室" width="360">
  </a>
</p>


# Spring-Boot-Redis-Session
Spring Boot 整合 Redis 与 Session 共享。
内涵 Spring Boot 整合 Redis，详情请查看 [Spring-Boot-Redis](https://github.com/XXWXHK/Spring-Boot-Redis)

# 依赖

## Spring Boot 依赖（创建项目时选择）

- Spring Boot
    - 2.1.5.RELEASE
    
- Core
    - Session               用于管理用户会话信息的API和实现。
    
- Web
    - Web                   使用Tomcat和Spring MVC进行全栈Web开发
    
- Template
	- Thymeleaf             Thymeleaf模板引擎（页面）
    
- Security
    - Security              通过spring-security保护您的应用程序
                            （由于 spring-boot-starter-parent 2.1.5.RELEASE 中的 Session 需要 Security 支持，故此才引入，
                            Security 默认开启，
                            如果不使用 Security，禁用方法参见 cn.com.xuxiaowei.redissession.config.WebSecurityConfigurerAdapterConfig，
                            如果使用 2.1.5.RELEASE 之前的版本，则不需要引入 Security）
    
- NoSQL
    - Redis                 Redis键值数据存储，包括spring-data-redis
    
# 使用说明

## 连接说明

- 单个项目无所谓

- 多个项目连接同一个 Redis 的相同数据库（spring.redis.database）

## 支持协议

- http、https

## 支持域名

- 必须是相同域名下的不同子域进行 Redis Session

-- 如：将 Cookie 设置在 xuxiaowei.com.cn 中，可在 所有子域中使用 www.xuxiaowei.com.cn 、 demo.xuxiaowei.com.cn 等等

## 测试

- 将本项目复制一份，将端口 server.port 改为不同，运行后，访问不同的项目，卡查看到，相同的 Session