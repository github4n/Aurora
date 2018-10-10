package me.aurora.config.job;

import lombok.extern.slf4j.Slf4j;
import me.aurora.domain.job.Job;
import me.aurora.repository.JobRepo;
import me.aurora.util.job.ScheduleUtils;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.util.List;

/**
 * @author 郑杰
 * @date 2018/10/06 11:09:35
 */
@Component
@Slf4j
public class MyJobRunner implements ApplicationRunner {

    @Autowired
    private JobRepo jobRepo;

    @Resource(name = "scheduler")
    private Scheduler scheduler;

    /**
     * 项目启动时重新激活启用的定时任务
     * @param applicationArguments
     * @throws Exception
     */
    @Override
    public void run(ApplicationArguments applicationArguments){

        System.out.println("--------------------开始注入定时任务---------------------");

        List<Job> jobs = jobRepo.findAll();
        jobs.forEach(scheduleJob -> {
            ScheduleUtils.createScheduleJob(scheduler, scheduleJob);
        });
    }
}
