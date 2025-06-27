/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.advantech.test;

import com.advantech.model.M9ieWorktimeView;
import com.advantech.service.M9ieWorktimeViewService;
import java.util.List;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 *
 * @author Justin.Yeh
 */
@WebAppConfiguration
@ContextConfiguration(locations = {
    "classpath:servlet-context.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class ServiceTest {

    @Autowired
    private M9ieWorktimeViewService m9ieWorktimeViewService;

    @Test
//    @Transactional
//    @Rollback(false)
    public void testM9ieWorktimeViewService() {
        System.out.println("testM9ieWorktimeViewService");
//        PageInfo info = new PageInfo();
//        List<M9ieWorktimeView> result0 = m9ieWorktimeViewService.findAll(info);
        List<M9ieWorktimeView> result = m9ieWorktimeViewService.findAll();
        assertTrue(!result.isEmpty());
        result = m9ieWorktimeViewService.findByModelNames("POC-ALG-W216-5D", "POC-ALG-W216-5D");
        assertTrue(!result.isEmpty());
    }
}
