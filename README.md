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