/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.advantech.webservice;

//import com.advantech.webservice.WebServiceTX;
import static java.lang.System.out;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Wei.Cheng
 */
public class WebServiceTXTest {
    
    @Autowired
    private WebServiceTX tx;

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
        tx.kanbanUserLogout(testJobnumber);
    }

}
