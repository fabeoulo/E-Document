/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.advantech.test;

import com.advantech.helper.HibernateObjectPrinter;
import com.advantech.quartzJob.BackupDataToExcel;
import com.advantech.quartzJob.StandardTimeUpload;
import com.advantech.quartzJob.SyncEmployeeZoneUser;
import com.advantech.quartzJob.SyncPreAssyModuleQty;
import com.advantech.quartzJob.SyncWorktime;
import com.advantech.quartzJob.SyncWorktimeM4f;
import com.advantech.quartzJob.WorktimeEventLog;
import com.advantech.quartzJob.WorktimeEventLog1;
import com.advantech.quartzJob.WorktimeFieldValueRetrieve;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Wei.Cheng
 */
@WebAppConfiguration
@ContextConfiguration(locations = {
    "classpath:servlet-context.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class QuartzJobTest {

    @Autowired
    @Qualifier("worktimeEventLog1")
    private WorktimeEventLog1 job1;

    @Autowired
    @Qualifier("worktimeEventLog")
    private WorktimeEventLog job8;

    @Autowired
    @Qualifier("standardTimeUpload")
    private StandardTimeUpload job2;

    @Autowired
    @Qualifier("worktimeFieldValueRetrieve")
    private WorktimeFieldValueRetrieve job3;

    @Autowired
    @Qualifier("syncEmployeeZoneUser")
    private SyncEmployeeZoneUser job4;

    @Autowired
    @Qualifier("backupDataToExcel")
    private BackupDataToExcel job5;

    @Autowired
    private SyncPreAssyModuleQty job7;

    @Autowired
    private SyncWorktimeM4f job9;

    @Autowired
    private SyncWorktime job10;

//    @Test
//    @Transactional("tx1")
//    @Rollback(true)
    public void testSyncWorktime() throws Exception {
        job10.syncModelFromM9ie();
    }
    
//    @Test
//    @Transactional("tx2")
//    @Rollback(true)
    public void testSyncWorktimeM4f() throws Exception {
        job9.syncModelFromM9ie();
    }

//    @Test
    public void testSyncPreAssyModuleQty() throws Exception {
        job7.execute();
    }

//    @Test
    public void testBackupDataToExcel() throws Exception {
        job5.backupToDisk();
    }

//    @Test
    public void testWorktimeEventLog1() {
        job1.execute();
    }

//    @Test
    public void testWorktimeEventLog() {
        job8.execute();
    }

//    @Test
    public void testStandardTimeUpload() {

        job2.uploadToMes();
//        HibernateObjectPrinter.print(job2.getMailByNotification("worktime_ie_alarm"));
    }

//    @Test
    public void testWorktimeFieldValueRetrieve() {

        HibernateObjectPrinter.print(job3.getMailByNotification("worktime_ie_alarm"));
    }

//    @Test
    public void testSyncEmployeeZoneUser() {
        job4.execute();
    }

}
