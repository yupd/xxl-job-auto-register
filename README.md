## xxljob-autoregister-spring-boot-starter

**********************************

> 自动注册xxl-job执行器以及任务

- 减少参数配置。

```properties
# 【已删除】执行器名称，使用 xxl.job.executor.appname 作为名称
xxl.job.executor.title=Exe-Titl
# 【已删除】使用原生的实现逻辑获取address
xxl.job.executor.addressList=http://127.0.0.1:9999
```

- 注解配置优化
```java
public class TestService {
    @XxlRegister(cron = "0 0 0 * * ? *",
            author = "hydra",
            jobDesc = "测试job",
            disabled = true, // 将原属性 triggerStatus 改成 disabled
            overwrite = true // 增加此配置，支持任务更新操作
    )
    public void testJob() {}
}
```

- 支持任务自动更新操作。

## 1、打包

```
mvn clean install
```

## 2、项目中引入

```xml
<dependency>
    <groupId>io.github.yupd</groupId>
    <artifactId>spring-boot-starter-xxljob-wrapper</artifactId>
    <version>0.0.1</version>
</dependency>
```

## 3、配置

springboot项目配置文件application.properties：

```properties
server.port=8082

# 原生xxl-job配置
xxl.job.admin.addresses=http://127.0.0.1:8080/xxl-job-admin
xxl.job.accessToken=default_token
xxl.job.executor.appname=xxl-job-executor-test
xxl.job.executor.address=
xxl.job.executor.ip=127.0.0.1
xxl.job.executor.port=9999
xxl.job.executor.logpath=/data/applogs/xxl-job/jobhandler
xxl.job.executor.logretentiondays=30

# 新增配置项，必须项
# admin用户名
xxl.job.admin.username=admin
# admin 密码
xxl.job.admin.password=123456
```

`XxlJobSpringExecutor`参数配置与之前相同

## 4、添加注解
需要自动注册的方法添加注解`@XxlRegister`，不加则不会自动注册

```java
@Service
public class TestService {

    /**
     * overwrite：可以更新任务信息
     * disabled：任务启用还是禁用
     */
    @XxlJob(value = "testJob")
    @XxlRegister(cron = "0 0 0 * * ? *",
            author = "hydra",
            jobDesc = "测试job", disabled = true, overwrite = true)
    public void testJob(){
        System.out.println("#公众号：码农参上");
    }

    @XxlJob(value = "testJob222")
    @XxlRegister(cron = "59 1-2 0 * * ?",
            triggerStatus = 1)
    public void testJob2(){
        System.out.println("#作者：Hydra");
    }

    @XxlJob(value = "testJob444")
    @XxlRegister(cron = "59 59 23 * * ?")
    public void testJob4(){
        System.out.println("hello xxl job");
    }
}
```
