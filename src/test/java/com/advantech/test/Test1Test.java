/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.advantech.test;

import com.advantech.model.Worktime;
import com.advantech.security.State;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.joda.time.DateTime;
import org.junit.Test;

/**
 *
 * @author Wei.Cheng
 */
public class Test1Test {

//    @Test
    public void test() {
        String[] st = {"   test1   ", "   test2", "test3   ", "   te st4   ", "test5", "te st6"};

        System.out.println(Arrays.toString(st));

        List<String> l = Arrays.stream(st).map(s -> {
            return removeModelNameExtraSpaceCharacter(s);
        }).collect(Collectors.toList());

        System.out.println(l);

        l.forEach(t -> {
            System.out.println(t.length());
        });
    }

    private void removeModelNameExtraSpaceCharacter(Worktime w) {
        String modelName = w.getModelName();
        w.setModelName(removeModelNameExtraSpaceCharacter(modelName));
    }

    private String removeModelNameExtraSpaceCharacter(String modelName) {
        return modelName.replaceAll("^\\s+", "").replaceAll("\\s+$", "");
    }

//    @Test
    public void testState() {
        String name = State.ACTIVE.getName();
        String s = State.ACTIVE.toString();
    }
    
//    @Test
    public void testDateTime() {
        DateTime d = new DateTime("2017/01/01 00:00:00.00 ");
    }

//    @Test
    public void testReadFile() throws Exception{
        String syncFilePath = "C:\\Users\\Wei.Cheng\\Desktop\\M6 work_time(整理後).xls";
        try (InputStream is = new FileInputStream(new File(syncFilePath))) {
            
        }   
    }
    
    @Test
    public void testString() throws Exception{
        String t1 = "‪Yui‪";
        String t2 = "Yui";
        
        System.out.println(t1.equals(t2));
    }

}
