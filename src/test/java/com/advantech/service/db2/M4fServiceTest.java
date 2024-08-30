/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.advantech.service.db2;

import com.advantech.jqgrid.PageInfo;
import com.advantech.model.Unit;
import com.advantech.model.UserProfile;
import com.advantech.model.db2.*;
import com.advantech.service.UnitService;
import com.advantech.service.UserService;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Justin.Yeh
 */
@WebAppConfiguration
@ContextConfiguration(locations = {
    "classpath:servlet-context.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class M4fServiceTest {

    @Autowired
    private PreAssyM4fService preAssyService;

    @Autowired
    private FlowGroupM4fService flowGroupM4fService;

    @Autowired
    private FlowM4fService flowM4fService;

    @Autowired
    private BusinessGroupM4fService businessGroupM4fService;

    @Autowired
    private WorktimeFormulaSettingM4fService worktimeFormulaSettingM4fService;

    @Autowired
    private WorktimeMaterialPropertyUploadSettingM4fService propSettingService;

    @Autowired
    private WorktimeMaterialPropertyDownloadSettingM4fService propSettingDlService;
    
    @Autowired
    private PendingM4fService pendingM4fService;

    @Autowired
    private CartonLabelM4fService cartonLabelM4fService;

    @Autowired
    private OutLabelM4fService outLabelM4fService;

    @Autowired
    private UserM4fService userM4fService;

    @Autowired
    private UserService userService;

    @Autowired
    private UnitService unitService;

    @Autowired
    private UserProfileM4fService userProfileM4fService;

    @Autowired
    private UnitM4fService unitM4fService;
    
    @Autowired
    private FloorM4fService floorM4fService;
    
    @Autowired
    private WorktimeColumnGroupM4fService worktimeColumnGroupM4fService;
    
    @Autowired
    private TypeM4fService typeM4fService;
    
    @Autowired
    private CobotM4fService cobotM4fService;
    
//    @Test
    public void testCobotM4fService() {
        System.out.println("testCobotM4fService");
        PageInfo info = new PageInfo();
        List<CobotM4f> result0 = cobotM4fService.findAll(info);
        List<CobotM4f> result = cobotM4fService.findAll();
        assertTrue(!result.isEmpty());
        CobotM4f pojo = cobotM4fService.findByPrimaryKey(1);
    }
    
//    @Test
    public void testTypeM4fService() {
        System.out.println("testTypeM4fService");
        PageInfo info = new PageInfo();
        List<TypeM4f> result0 = typeM4fService.findAll(info);
        List<TypeM4f> result = typeM4fService.findAll();
        assertTrue(!result.isEmpty());
        TypeM4f pojo = typeM4fService.findByPrimaryKey(1);
    }
    
//    @Test
    public void testWorktimeColumnGroupM4fService() {
        System.out.println("testWorktimeColumnGroupM4fService");
        PageInfo info = new PageInfo();
        List<WorktimeColumnGroupM4f> result0 = worktimeColumnGroupM4fService.findAll(info);
        List<WorktimeColumnGroupM4f> result = worktimeColumnGroupM4fService.findAll();
        assertTrue(!result.isEmpty());
        WorktimeColumnGroupM4f pojo = worktimeColumnGroupM4fService.findByPrimaryKey(1);
        WorktimeColumnGroupM4f pojo2 = worktimeColumnGroupM4fService.findByUnit(1);
    }
    
//    @Test
    public void testUserProfileM4fService() {
        System.out.println("testUserProfileM4fService");
        PageInfo info = new PageInfo();
        List<UserProfileM4f> result = userProfileM4fService.findAll();
        assertTrue(!result.isEmpty());
        UserProfileM4f pojo = userProfileM4fService.findByPrimaryKey(1);
        UserProfileM4f pojo2 = userProfileM4fService.findByType("USER");

        Set<UserProfileM4f> set = new HashSet<>(Arrays.asList(pojo, pojo2));

    }

//    @Test
    public void testUserM4fService() {
        System.out.println("testUserM4fService");
        PageInfo info = new PageInfo();
        Unit unit = unitService.findByPrimaryKey(2);

        List<UserM4f> result0 = userM4fService.findAll(info);
        List<UserM4f> result = userM4fService.findAll();
        List<UserM4f> result1 = userM4fService.findAll(info, unit);
        assertTrue(!result.isEmpty());
        UserM4f pojo = userM4fService.findByPrimaryKey(1);
        UserM4f pojo1 = userM4fService.findByJobnumber("000000546");
        IUserM9 pojo2 = userService.findByJobnumber("000000546");
        List result2 = userM4fService.findByUnitName("SPE", "SPE_AND_EE");
        List result3 = userService.findByUnitName("SPE", "SPE_AND_EE");
        List<UserProfile> result4 = userM4fService.findUserProfiles(pojo.getId());
    }

//    @Test
    public void testCartonLabelM4fService() {
        System.out.println("testCartonLabelM4fService");
        PageInfo info = new PageInfo();
        List<CartonLabelM4f> result0 = cartonLabelM4fService.findAll(info);
        List<CartonLabelM4f> result = cartonLabelM4fService.findAll();
        assertTrue(!result.isEmpty());
        CartonLabelM4f pojo = cartonLabelM4fService.findByPrimaryKey(1);
    }
    
//    @Test
    public void testFloorM4fService() {
        System.out.println("testFloorM4fService");
        PageInfo info = new PageInfo();
        List<FloorM4f> result = floorM4fService.findAll();
        assertTrue(!result.isEmpty());
        FloorM4f pojo = floorM4fService.findByPrimaryKey(1);
    }
    
//    @Test
    public void testUnitM4fService() {
        System.out.println("testUnitM4fService");
        PageInfo info = new PageInfo();
        List<UnitM4f> result = unitM4fService.findAll();
        assertTrue(!result.isEmpty());
        UnitM4f pojo = unitM4fService.findByPrimaryKey(1);
    }

//    @Test
    public void testOutLabelM4fService() {
        System.out.println("testOutLabelM4fService");
        PageInfo info = new PageInfo();
        List<OutLabelM4f> result0 = outLabelM4fService.findAll(info);
        List<OutLabelM4f> result = outLabelM4fService.findAll();
        assertTrue(!result.isEmpty());
        OutLabelM4f pojo = outLabelM4fService.findByPrimaryKey(1);
    }

//    @Test
    public void testPendingM4fService() {
        System.out.println("testPendingM4fService");
        PageInfo info = new PageInfo();
        List<PendingM4f> result0 = pendingM4fService.findAll(info);
        List<PendingM4f> result = pendingM4fService.findAll();
        assertTrue(!result.isEmpty());
        PendingM4f pojo = pendingM4fService.findByPrimaryKey(1);
    }

//    @Test
    public void testWorktimeMaterialPropertyDownloadSettingM4fService() {
        System.out.println("testWorktimeMaterialPropertyDownloadSettingM4fService");
        List<WorktimeMaterialPropertyDownloadSettingM4f> result = propSettingDlService.findAll();
        assertTrue(!result.isEmpty());
        WorktimeMaterialPropertyDownloadSettingM4f pojo = propSettingDlService.findByPrimaryKey(1);
    }
    
//    @Test
    public void testWorktimeMaterialPropertyUploadSettingM4fService() {
        System.out.println("testWorktimeMaterialPropertyUploadSettingM4fService");
        List<WorktimeMaterialPropertyUploadSettingM4f> result = propSettingService.findAll();
        assertTrue(!result.isEmpty());
        WorktimeMaterialPropertyUploadSettingM4f pojo = propSettingService.findByPrimaryKey(1);
    }

//    @Test
    public void testWorktimeFormulaSettingM4fService() {
        System.out.println("testWorktimeFormulaSettingM4fService");
        List<WorktimeFormulaSettingM4f> result = worktimeFormulaSettingM4fService.findAll();
        assertTrue(!result.isEmpty());
        WorktimeFormulaSettingM4f pojo = worktimeFormulaSettingM4fService.findByPrimaryKey(16788);
        List<WorktimeFormulaSettingM4f> pojo1 = worktimeFormulaSettingM4fService.findByWorktime(16894);
    }

//    @Test
    public void testBusinessGroupM4fService() {
        System.out.println("testBusinessGroupM4fService");
        List<BusinessGroupM4f> result = businessGroupM4fService.findAll();
        assertTrue(!result.isEmpty());
        BusinessGroupM4f pojo = businessGroupM4fService.findByPrimaryKey(1);
    }

//    @Test
//    @Transactional
//    @Rollback(false)
    public void testFlowM4fService() {
        System.out.println("testFlowM4fService");
//        PageInfo info = new PageInfo();
//        List<FlowM4f> result0 = flowM4fService.findAll(info);
        List<FlowM4f> result = flowM4fService.findAll();
//        assertTrue(!result.isEmpty());
//        FlowM4f pojo = flowM4fService.findByPrimaryKey(1);
//        FlowM4f pojo1 = flowM4fService.findByFlowName("BAB_ASSY");
//        List<FlowM4f> pojo2 = flowM4fService.findByFlowGroup(1);
//        List<FlowM4f> pojo3 = flowM4fService.findByParent(1);

        Map<String, FlowM4f> _flowNamesM4f = result.stream().collect(Collectors.toMap(f -> f.getName(), f -> f));
        String flowName = "BAB_ASSY-BI";
        if (isNullOrEmpty(flowName)) {
            return;
        }
        FlowM4f babFlow = _flowNamesM4f.get(flowName);
        if (!_flowNamesM4f.containsKey(flowName)) {
            babFlow = new FlowM4f();
            babFlow.setName(flowName);
            flowM4fService.insert(babFlow);
            babFlow = flowM4fService.findByFlowName(flowName);
            _flowNamesM4f.put(flowName, babFlow);
        }
    }

//    @Test
//    @Transactional
//    @Rollback(false)
    public void testPreAssyService() {
        System.out.println("testPreAssyService");
//        PageInfo info = new PageInfo();
//        List<PreAssyM4f> result0 = preAssyService.findAll(info);
        List<PreAssyM4f> result = preAssyService.findAll();
//        assertTrue(!result.isEmpty());
//        PreAssyM4f pojo = preAssyService.findByPrimaryKey(1);
//        PreAssyM4f pojo1 = preAssyService.findByFlowName("SYSTEM");

        Map<String, PreAssyM4f> _preAssyNamesM4f = result.stream().collect(Collectors.toMap(f -> f.getName(), f -> f));
        String flowName = "PRE-ASSY-PT";
        if (isNullOrEmpty(flowName)) {
            return;
        }
        PreAssyM4f preAssy = _preAssyNamesM4f.get(flowName);
        if (!_preAssyNamesM4f.containsKey(flowName)) {
            preAssy = new PreAssyM4f();
            preAssy.setName(flowName);
            preAssyService.insert(preAssy);
            preAssy = preAssyService.findByFlowName(flowName);
            _preAssyNamesM4f.put(flowName, preAssy);
        }

    }

    private boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

//    @Test
    public void testFlowGroupM4fService() {
        System.out.println("testFlowGroupM4fService");
        PageInfo info = new PageInfo();
        List<FlowGroupM4f> result0 = flowGroupM4fService.findAll(info);
        List<FlowGroupM4f> result = flowGroupM4fService.findAll();
        assertTrue(!result.isEmpty());
        FlowGroupM4f pojo = flowGroupM4fService.findByPrimaryKey(1);
    }
}
