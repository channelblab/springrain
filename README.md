# SpringRain

<div style="text-align: center;">

![Static Badge](https://img.shields.io/badge/Version_1.0.0)
![Static Badge](https://img.shields.io/badge/Version-2-4)

</div>

## 基础哲学

**约定大于配置，快速出成果，切勿炫技！**


## 设计根据

### 为什么http请求头的大量使用？
严格意义来说，请求参数放在什么地方传都是可以的。但是在项目中，为什么会将一部分数据放在请求头中进行传输？

+ 请求头中传输的是公共、通用型的参数。因为其是公共的，放在任何请求参数中显得不合适。
+ 现如今多段开发，一些开发方式不支持cookie等传输方式，因为为了多端通用将数据放在请求头中较为合适

什么样的数据应该放在请求头中？







## 数据库初始化

```yaml

spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/springrain?servertimezone=UTC&useUnicode=true&charaterEncoding=utf-8&createDatabaseIfNotExist=true
    username: root
    password: dengyi1991
  sql:
    init:
      mode: always
      schema-locations: classpath:database/schema.sql
      data-locations: classpath:database/data.sql
      encoding: utf-8
      continue-on-error: true
      platform: mysql

```

## 系统操作日志

大数据居量返回的接口，请使用`@NoLog`注解进行标注，不进行日志拦截

## 权限

### 菜单、按钮、API权限

### 数据权限
数据的权限，没有通用的解决方案，只能在代码中进行处理


## 如何使用？

1. 添加maven依赖
```xml

<dependency>
    <groupId>com.lmax</groupId>
    <artifactId>disruptor</artifactId>
    <version>3.4.2</version>
</dependency>

```
项目最新代码为master分支

2. 新建springboot工程
3. 将本项目在启动类上引入
```java

@SpringBootApplication
@Import(com.channelblab.springrain.SpringRainApplication.class)
public class DemoApp {
    public static void main(String[] args) {
        SpringApplication.run(DemoApp.class, args);
    }
}

```
4. 愉快开发
