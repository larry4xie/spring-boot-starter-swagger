# spring-boot-starter-swagger

spring-boot-starter-swagger via springbox

## 依赖

```xml
<dependency>
    <groupId>xyz.lxie</groupId>
    <artifactId>spring-boot-starter-swagger</artifactId>
    <version>${starter-swagger.version}</version>
</dependency>
```

> 最新版本[查询](http://search.maven.org/#search%7Cga%7C1%7Cg%3A%22xyz.lxie%22%20a%3A%22spring-boot-starter-swagger%22)

## 配置

```properties
# application.properties
swagger.ui.xxx=xyz
...
```

配置参数见: [SwaggerProperties.java](https://github.com/xiegang/spring-boot-starter-swagger/blob/master/src/main/java/xyz/lxie/springboot/api/SwaggerProperties.java)

线上环境关闭swagger ui, 可以配置参数 `swagger.ui.enable=false`

## 使用
[http://host:port/swagger-ui.html](http://localhost:8080/swagger-ui.html)



