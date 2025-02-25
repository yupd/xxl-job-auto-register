package com.xxl.job.plus.executor.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 动态注册任务注解
 *
 * @author yupd
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface XxlRegister {

    String cron();

    String jobDesc() default "default jobDesc";

    String author() default "default Author";

    /*
     * 默认为 ROUND 轮询方式
     * 可选： FIRST 第一个
     * */
    String executorRouteStrategy() default "ROUND";

    /**
     * 任务禁用/启用
     * @return
     */
    boolean disabled() default false;

    /**
     * 是否更新任务
     */
    boolean overwrite() default false;
}
