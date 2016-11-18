/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.advantech.helper;

import com.advantech.service.BabLineTypeFacade;
import com.advantech.service.BasicLineTypeFacade;
import com.advantech.service.TestLineTypeFacade;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static org.quartz.TriggerKey.triggerKey;

/**
 *
 * @author Wei.Cheng
 */
public class CronTrigMod {

    private static final Logger log = LoggerFactory.getLogger(CronTrigMod.class);

    public static Map changedJobKey = new HashMap();

    private final String mainJobKey = "DailyJobWorker";

    private Scheduler scheduler;
    private static CronTrigMod instance;

    BasicLineTypeFacade bf = BabLineTypeFacade.getInstance();
    BasicLineTypeFacade tf = TestLineTypeFacade.getInstance();

    private CronTrigMod() {
        try {
            scheduler = new StdSchedulerFactory().getScheduler();
        } catch (SchedulerException ex) {
            log.error(ex.toString());
        }
    }

//    http://openhome.cc/Gossip/DesignPattern/SingletonPattern.htm
    @SuppressWarnings("DoubleCheckedLocking")
    public static CronTrigMod getInstance() {
        return instance == null ? new CronTrigMod() : instance;
    }

    public JobKey createJobKey(String jobName) {
        return new JobKey(jobName, jobName + "Group");
    }

    public TriggerKey createTriggerKey(String triggerName) {
        return new TriggerKey(triggerName, triggerName + "Group");
    }

    public JobDetail createJobDetail(String jobName, Class jobClass, Map<String, Object> jobData) {
        return JobBuilder.newJob(jobClass)
                .withIdentity(jobName, jobName + "Group")
                .usingJobData(new JobDataMap(jobData))
                .build();
    }

    public String getTriggerByJobKey(String jobKey) {
        try {
            Trigger t = scheduler.getTrigger(new TriggerKey(jobKey));
            return ((CronTrigger) t).getCronExpression();
        } catch (SchedulerException ex) {
            log.error(ex.toString());
            return null;
        }
    }

    public boolean triggerPauseOrResume(String order) {
        try {
            if ("pause".equals(order)) {
                enterTestMode();
            } else if ("resume".equals(order)) {
                outTestMode();
            }
        } catch (SchedulerException ex) {
            log.error(ex.toString());
            return false;
        }
        return true;
    }

    private void enterTestMode() throws SchedulerException {
//        scheduler.standby();
        changeResultOutputFlag(false);
        log.info("Stop result output");
    }

    private void outTestMode() throws SchedulerException {
//        scheduler.start();
        changeResultOutputFlag(true);
        log.info("Resume result output");
    }

    private void changeResultOutputFlag(boolean flag) {
        bf.isNeedToOutput(flag);
        tf.isNeedToOutput(flag);
    }

    public void updateMainJobCronExpressionToDefault() throws SchedulerException {
        // retrieve the trigger
        Object trig = changedJobKey.get(mainJobKey);
        Trigger oldTrigger = (trig != null) ? (Trigger) trig : scheduler.getTrigger(triggerKey(mainJobKey));
        scheduler.rescheduleJob(oldTrigger.getKey(), oldTrigger);
        changedJobKey.remove(mainJobKey);
    }

    public void updateMainJobCronExpression() throws SchedulerException {
        // retrieve the trigger
        Object trig = changedJobKey.get(mainJobKey);

        Trigger oldTrigger = (trig != null) ? (Trigger) trig : scheduler.getTrigger(triggerKey(mainJobKey));
        changedJobKey.put(mainJobKey, oldTrigger);

        // obtain a builder that would produce the trigger
        TriggerBuilder tb = oldTrigger.getTriggerBuilder();

        // update the schedule associated with the builder, and build the new trigger
        // (other builder methods could be called, to change the trigger in any desired way)
        Trigger newTrigger = tb.withSchedule(SimpleScheduleBuilder.simpleSchedule()
                .withIntervalInMinutes(15)
                .withRepeatCount(1)
        ).build();

        scheduler.rescheduleJob(oldTrigger.getKey(), newTrigger);
    }

    public boolean updateCronExpression(String triggerKey, String cronExpression, Integer executeNow) {
        if (StringUtils.isBlank(triggerKey) || StringUtils.isBlank(cronExpression)) {
            log.error("参数错误");
            return false;
        }
        if (executeNow == null) {
            executeNow = 0;
        }
        triggerKey = triggerKey.trim();
        cronExpression = cronExpression.trim();
        TriggerKey key = new TriggerKey(triggerKey);//动态条件  
        try {
            Trigger oldTrigger = scheduler.getTrigger(key);
            if (oldTrigger instanceof CronTriggerImpl) {
                CronTriggerImpl trigger = (CronTriggerImpl) oldTrigger;
                trigger.setCronExpression(cronExpression);//动态传入的条件  
                //不立即执行  
                if (executeNow == 0) {
                    trigger.setStartTime(new Date());//防止立即生效  
                }
                scheduler.rescheduleJob(trigger.getKey(), trigger);
            }
        } catch (SchedulerException | ParseException e) {
            log.error("更新cron定时任务运行时间失败[triggerKey=" + triggerKey + "]:", e);
            return false;
        }
        return true;
    }

    public void generateAJob(Class jobClass, String jobName, String crontrigger) throws SchedulerException {
        this.generateAJob(jobClass, this.createJobKey(jobName), this.createTriggerKey(jobName), crontrigger);
    }

    public void generateAJob(Class jobClass, JobKey jobKey, TriggerKey trigKey, String crontrigger) throws SchedulerException {
        if (scheduler.isStarted()) {
            if (!isKeyInScheduleExist(jobKey)) {
                JobDetail job = JobBuilder.newJob(jobClass).withIdentity(jobKey).build();
                CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(trigKey)
                        .withSchedule(CronScheduleBuilder.cronSchedule(crontrigger)).build();
                scheduler.scheduleJob(job, trigger);
            } else {
                log.info("The job with key name " + jobKey + " is exist.");
            }
        }
    }

    public void generateAJob(JobDetail job, TriggerKey trigKey, String crontrigger) throws SchedulerException {
        if (scheduler.isStarted()) {
            JobKey jobKey = job.getKey();
            if (!isKeyInScheduleExist(jobKey)) {
                CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(trigKey)
                        .withSchedule(CronScheduleBuilder.cronSchedule(crontrigger)).build();
                scheduler.scheduleJob(job, trigger);
            } else {
                log.info("The job with key name " + jobKey + " is exist.");
            }
        }
    }

    public void removeAJob(String jobName) throws SchedulerException {
        this.removeAJob(this.createJobKey(jobName), this.createTriggerKey(jobName));
    }

    public void removeAJob(JobKey jobKey, TriggerKey trigKey) throws SchedulerException {
        if (scheduler.isStarted()) {
            if (isKeyInScheduleExist(jobKey) && isKeyInScheduleExist(trigKey)) {
                scheduler.unscheduleJob(trigKey);
                scheduler.deleteJob(jobKey);
            }
        }
    }

    public boolean isJobInScheduleExist(String jobName) throws SchedulerException {
        JobKey jobKey = this.createJobKey(jobName);
        return this.isKeyInScheduleExist(jobKey);
    }

    public boolean isKeyInScheduleExist(Object key) throws SchedulerException {
        if (key instanceof JobKey) {
            return scheduler.checkExists((JobKey) key);
        } else if (key instanceof TriggerKey) {
            return scheduler.checkExists((TriggerKey) key);
        } else if (key instanceof String) {
            return scheduler.checkExists(new JobKey((String) key));
        } else {
            return false;
        }
    }
}
