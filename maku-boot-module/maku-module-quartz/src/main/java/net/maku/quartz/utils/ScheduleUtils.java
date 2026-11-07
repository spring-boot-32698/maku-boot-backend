package net.maku.quartz.utils;

import net.maku.framework.common.exception.ServerException;
import net.maku.quartz.entity.ScheduleJobEntity;
import net.maku.quartz.enums.ScheduleConcurrentEnum;
import net.maku.quartz.enums.ScheduleStatusEnum;
import org.quartz.*;

/**
 * Scheduled task utility class
 *
 * @author Amu babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
public class ScheduleUtils {
    private final static String JOB_NAME = "TASK_NAME_";
    /**
     * Task scheduling parameter key
     */
    public static final String JOB_PARAM_KEY = "JOB_PARAM_KEY";

    /**
     * Get the quartz task class
     */
    public static Class<? extends Job> getJobClass(ScheduleJobEntity scheduleJob) {
        if (scheduleJob.getConcurrent().equals(ScheduleConcurrentEnum.NO.getValue())) {
            return ScheduleDisallowConcurrentExecution.class;
        } else {
            return ScheduleConcurrentExecution.class;
        }
    }

    /**
     * Get trigger key
     */
    public static TriggerKey getTriggerKey(ScheduleJobEntity scheduleJob) {
        return TriggerKey.triggerKey(JOB_NAME + scheduleJob.getId(), scheduleJob.getJobGroup());
    }

    /**
     * Get jobKey
     */
    public static JobKey getJobKey(ScheduleJobEntity scheduleJob) {
        return JobKey.jobKey(JOB_NAME + scheduleJob.getId(), scheduleJob.getJobGroup());
    }

    /**
     * Create scheduled task
     */
    public static void createScheduleJob(Scheduler scheduler, ScheduleJobEntity scheduleJob) {
        try {
            // job key
            JobKey jobKey = getJobKey(scheduleJob);
            // Build job info
            JobDetail jobDetail = JobBuilder.newJob(getJobClass(scheduleJob)).withIdentity(jobKey).build();

            // Expression schedule builder
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(scheduleJob.getCronExpression())
                    .withMisfireHandlingInstructionDoNothing();

            // Build a new trigger from the new cronExpression
            CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(getTriggerKey(scheduleJob))
                    .withSchedule(scheduleBuilder).build();

            // Put in parameters, which the method can read at runtime
            jobDetail.getJobDataMap().put(JOB_PARAM_KEY, scheduleJob);
            // Add the task to Quartz
            scheduler.scheduleJob(jobDetail, trigger);

            // Check whether it exists
            if (scheduler.checkExists(jobKey)) {
                // To avoid data problems at creation, remove first and then create
                scheduler.deleteJob(jobKey);
            }

            // Check whether the task has expired
            if (CronUtils.getNextExecution(scheduleJob.getCronExpression()) != null) {
                // Execute scheduled job
                scheduler.scheduleJob(jobDetail, trigger);
            }

            // Pause task
            if (scheduleJob.getStatus().equals(ScheduleStatusEnum.PAUSE.getValue())) {
                scheduler.pauseJob(jobKey);
            }
        } catch (SchedulerException e) {
            throw new ServerException("Failed to create scheduled task", e);
        }
    }


    /**
     * Run the task now
     */
    public static void run(Scheduler scheduler, ScheduleJobEntity scheduleJob) {
        try {
            // Parameter
            JobDataMap dataMap = new JobDataMap();
            dataMap.put(JOB_PARAM_KEY, scheduleJob);

            JobKey jobKey = getJobKey(scheduleJob);
            if (scheduler.checkExists(jobKey)) {
                scheduler.triggerJob(jobKey, dataMap);
            }
        } catch (SchedulerException e) {
            throw new ServerException("Failed to execute scheduled task", e);
        }
    }

    /**
     * Pause task
     */
    public static void pauseJob(Scheduler scheduler, ScheduleJobEntity scheduleJob) {
        try {
            scheduler.pauseJob(getJobKey(scheduleJob));
        } catch (SchedulerException e) {
            throw new ServerException("Failed to pause scheduled task", e);
        }
    }

    /**
     * Resume task
     */
    public static void resumeJob(Scheduler scheduler, ScheduleJobEntity scheduleJob) {
        try {
            scheduler.resumeJob(getJobKey(scheduleJob));
        } catch (SchedulerException e) {
            throw new ServerException("Failed to resume scheduled task", e);
        }
    }

    /**
     * Update scheduled task
     */
    public static void updateSchedulerJob(Scheduler scheduler, ScheduleJobEntity scheduleJob) {
        // Check whether it exists
        JobKey jobKey = ScheduleUtils.getJobKey(scheduleJob);

        try {
            // To avoid data problems at creation, remove first and then create
            if (scheduler.checkExists(jobKey)) {
                scheduler.deleteJob(jobKey);
            }
        } catch (SchedulerException e) {
            throw new ServerException("Failed to update scheduled task", e);
        }

        ScheduleUtils.createScheduleJob(scheduler, scheduleJob);
    }

    /**
     * Delete scheduled task
     */
    public static void deleteScheduleJob(Scheduler scheduler, ScheduleJobEntity scheduleJob) {
        try {
            scheduler.deleteJob(getJobKey(scheduleJob));
        } catch (SchedulerException e) {
            throw new ServerException("Failed to delete scheduled task", e);
        }
    }
}