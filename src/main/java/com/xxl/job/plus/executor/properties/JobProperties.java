package com.xxl.job.plus.executor.properties;

import com.xxl.job.core.util.IpUtil;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author yupd
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "xxl.job")
public class JobProperties {

    @Value("${xxl.job.admin.addresses}")
    private String adminAddresses;

    @Value("${xxl.job.accessToken}")
    private String accessToken;

    @Value("${xxl.job.executor.appname}")
    private String appname;

    @Value("${xxl.job.executor.address}")
    private String address;

    @Value("${xxl.job.executor.ip}")
    private String ip;

    @Value("${xxl.job.executor.port}")
    private int port;

    @Value("${xxl.job.executor.logpath}")
    private String logPath;

    @Value("${xxl.job.executor.logretentiondays}")
    private int logRetentionDays;

    /**
     * 执行器地址类型：0=自动注册、1=手动录入
     */
    @Value("${xxl.job.executor.addressType:1}")
    private Integer addressType;

    @Value("${xxl.job.admin.username}")
    private String username;

    @Value("${xxl.job.admin.password}")
    private String password;

    public String getExecutorAddress() {
        ip = ip != null && ip.trim().length() > 0 ? ip : IpUtil.getIp();
        if (address == null || address.trim().length() == 0) {
            return "http://" + IpUtil.getIpPort(ip, port);
        }
        return address;
    }

}
