/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.advantech.webservice;

//import com.advantech.webservice.WebServiceTX;
import com.advantech.webservice.WebServiceTX;
import static java.lang.System.out;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Wei.Cheng
 */
public class WebServiceTXTest {
    

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }
    String testJobnumber = "A-P03297";
    public WebServiceTXTest() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }
    
    //測試登入登出請距離超過30Min

    /**
     * Test of getMESUser method, of class WebServiceRV.
     */
//    @Test
//    public void testLogin() {
//        out.println("testLogin");
//        String result = WebServiceTX.getInstance().kanbanUserLogin(testJobnumber);
//        out.println(result);
//    }

    /**
     * Test of getMESUser method, of class WebServiceRV.
     */
    @Test
    public void testLogout() {
        out.println("testLogout");
        try {
            String result = WebServiceTX.getInstance().kanbanUserLogout(testJobnumber);
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

}
