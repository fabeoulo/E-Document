/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.advantech.webservice.port;

import com.advantech.model.Worktime;
import com.advantech.model.db2.IUserM9;
import com.advantech.service.UserService;
import com.advantech.webservice.root.MesUserInfoQueryRoot;
import com.advantech.webservice.unmarshallclass.MesUserInfo;
import com.advantech.webservice.unmarshallclass.MesUserInfos;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.bind.JAXBException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.advantech.model.db2.IWorktimeForWebService;
import com.advantech.webservice.Factory;
import com.advantech.webservice.root.DeptIdM9;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 *
 * @author Wei.Cheng
 */
@Component
public class MesUserInfoQueryPort extends BasicQueryPort {

    private static final Logger logger = LoggerFactory.getLogger(MesUserInfoQueryPort.class);

    @Autowired
    private UserService userService;

    private final Integer sysSpe3f = DeptIdM9.SPE3F.getCode();
    private final Integer sysBpe3f = DeptIdM9.EE3F.getCode();

    @Override
    protected void initJaxb() {
        try {
            super.initJaxb(MesUserInfoQueryRoot.class, MesUserInfos.class);
        } catch (JAXBException e) {
            logger.error(e.toString());
        }
    }

    // OK
    @Override
    public List queryM(IWorktimeForWebService w, Factory f) throws Exception {
        return (List<MesUserInfo>) super.queryM(w, f);
    }

    @Override // OK
    public Map<String, String> transformData(IWorktimeForWebService w) throws Exception {
        Map<String, String> xmlResults = new HashMap();
        Map<String, String> m = this.getJobnumber(w);
        for (Map.Entry<String, String> entry : m.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            MesUserInfoQueryRoot root = new MesUserInfoQueryRoot();
            MesUserInfoQueryRoot.USERINFO users = root.getUSERS();
            users.setUSERNO(value);
            xmlResults.put(key, super.generateXmlString(root));
        }
        return xmlResults;
    }

    public Map<String, String> getJobnumber(IWorktimeForWebService w) {
        Map<String, String> m = new HashMap();
        IUserM9 speOwner = w.getUserBySpeOwnerId();
        IUserM9 eeOwner = w.getUserByEeOwnerId();
        IUserM9 qcOwner = w.getUserByQcOwnerId();
        IUserM9 mpmOwner = w.getUserByMpmOwnerId();

        if (eeOwner != null) {
            m.put("eeOwner", userService.findByPrimaryKey(eeOwner.getId()).getJobnumber());
        }

        if (mpmOwner != null) {
            m.put("mpmOwner", userService.findByPrimaryKey(mpmOwner.getId()).getJobnumber());
        }

        m.put("speOwner", userService.findByPrimaryKey(speOwner.getId()).getJobnumber());
        m.put("qcOwner", userService.findByPrimaryKey(qcOwner.getId()).getJobnumber());

        return m;
    }

    public void setSpeEeDept(List<MesUserInfo> l, Worktime w) {
        Map<String, String> m = this.getJobnumber(w);

        if (m.containsKey("eeOwner")) {
            String jobNo = m.get("eeOwner");
            List<MesUserInfo> o = l.stream().filter(mu -> jobNo.equals(mu.getJobnumber())).collect(Collectors.toList());
            o.forEach(mu -> mu.setDeptId(sysBpe3f));
        }

        if (m.containsKey("speOwner")) {
            String jobNo = m.get("speOwner");
            Optional<MesUserInfo> o = l.stream().filter(mu -> jobNo.equals(mu.getJobnumber())).findFirst();
            o.ifPresent(mu -> mu.setDeptId(sysSpe3f));
        }
    }
}
