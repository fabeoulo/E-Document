/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.advantech.test;

import com.advantech.quartzJob.BackupDataToExcel;
import com.advantech.quartzJob.StandardTimeUpload;
import com.advantech.quartzJob.WorktimeEventLog;
import com.advantech.quartzJob.WorktimeEventLog1;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 *
 * @author Wei.Cheng
 */
@WebAppConfiguration
@ContextConfiguration(locations = {
    "classpath:servlet-context.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class TestQuartzJobs {

    @Autowired
    private BackupDataToExcel backupExcel;

    @Autowired
    private StandardTimeUpload standardTimeUpload;
    
    @Autowired
    private WorktimeEventLog worktimeEventLog;

    @Autowired
    private WorktimeEventLog1 worktimeEventLog1;
    
//    @Test
    public void testTestLineTypeRecord() throws JobExecutionException, Exception {
        backupExcel.backupToDisk();
    }

//    @Test
    public void testSTUpload() {
        standardTimeUpload.uploadToMes();
    }

//    @Test
    public void testWorktimeEventLog() {
        worktimeEventLog.execute();
    }
    
//    @Test
    public void testWorktimeEventLog1() {
        worktimeEventLog1.execute();
    }
}
