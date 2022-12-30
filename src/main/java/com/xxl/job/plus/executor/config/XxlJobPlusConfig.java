package com.xxl.job.plus.executor.config;

import com.xxl.job.plus.executor.core.XxlJobAutoRegister;
import com.xxl.job.plus.executor.properties.JobProperties;
import com.xxl.job.plus.executor.service.JobGroupService;
import com.xxl.job.plus.executor.service.JobInfoService;
import com.xxl.job.plus.executor.service.JobLoginService;
import com.xxl.job.plus.executor.service.impl.JobGroupServiceImpl;
import com.xxl.job.plus.executor.service.impl.JobInfoServiceImpl;
import com.xxl.job.plus.executor.service.impl.JobLoginServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author : yupd
 * @version: 1.0
 */
@Configuration
@EnableConfigurationProperties({JobProperties.class})
@ConditionalOnProperty(prefix = "xxl.job.plus", name = "enabled", matchIfMissing = true)
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
