package com.xxl.job.plus.executor.service.impl;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.xxl.job.plus.executor.model.XxlJobGroup;
import com.xxl.job.plus.executor.properties.JobProperties;
import com.xxl.job.plus.executor.service.JobGroupService;
import com.xxl.job.plus.executor.service.JobLoginService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author : Hydra
 * @date: 2022/9/19 17:34
 * @version: 1.0
 */
public class JobGroupServiceImpl implements JobGroupService {

    @Autowired
    private JobProperties jobProperties;

    @Autowired
    private JobLoginService jobLoginService;

    @Override
    public List<XxlJobGroup> getJobGroup() {
        String url=jobProperties.getAdminAddresses()+"/jobgroup/pageList";
        HttpResponse response = HttpRequest.post(url)
                .form("appname", jobProperties.getAppName())
                .form("title", jobProperties.getTitle())
                .cookie(jobLoginService.getCookie())
                .execute();

        String body = response.body();
        JSONArray array = JSONUtil.parse(body).getByPath("data", JSONArray.class);
        List<XxlJobGroup> list = array.stream()
                .map(o -> JSONUtil.toBean((JSONObject) o, XxlJobGroup.class))
                .collect(Collectors.toList());

        return list;
    }

    @Override
    public boolean autoRegisterGroup() {
        String url=jobProperties.getAdminAddresses()+"/jobgroup/save";
        HttpRequest httpRequest = HttpRequest.post(url)
                .form("appname", jobProperties.getAppName())
                .form("title", jobProperties.getTitle());

        httpRequest.form("addressType", jobProperties.getAddressType());
        if (jobProperties.getAddressType().equals(1)){
            if (Strings.isBlank(jobProperties.getAddressList())){
                throw new RuntimeException("手动录入模式下,执行器地址列表不能为空");
            }
            httpRequest.form("addressList", jobProperties.getAddressList());
        }

        HttpResponse response = httpRequest.cookie(jobLoginService.getCookie())
                .execute();
        Object code = JSONUtil.parse(response.body()).getByPath("code");
        return code.equals(200);
    }

    @Override
    public boolean preciselyCheck() {
        List<XxlJobGroup> jobGroup = getJobGroup();
        Optional<XxlJobGroup> has = jobGroup.stream()
                .filter(xxlJobGroup -> xxlJobGroup.getAppname().equals(jobProperties.getAppName())
                        && xxlJobGroup.getTitle().equals(jobProperties.getTitle()))
                .findAny();
        return has.isPresent();
    }

}
