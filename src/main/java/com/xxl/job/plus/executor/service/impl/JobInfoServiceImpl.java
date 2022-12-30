package com.xxl.job.plus.executor.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.xxl.job.plus.executor.model.XxlJobInfo;
import com.xxl.job.plus.executor.properties.JobProperties;
import com.xxl.job.plus.executor.service.JobInfoService;
import com.xxl.job.plus.executor.service.JobLoginService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author : Hydra
 * @date: 2022/9/20 10:36
 * @version: 1.0
 */
public class JobInfoServiceImpl implements JobInfoService {

    @Autowired
    private JobProperties jobProperties;

    @Autowired
    private JobLoginService jobLoginService;

    @Override
    public List<XxlJobInfo> getJobInfo(Integer jobGroupId, String executorHandler) {
        String url = jobProperties.getAdminAddresses() + "/jobinfo/pageList";
        HttpResponse response = HttpRequest.post(url)
                .form("jobGroup", jobGroupId)
                .form("executorHandler", executorHandler)
                .form("triggerStatus", -1)
                .cookie(jobLoginService.getCookie())
                .execute();

        String body = response.body();
        JSONArray array = JSONUtil.parse(body).getByPath("data", JSONArray.class);
        List<XxlJobInfo> list = array.stream()
                .map(o -> JSONUtil.toBean((JSONObject) o, XxlJobInfo.class))
                .collect(Collectors.toList());

        return list;
    }

    @Override
    public Integer addJobInfo(XxlJobInfo xxlJobInfo) {
        String url = jobProperties.getAdminAddresses() + "/jobinfo/add";
        Map<String, Object> paramMap = BeanUtil.beanToMap(xxlJobInfo);
        HttpResponse response = HttpRequest.post(url)
                .form(paramMap)
                .cookie(jobLoginService.getCookie())
                .execute();

        JSON json = JSONUtil.parse(response.body());
        Object code = json.getByPath("code");
        if (code.equals(200)) {
            return Convert.toInt(json.getByPath("content"));
        }
        throw new RuntimeException("add jobInfo error!");
    }

    @Override
    public boolean updateJobInfo(XxlJobInfo xxlJobInfo) {
        String url = jobProperties.getAdminAddresses() + "/jobinfo/update";
        Map<String, Object> paramMap = BeanUtil.beanToMap(xxlJobInfo);
        HttpResponse response = HttpRequest.post(url)
                .form(paramMap)
                .cookie(jobLoginService.getCookie())
                .execute();
        JSON json = JSONUtil.parse(response.body());
        Object code = json.getByPath("code");
        return code.equals(200);
    }

    @Override
    public boolean startOrStopJob(XxlJobInfo xxlJobInfo) {
        String action = xxlJobInfo.getTriggerStatus() == 0 ? "stop" : "start";
        String url = jobProperties.getAdminAddresses() + "/jobinfo/" + action;
        Map<String, Object> paramMap = BeanUtil.beanToMap(xxlJobInfo);
        HttpResponse response = HttpRequest.post(url)
                .form(paramMap)
                .cookie(jobLoginService.getCookie())
                .execute();
        JSON json = JSONUtil.parse(response.body());
        Object code = json.getByPath("code");
        return code.equals(200);
    }
}
