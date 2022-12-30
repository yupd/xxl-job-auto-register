package com.xxl.job.plus.executor.service.impl;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.xxl.job.plus.executor.properties.JobProperties;
import com.xxl.job.plus.executor.service.JobLoginService;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.HttpCookie;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author : Hydra
 * @date: 2022/9/19 17:49
 * @version: 1.0
 */
public class JobLoginServiceImpl implements JobLoginService {

    @Autowired
    private JobProperties jobProperties;

    private final Map<String, String> loginCookie = new HashMap<>();

    @Override
    public void login() {
        String url = jobProperties.getAdminAddresses() + "/login";
        HttpResponse response = HttpRequest.post(url)
                .form("userName", jobProperties.getUsername())
                .form("password", jobProperties.getPassword())
                .execute();
        List<HttpCookie> cookies = response.getCookies();
        Optional<HttpCookie> cookieOpt = cookies.stream()
                .filter(cookie -> cookie.getName().equals("XXL_JOB_LOGIN_IDENTITY")).findFirst();
        if (!cookieOpt.isPresent()) {
            throw new RuntimeException("get xxl-job cookie error!");
        }

        String value = cookieOpt.get().getValue();
        loginCookie.put("XXL_JOB_LOGIN_IDENTITY", value);
    }

    @Override
    public String getCookie() {
        for (int i = 0; i < 3; i++) {
            String cookieStr = loginCookie.get("XXL_JOB_LOGIN_IDENTITY");
            if (cookieStr != null) {
                return "XXL_JOB_LOGIN_IDENTITY=" + cookieStr;
            }
            login();
        }
        throw new RuntimeException("get xxl-job cookie error!");
    }
}
