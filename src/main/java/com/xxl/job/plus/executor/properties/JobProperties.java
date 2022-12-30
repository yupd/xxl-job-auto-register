package com.xxl.job.plus.executor.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author yupd
 */
@ConfigurationProperties(prefix = "xxl.job")
public class JobProperties {

    private String adminAddresses;

    private String appName;

    private String title;

    private Integer addressType;

    private String addressList;

    private String username;

    private String password;
}
