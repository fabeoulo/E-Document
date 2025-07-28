/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.advantech.webservice.download;

import com.advantech.model.User;
import com.advantech.model.Worktime;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.beanutils.PropertyUtils;

/**
 *
 * @author Justin.Yeh
 */
public abstract class BasicM4fDownload<WT extends Worktime> {

    public abstract WT download(WT wt) throws Exception;

    protected <T extends Object> Map<String, T> toSelectOptions(List<T> l) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        Map m = new HashMap();
        if (!l.isEmpty()) {
            Object firstObj = l.get(0);
            boolean isUserObject = firstObj instanceof User;
            for (Object obj : l) {
                String name = (String) PropertyUtils.getProperty(obj, isUserObject ? "jobnumber" : "name");
                m.put(isUserObject ? name.toUpperCase() : name, obj);
            }
        }
        return m;
    }

    protected boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }
}
