package net.maku.quartz.utils;

import cn.hutool.extra.spring.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import net.maku.framework.common.utils.ExceptionUtils;
import net.maku.quartz.entity.ScheduleJobEntity;
import net.maku.quartz.entity.ScheduleJobLogEntity;
import net.maku.quartz.enums.ScheduleStatusEnum;
import net.maku.quartz.service.ScheduleJobLogService;
import org.apache.commons.lang3.StringUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.Date;

@Slf4j
public abstract class AbstractScheduleJob implements Job {
    private static final ThreadLocal<Date> threadLocal = new ThreadLocal<>();

    @Override
    public void execute(JobExecutionContext context) {
        ScheduleJobEntity scheduleJob = new ScheduleJobEntity();
        BeanUtils.copyProperties(context.getMergedJobDataMap().get(ScheduleUtils.JOB_PARAM_KEY), scheduleJob);

        try {
            threadLocal.set(new Date());
            doExecute(scheduleJob);
            saveLog(scheduleJob, null);
        } catch (Exception e) {
            log.error("Task execution failed, task ID:{}", scheduleJob.getId(), e);
            saveLog(scheduleJob, e);
        }
    }

    /**
     * Execute spring bean method
     */
    protected void doExecute(ScheduleJobEntity scheduleJob) throws Exception {
        log.info("Preparing to execute task, task ID:{}", scheduleJob.getId());

        Object bean = SpringUtil.getBean(scheduleJob.getBeanName());
        Method method = bean.getClass().getDeclaredMethod(scheduleJob.getMethod(), String.class);
        method.invoke(bean, scheduleJob.getParams());

        log.info("Task execution completed, task ID:{}", scheduleJob.getId());
    }

    /**
     * Save log
     */
    protected void saveLog(ScheduleJobEntity scheduleJob, Exception e) {
        Date startTime = threadLocal.get();
        threadLocal.remove();

        // Total execution time
        long times = System.currentTimeMillis() - startTime.getTime();

        // Save execution record
        ScheduleJobLogEntity log = new ScheduleJobLogEntity();
        log.setJobId(scheduleJob.getId());
        log.setJobName(scheduleJob.getJobName());
        log.setJobGroup(scheduleJob.getJobGroup());
        log.setBeanName(scheduleJob.getBeanName());
        log.setMethod(scheduleJob.getMethod());
        log.setParams(scheduleJob.getParams());
        log.setTimes(times);
        log.setCreateTime(LocalDateTime.now());

        if (e != null) {
            log.setStatus(ScheduleStatusEnum.PAUSE.getValue());
            String error = StringUtils.substring(ExceptionUtils.getExceptionMessage(e), 0, 2000);
            log.setError(error);
        } else {
            log.setStatus(ScheduleStatusEnum.NORMAL.getValue());
        }

        // Save log
        SpringUtil.getBean(ScheduleJobLogService.class).save(log);
    }

}
