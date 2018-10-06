package me.aurora.service.impl;

import cn.hutool.http.HttpStatus;
import me.aurora.config.exception.AuroraException;
import me.aurora.domain.job.Job;
import me.aurora.repository.JobRepo;
import me.aurora.repository.spec.JobSpce;
import me.aurora.service.JobService;
import me.aurora.util.PageUtil;
import me.aurora.util.ValidationUtil;
import me.aurora.util.job.ScheduleUtils;
import org.quartz.CronExpression;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.Map;
import java.util.Optional;

/**
 * @author 郑杰
 * @date 2018/10/05 19:18:05
 */
@Service(value = "jobService")
public class JobServiceImpl implements JobService {

    @Autowired
    private JobRepo jobRepo;

    @Resource(name = "scheduler")
    private Scheduler scheduler;

    @Override
    public Map getJobsInfo(JobSpce jobSpce, Pageable pageable) {
        Page<Job> jobs = jobRepo.findAll(jobSpce,pageable);
        return PageUtil.buildPage(jobs.getContent(),jobs.getTotalElements());
    }

    @Override
    public void insert(Job job) {
        if (!CronExpression.isValidExpression(job.getCronExpression())){
            throw new AuroraException(HttpStatus.HTTP_BAD_REQUEST,"cron表达式格式错误");
        }
        jobRepo.save(job);
        ScheduleUtils.createScheduleJob(scheduler, job);
    }

    @Override
    public Job findById(Long id) {
        Optional<Job> job = jobRepo.findById(id);
        ValidationUtil.isNull(job,"id :" +id +" is not find");
        return job.get();
    }

    @Override
    public void updateStatus(Job job) {
        if (job.getStatus().equals(Job.ScheduleStatus.PAUSE.getValue())) {
            ScheduleUtils.resumeJob(scheduler, job.getId());
            job.setStatus("0");
        } else {
            ScheduleUtils.pauseJob(scheduler,job.getId());
            job.setStatus("1");
        }
        jobRepo.save(job);
    }

    @Override
    public void update(Job old, Job job) {
        if (!CronExpression.isValidExpression(job.getCronExpression())){
            throw new AuroraException(HttpStatus.HTTP_BAD_REQUEST,"cron表达式格式错误");
        }
        job.setStatus(old.getStatus());
        jobRepo.save(job);
        ScheduleUtils.updateScheduleJob(scheduler,job);
    }

    @Override
    public void delete(Job job) {
        ScheduleUtils.deleteScheduleJob(scheduler,job.getId());
        jobRepo.delete(job);
    }
}
