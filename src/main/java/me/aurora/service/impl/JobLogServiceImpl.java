package me.aurora.service.impl;

import me.aurora.domain.job.JobLog;
import me.aurora.repository.JobLogRepo;
import me.aurora.repository.spec.JobLogSpce;
import me.aurora.service.JobLogService;
import me.aurora.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author 郑杰
 * @date 2018/10/05 19:51:22
 */
@Service(value = "jobLogService")
public class JobLogServiceImpl implements JobLogService {

    @Autowired
    private JobLogRepo jobLogRepo;

    @Override
    public void insert(JobLog log) {
        jobLogRepo.save(log);
    }

    @Override
    public Map getJobLogsInfo(JobLogSpce jobLogSpce, Pageable pageable) {
        Page<JobLog> jobLogs = jobLogRepo.findAll(jobLogSpce,pageable);
        return PageUtil.buildPage(jobLogs.getContent(),jobLogs.getTotalElements());
    }
}
