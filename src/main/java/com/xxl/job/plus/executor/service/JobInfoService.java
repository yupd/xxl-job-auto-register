package com.xxl.job.plus.executor.service;

import com.xxl.job.plus.executor.model.XxlJobInfo;

import java.util.List;

public interface JobInfoService {

    List<XxlJobInfo> getJobInfo(Integer jobGroupId, String executorHandler);

    Integer addJobInfo(XxlJobInfo xxlJobInfo);

    /**
     * 更新任务
     * @param xxlJobInfo
     * @return
     */
    boolean updateJobInfo(XxlJobInfo xxlJobInfo);

    /**
     * 启停任务
     * @param xxlJobInfo
     * @return
     */
    boolean startOrStopJob(XxlJobInfo xxlJobInfo);

}
