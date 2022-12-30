package com.xxl.job.plus.executor.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author yupd
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "xxl.job")
public class JobProperties {

    private String adminAddresses;

    private String appName;

    private String accessToken;

    private String address;

    private String ip;

    private int port;

    private String logPath;

    private int logRetentionDays;

    private String title;

    private Integer addressType;

    private String addressList;

    private String username;

    private String password;
}
