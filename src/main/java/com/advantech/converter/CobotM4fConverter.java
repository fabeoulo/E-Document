/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.advantech.converter;

import com.advantech.model2.CobotM4f;
import com.advantech.service.db2.CobotM4fService;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 *
 * @author Justin.Yeh
 */
@Component
public class CobotM4fConverter implements Converter<String, CobotM4f> {

    @Autowired
    private CobotM4fService cobotService;

    @Override
    public CobotM4f convert(String s) {
        return cobotService.findByPrimaryKey(Integer.parseInt(s));
    }

    public String convertToString(Set<CobotM4f> cobots){
        String[] names = cobots.stream().map(c -> c.getName()).toArray(String[]::new);
        return String.join(",", names);
    }
}
