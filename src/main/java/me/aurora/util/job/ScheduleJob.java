package me.aurora.util.job;

import me.aurora.domain.job.Job;
import me.aurora.domain.job.JobLog;
import me.aurora.service.JobLogService;
import me.aurora.service.JobService;
import me.aurora.util.SpringContextUtils;
import org.apache.commons.lang3.StringUtils;
import org.quartz.JobExecutionContext;
import org.quartz.Scheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;
import javax.annotation.Resource;
import java.util.concurrent.*;

/**
 * 用于记录日志
 * 定时任务
 * @author 郑杰
 */
public class ScheduleJob extends QuartzJobBean {

    @Resource(name = "scheduler")
    private Scheduler scheduler;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private ExecutorService service = Executors.newSingleThreadExecutor();

    @Override
    protected void executeInternal(JobExecutionContext context) {
        Job scheduleJob = (Job) context.getMergedJobDataMap().get(Job.JOB_PARAM_KEY);

        // 获取spring bean
        JobLogService logService = (JobLogService) SpringContextUtils.getBean("jobLogService");
        JobService jobService = (JobService) SpringContextUtils.getBean("jobService");

        JobLog log = new JobLog();
        log.setJobId(scheduleJob.getId());
        log.setBeanName(scheduleJob.getBeanName());
        log.setMethodName(scheduleJob.getMethodName());
        log.setParams(scheduleJob.getParams());
        long startTime = System.currentTimeMillis();
        log.setCronExpression(scheduleJob.getCronExpression());
        try {
            // 执行任务
            logger.info("任务准备执行，任务ID：{}", scheduleJob.getId());
            ScheduleRunnable task = new ScheduleRunnable(scheduleJob.getBeanName(), scheduleJob.getMethodName(),
                    scheduleJob.getParams());
            Future<?> future = service.submit(task);
            future.get();
            long times = System.currentTimeMillis() - startTime;
            log.setTimes(times);
            // 任务状态 0：成功 1：失败
            log.setStatus("0");
            logger.info("任务执行完毕，任务ID：{} 总共耗时：{} 毫秒", scheduleJob.getId(), times);
        } catch (Exception e) {
            logger.error("任务执行失败，任务ID：" + scheduleJob.getId(), e);
            long times = System.currentTimeMillis() - startTime;
            log.setTimes(times);
            // 任务状态 0：成功 1：失败
            log.setStatus("1");
            log.setError(StringUtils.substring(e.toString(), 0, 2000));
            //出错就暂停任务
            ScheduleUtils.pauseJob(scheduler,scheduleJob.getId());
            //更新状态
            jobService.updateStatus(scheduleJob);
        } finally {
            logService.insert(log);
        }
    }
}
