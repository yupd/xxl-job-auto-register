package com.xxl.job.plus.executor.config;

import com.xxl.job.plus.executor.core.XxlJobAutoRegister;
import com.xxl.job.plus.executor.service.JobGroupService;
import com.xxl.job.plus.executor.service.JobInfoService;
import com.xxl.job.plus.executor.service.JobLoginService;
import com.xxl.job.plus.executor.service.impl.JobGroupServiceImpl;
import com.xxl.job.plus.executor.service.impl.JobInfoServiceImpl;
import com.xxl.job.plus.executor.service.impl.JobLoginServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author : Hydra
 * @date: 2022/9/20 15:59
 * @version: 1.0
 */
@Configuration
public class XxlJobPlusConfig {

    @Bean
    @ConditionalOnMissingBean
    public XxlJobAutoRegister xxlJobAutoRegister(){
        return new XxlJobAutoRegister();
    }

    @Bean
    @ConditionalOnMissingBean
    public JobGroupService jobGroupService(){
        return new JobGroupServiceImpl();
    }

    @Bean
    @ConditionalOnMissingBean
    public JobInfoService jobInfoService(){
        return new JobInfoServiceImpl();
    }

    @Bean
    @ConditionalOnMissingBean
    public JobLoginService jobLoginService(){
        return new JobLoginServiceImpl();
    }
}
