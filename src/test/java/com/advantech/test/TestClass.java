/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.advantech.test;

import com.advantech.converter.Encodeable;
import com.advantech.webservice.Factory;
import static com.google.common.collect.Lists.newArrayList;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;

/**
 *
 * @author Wei.Cheng
 */
public class TestClass {

    private static final Logger log = LoggerFactory.getLogger(TestClass.class);

    List<StopWatch> temp_L = new ArrayList();

//    @Test
    public void test() {
        log.info("bab_id {} / Max: {} / Sum: {} / BANANCE: {} / STANDARD: {}", 1, 0.1,
                0.2, 0.3, 0.4);
    }

//    @Test
    public void testKeywordFilter() throws InterruptedException {
        List<String> keywords = newArrayList("TPC", "T1PC1", "ABCC", "T1PC1331", "DBB");
        String modelName = "TPC1331-2213-ZZ";

        String key = keywords.stream()
                .filter(modelName::contains)
                .max(Comparator.comparing(String::length)).orElse(null);
        System.out.println(key);
    }

    @Test
    public void testEnum() {
        Factory f = Factory.DEFAULT;
        System.out.println(f.token());
        System.out.println(f.toString());
        System.out.println(f.token() instanceof String);
        assertEquals(Factory.TEMP2, Encodeable.forToken(Factory.class, "M2"));
        assertEquals(Factory.TEMP2.token(), "M2");
        Factory m6 = Factory.getEnum("M6");
        assertEquals(Factory.TEMP1, m6);
    }

}
