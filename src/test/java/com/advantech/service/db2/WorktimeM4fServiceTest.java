/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package com.advantech.service.db2;

import com.advantech.jqgrid.PageInfo;
import com.advantech.model.db2.IWorktimeForWebService;
import com.advantech.model.db2.WorktimeM4f;
import static com.google.common.collect.Lists.newArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
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
public class WorktimeM4fServiceTest {

    @Autowired
    WorktimeM4fService instance;

    public WorktimeM4fServiceTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of findAll method, of class WorktimeM4fService.
     */
//    @Test//OK
    public void testFindAll_0args() {
        System.out.println("findAll");
        List<WorktimeM4f> result = instance.findAll();
        assertTrue(!result.isEmpty());
    }

    /**
     * Test of findAll method, of class WorktimeM4fService.
     */
//    @Test//OK
    public void testFindAll_PageInfo() {
        System.out.println("findAll");
        PageInfo info = new PageInfo();
        List<WorktimeM4f> expResult = null;
        List<WorktimeM4f> result = instance.findAll(info);
        assertNotEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

//    /**
//     * Test of findByPrimaryKey method, of class WorktimeM4fService.
//     */
//    @Test//OK
    public void testFindByPrimaryKey() {
        System.out.println("findByPrimaryKey");
        Object obj_id = null;
        WorktimeM4f expResult = null;
        WorktimeM4f result = instance.findByPrimaryKey(16894);
        assertNotEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }
//
//    /**
//     * Test of findByPrimaryKeys method, of class WorktimeM4fService.
//     */
//    @Test
//    public void testFindByPrimaryKeys() {
//        System.out.println("findByPrimaryKeys");
//        Integer[] ids = null;
//        List<WorktimeM4f> expResult = null;
//        List<WorktimeM4f> result = instance.findByPrimaryKeys(ids);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of findByModel method, of class WorktimeM4fService.
//     */
//    @Test
//    public void testFindByModel() {
//        System.out.println("findByModel");
//        String modelName = "";

//        WorktimeM4f expResult = null;
//        WorktimeM4f result = instance.findByModel(modelName);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of findCobots method, of class WorktimeM4fService.
//     */
//    @Test
//    public void testFindCobots() {
//        System.out.println("findCobots");
//        int obj_id = 0;
//        Set<Cobot> expResult = null;
//        Set<Cobot> result = instance.findCobots(obj_id);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of findCobotsAsList method, of class WorktimeM4fService.
//     */
//    @Test
//    public void testFindCobotsAsList() {
//        System.out.println("findCobotsAsList");
//        int obj_id = 0;
//        List<Cobot> expResult = null;
//        List<Cobot> result = instance.findCobotsAsList(obj_id);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of findWithFullRelation method, of class WorktimeM4fService.
//     */
//    @Test
//    public void testFindWithFullRelation() {
//        System.out.println("findWithFullRelation");
//        PageInfo info = null;
//        List<WorktimeM4f> expResult = null;
//        List<WorktimeM4f> result = instance.findWithFullRelation(info);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of insert method, of class WorktimeM4fService.
//     */
//    @Test
//    public void testInsert_List() throws Exception {
//        System.out.println("insert");
//        List<WorktimeM4f> l = null;
//        int expResult = 0;
//        int result = instance.insert(l);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of insert method, of class WorktimeM4fService.
//     */
//    @Test
//    public void testInsert_Worktime() throws Exception {
//        System.out.println("insert");
//        WorktimeM4f worktime = null;
//        int expResult = 0;
//        int result = instance.insert(worktime);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of insertWithFormulaSetting method, of class WorktimeM4fService.
//     */
//    @Test
//    public void testInsertWithFormulaSetting_List() throws Exception {
//        System.out.println("insertWithFormulaSetting");
//        List<WorktimeM4f> l = null;
//        int expResult = 0;
//        int result = instance.insertWithFormulaSetting(l);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of insertWithFormulaSetting method, of class WorktimeM4fService.
//     */
//    @Test
//    public void testInsertWithFormulaSetting_Worktime() throws Exception {
//        System.out.println("insertWithFormulaSetting");
//        WorktimeM4f worktime = null;
//        int expResult = 0;
//        int result = instance.insertWithFormulaSetting(worktime);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
    /**
     * Test of insertSeries method, of class WorktimeM4fService.
     */
//    @Test
    @Transactional
    @Rollback(true)
    public void testInsertSeries() throws Exception {
        System.out.println("insertSeries");
        String baseModelName = "UNO-1372G-E3AE";
        List<String> seriesModelNames = newArrayList("TEST_1", "TEST2");
        int expResult = 1;
        int result = instance.insertSeriesWithMesUpload(baseModelName, seriesModelNames);
        assertEquals(expResult, result);

        WorktimeM4f seriesModelPojo = instance.findByModel(seriesModelNames.get(0));
        assertNotNull(seriesModelPojo);
        assertEquals(3, seriesModelPojo.getCobots().size());

    }
//
//    /**
//     * Test of update method, of class WorktimeM4fService.
//     */
//    @Test
//    public void testUpdate_List() throws Exception {
//        System.out.println("update");
//        List<WorktimeM4f> l = null;

//        int expResult = 0;
//        int result = instance.update(l);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of update method, of class WorktimeM4fService.
//     */
//    @Test
//    public void testUpdate_Worktime() throws Exception {
//        System.out.println("update");
//        WorktimeM4f worktime = null;
//        int expResult = 0;
//        int result = instance.update(worktime);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of merge method, of class WorktimeM4fService.
//     */
//    @Test
//    public void testMerge_List() throws Exception {
//        System.out.println("merge");
//        List<WorktimeM4f> l = null;
//        int expResult = 0;
//        int result = instance.merge(l);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of merge method, of class WorktimeM4fService.
//     */
//    @Test
//    public void testMerge_Worktime() throws Exception {
//        System.out.println("merge");
//        WorktimeM4f worktime = null;
//        int expResult = 0;
//        int result = instance.merge(worktime);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
    
//    @Test
//    @Transactional
//    @Rollback(false)
    public void testInsertByExcelModel() throws Exception {
        System.out.println("testInsertByExcelModel");

        WorktimeM4f wm4 = new WorktimeM4f();
        wm4.setModelName("MIC-75G20-10B1");
        List<WorktimeM4f> l = newArrayList(wm4);
        assertNotNull(l);
        instance.saveOrUpdate(l);
    }
    
//    /**
//     * Test of insertByExcel method, of class WorktimeM4fService.
//     */
//    @Test
//    public void testInsertByExcel() throws Exception {
//        System.out.println("insertByExcel");
//        List<WorktimeM4f> l = null;
//        int expResult = 0;
//        int result = instance.insertByExcel(l);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of mergeByExcel method, of class WorktimeM4fService.
//     */
//    @Test
//    public void testMergeByExcel() throws Exception {
//        System.out.println("mergeByExcel");
//        List<WorktimeM4f> l = null;
//        int expResult = 0;
//        int result = instance.mergeByExcel(l);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of initUnfilledFormulaColumn method, of class WorktimeM4fService.
//     */
//    @Test
//    @Transactional
//    @Rollback(false)
    public void testInitUnfilledFormulaColumn() throws Exception {
        System.out.println("initUnfilledFormulaColumn");

//        List<WorktimeM4f> l = instance.findAll();
        List<WorktimeM4f> l = instance.findWithFlowRelationAndCobot(10606);
        assertNotNull(l);
        for (WorktimeM4f worktime : l) {
            System.out.println("Upload model: " + worktime.getModelName());
            instance.initUnfilledFormulaColumn(worktime);
        }
        instance.saveOrUpdate(l);
    }
//
//    /**
//     * Test of saveOrUpdate method, of class WorktimeM4fService.
//     */
//    @Test
//    public void testSaveOrUpdate() throws Exception {
//        System.out.println("saveOrUpdate");
//        List<WorktimeM4f> l = null;

//        int expResult = 0;
//        int result = instance.saveOrUpdate(l);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of delete method, of class WorktimeM4fService.
//     */
//    @Test
//    public void testDelete_List() throws Exception {
//        System.out.println("delete");
//        List<WorktimeM4f> l = null;
//        int expResult = 0;
//        int result = instance.delete(l);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of delete method, of class WorktimeM4fService.
//     */
//    @Test
//    public void testDelete_Worktime() throws Exception {
//        System.out.println("delete");
//        WorktimeM4f w = null;
//        int expResult = 0;
//        int result = instance.delete(w);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of delete method, of class WorktimeM4fService.
//     */
//    @Test
//    public void testDelete_IntegerArr() throws Exception {
//        System.out.println("delete");
//        Integer[] ids = null;
//        int expResult = 0;
//        int result = instance.delete(ids);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of delete method, of class WorktimeM4fService.
//     */
//    @Test
//    public void testDelete_int() throws Exception {
//        System.out.println("delete");
//        int id = 0;
//        int expResult = 0;
//        int result = instance.delete(id);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of reUpdateAllFormulaColumn method, of class WorktimeM4fService.
//     */
//    @Test
//    public void testReUpdateAllFormulaColumn() throws Exception {
//        System.out.println("reUpdateAllFormulaColumn");
//        instance.reUpdateAllFormulaColumn();
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
    @Test
    public void testBatchUpdate() throws Exception {
        System.out.println("testBatchUpdate");
        List<WorktimeM4f> l = instance.findByPrimaryKeys(3815, 3816);
        assertEquals(2, l.size());
        l.forEach(w -> w.setReasonCode("A3"));
        instance.merge(l);
    }

}
