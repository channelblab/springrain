# SpringRain

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