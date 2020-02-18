/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.advantech.test;

import com.advantech.facade.BabLineTypeFacade;
import com.advantech.helper.CustomPasswordEncoder;
import com.advantech.helper.HibernateObjectPrinter;
import com.advantech.model.Bab;
import com.advantech.model.BabCollectModeChangeEvent;
import com.advantech.model.BabDataCollectMode;
import com.advantech.model.BabSettingHistory;
import com.advantech.model.Floor;
import com.advantech.model.Fqc;
import com.advantech.model.FqcLine;
import com.advantech.model.Line;
import com.advantech.model.PrepareSchedule;
import com.advantech.model.TagNameComparison;
import com.advantech.model.User;
import com.advantech.model.view.BabProcessDetail;
import com.advantech.quartzJob.HandleUncloseBab;
import com.advantech.service.BabCollectModeChangeEventService;
import com.advantech.service.BabPassStationRecordService;
import com.advantech.service.BabSensorLoginRecordService;
import com.advantech.service.BabService;
import com.advantech.service.BabSettingHistoryService;
import com.advantech.service.FloorService;
import com.advantech.service.FqcLineService;
import com.advantech.service.FqcProductivityHistoryService;
import com.advantech.service.FqcService;
import com.advantech.service.LineBalancingService;
import com.advantech.service.LineService;
import com.advantech.service.LineUserReferenceService;
import com.advantech.service.PrepareScheduleService;
import com.advantech.service.TagNameComparisonService;
import com.advantech.service.UserService;
import com.advantech.webservice.WebServiceRV;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toList;
import javax.mail.MessagingException;
import org.apache.commons.lang3.math.NumberUtils;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
public class TestService {

    @Autowired
    private BabService babService;

    @Autowired
    private LineBalancingService lineBalancingService;

    @Autowired
    private BabSettingHistoryService babSettingHistoryService;

    @Autowired
    private BabSensorLoginRecordService babSensorLoginRecordService;

    @Autowired
    private TagNameComparisonService tagNameComparisonService;

    @Autowired
    private UserService userService;

    @Autowired
    private BabLineTypeFacade bf;

    @Autowired
    private CustomPasswordEncoder encoder;

    @Autowired
    private FqcService fqcService;

    @Autowired
    private FqcLineService fqcLineService;

    @Value("${endpoint.quartz.trigger}")
    private String endpointPollingCron;

    @Autowired
    private FqcProductivityHistoryService productivityService;

    @Autowired
    private BabPassStationRecordService passStationService;

    @Autowired
    private BabCollectModeChangeEventService babCollectModeChangeEventService;

//    @Test
    @Transactional
    @Rollback(true)
    public void testLineBalancingService() throws JobExecutionException {
        new HandleUncloseBab().executeInternal(null);
    }

//    @Test
    @Transactional
    @Rollback(true)
    public void testBabSettingHistoryService() {

        BabSettingHistory setting2 = babSettingHistoryService.findProcessingByTagName("L8-S-3");
        assertNotNull(setting2);

        assertEquals(setting2.getTagName().getName(), "L8-S-3");
    }

//    @Test
    @Transactional
    @Rollback(true)
    public void testBabSensorLoginRecordService() throws JsonProcessingException {

        List l = babSensorLoginRecordService.findByLine(3);
        assertTrue(!l.isEmpty());

        HibernateObjectPrinter.print(l);
    }

//    @Test
    public void testBabSettingHistory() throws JsonProcessingException {

        List<BabSettingHistory> allSettings = babSettingHistoryService.findProcessing();
        Bab b = babService.findByPrimaryKey(12991);

        HibernateObjectPrinter.print(allSettings.get(0));

        List<BabSettingHistory> l = allSettings.stream()
                .filter(rec -> rec.getBab().getId() == b.getId()).collect(toList());

        HibernateObjectPrinter.print(l.get(0));

        assertTrue(!l.isEmpty());

        HibernateObjectPrinter.print(allSettings);
        HibernateObjectPrinter.print(l);
    }

//    @Test
//    @Transactional
//    @Rollback(false)
    public void testHibernateInitialize() {
        TagNameComparison t4 = tagNameComparisonService.findByLampSysTagName("L2-S-4");
        TagNameComparison t7 = tagNameComparisonService.findByLampSysTagName("L2-S-7");
        assertNotNull(t4);
        assertNotNull(t7);

//        babService.checkAndInsert(b, endpointPollingCron, t7);
    }

//    @Test
    public void testBabSaving() {
        try {

            Bab b = babService.findByPrimaryKey(14227);

            lineBalancingService.sendMail(b, 1, 1, 1);

        } catch (MessagingException ex) {

        } catch (Exception ex1) {
            System.out.println(ex1);
        }
    }

//    @Test
    @Transactional
    @Rollback(false)
    public void testUserService() {

        int[] ids = {
            33,
            34,
            35,
            36,
            37
        };

        for (int id : ids) {
            User u = userService.findByPrimaryKey(id);
            u.setPassword(encoder.encode(u.getPassword()));
            userService.update(u);
        }
    }

//    @Test
    @Transactional
    @Rollback(true)
    public void testFqc() {
        Fqc fqc = fqcService.findByPrimaryKey(1);
        assertNotNull(fqc);
        assertEquals(1, fqc.getId());
        assertEquals("PIGB304ZA", fqc.getPo());

        FqcLine fqcLine = fqcLineService.findByPrimaryKey(8);
        assertNotNull(fqcLine);
        assertEquals(8, fqcLine.getId());
        assertEquals("L9", fqcLine.getName());

        assertEquals(fqc.getFqcLine(), fqcLine);
    }

//    @Test
    public void testProductivityService() {

    }

    @Autowired
    private WebServiceRV rv;

//    @Test
    @Transactional
    @Rollback(true)
    public void testBarcode() {
        Bab b = babService.findByPrimaryKey(29710);
        assertNotNull(b);

        String tag1 = "NA-S-1";
        String tag2 = "NA-S-2";

        String barcode = "TPAB810772";

        try {
            //Assert can't add next barcode when current barcode is not finished.
            passStationService.checkStationInfoAndInsert(b, tag1, barcode);
            String nextBc = findBarcode(barcode, 1);
            passStationService.checkStationInfoAndInsert(b, tag1, nextBc);

            assertFalse("test1 fail", true);
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        }

        try {
            //Assert barcode can't insert next station when previous station is not finished
            passStationService.checkStationInfoAndInsert(b, tag2, barcode);

            assertFalse("test2 fail", true);
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        }

        //Test normal insert
        passStationService.checkStationInfoAndInsert(b, tag1, barcode);
        passStationService.checkStationInfoAndInsert(b, tag2, barcode);
        passStationService.checkStationInfoAndInsert(b, tag2, barcode);

        String nextBc = findBarcode(barcode, 1);

        passStationService.checkStationInfoAndInsert(b, tag1, nextBc);
        passStationService.checkStationInfoAndInsert(b, tag1, nextBc);
        passStationService.checkStationInfoAndInsert(b, tag2, nextBc);
        passStationService.checkStationInfoAndInsert(b, tag2, nextBc);

    }

    private String findBarcode(String barcode, int c) {
        Pattern p = Pattern.compile("-?\\d+");
        Matcher m = p.matcher(barcode);
        String st = null;
        while (m.find()) {
            st = m.group();
        }
        barcode = barcode.replace(st, Integer.toString((NumberUtils.createInteger(st)) + c));
        return barcode;
    }

//    @Test
    @Transactional
    @Rollback(false)
    public void testBabCollectModeChangeEventService() {
        List<BabCollectModeChangeEvent> event = babCollectModeChangeEventService.findAll();
        assertNotNull(event);
        HibernateObjectPrinter.print(event);

        BabCollectModeChangeEvent newEvent = new BabCollectModeChangeEvent();
        newEvent.setMode(BabDataCollectMode.AUTO);
        babCollectModeChangeEventService.insert(newEvent);

    }

    @Autowired
    private LineUserReferenceService lineUserReferenceService;

    @Autowired
    private LineService lineService;

//    @Test
    @Transactional
    @Rollback(true)
    public void testLineUserReferenceService() {
        User user = userService.findByPrimaryKey(11);
        Set<Line> lines = user.getLines();

//        HibernateObjectPrinter.print(l);
    }

//    @Test
    @Transactional
    @Rollback(true)
    public void testFindBabTimeGapPerLine() {
        DateTime sD = new DateTime("2019-12-16");
        DateTime eD = new DateTime("2019-12-20").withTime(23, 0, 0, 0);
        List<BabProcessDetail> l = babService.findBabTimeGapPerLine(sD, eD);
        List<BabProcessDetail> checkD = l.stream().filter(b -> b.getLineId() == 28).collect(toList());
        HibernateObjectPrinter.print(checkD);
    }

//    @Test
    @Transactional
    @Rollback(true)
    public void testSearchGaps() {
        DateTime sD = new DateTime("2019-12-19").withTime(8, 30, 0, 0);
        DateTime eD = new DateTime("2019-12-19").withTime(17, 30, 0, 0);

        List<Bab> l = babService.findByDate(sD, eD);
        l = l.stream().filter(b -> b.getLine().getId() == 28).collect(toList());

        List<Interval> gaps = babService.searchGaps(l, sD, eD);

        HibernateObjectPrinter.print(gaps);
    }

    @Autowired
    private PrepareScheduleService psService;

    @Autowired
    private FloorService floorService;

    @Test
    @Transactional
    @Rollback(true)
    public void testGroupBy() {

        Floor floor = floorService.findByPrimaryKey(1);
        DateTime d = new DateTime().withTime(0, 0, 0, 0);

        List<PrepareSchedule> l = psService.findByFloorAndDate(floor, d);
        List<String> modelNames = l.stream().map(s -> s.getModelName()).collect(toList());
        List<Bab> babs = babService.findByModelNames(modelNames);

         Map<String, Map<Line, Long>> historyFitUserSetting = babs.stream()
//                    .filter(b -> b.getModelName().equals(s.getModelName()))
                    .collect(groupingBy(Bab::getModelName,
                            Collectors.groupingBy(Bab::getLine,
                                    Collectors.mapping(Bab::getId,
                                            Collectors.counting()))));
         
         Map m = historyFitUserSetting.get("bbbb");
         System.out.println(m == null || m.isEmpty() ? "Object not exists" : "Object exists");
        
//        for (PrepareSchedule s : l) {
            
            //Map<ModelName, Map<Line, Count>>
           

//            HibernateObjectPrinter.print(historyFitUserSetting);
//            break;
//        }
    }

}
