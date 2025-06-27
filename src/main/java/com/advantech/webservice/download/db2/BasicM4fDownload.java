/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.advantech.webservice.download.db2;

import com.advantech.model.db2.IUserM9;
import com.advantech.model.db2.IWorktimeForWebService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.lang.reflect.InvocationTargetException;
import org.apache.commons.beanutils.PropertyUtils;

/**
 *
 * @author Justin.Yeh
 * @param <WT> T implements IWorktimeForWebService
 */
public abstract class BasicM4fDownload<WT extends IWorktimeForWebService> {

    public abstract WT download(WT wt) throws Exception;

    protected <T extends Object> Map<String, T> toSelectOptions(List<T> l) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        Map m = new HashMap();
        if (!l.isEmpty()) {
            Object firstObj = l.get(0);
            boolean isUserObject = firstObj instanceof IUserM9;
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
