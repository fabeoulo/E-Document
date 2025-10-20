/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.advantech.service.db2;

import com.advantech.dao.db2.*;
import com.advantech.helper.CustomPasswordEncoder;
import com.advantech.jqgrid.PageInfo;
import com.advantech.model.Unit;
import com.advantech.model.UserProfile;
import com.advantech.model2.UserM4f;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Justin.Yeh
 */
@Service
@Transactional("tx2")
public class UserM4fService extends BasicServiceImpl<Integer, UserM4f> {

    @Autowired
    private UserM4fDAO dao;

    @Override
    protected BasicDAOImpl getDao() {
        return this.dao;
    }

    public List<UserM4f> findAll(PageInfo info) {
        return dao.findAll(info);
    }

    public List<UserM4f> findAll(PageInfo info, Unit usersUnit) {
        return dao.findAll(info, usersUnit);
    }

    public UserM4f findByJobnumber(String jobnumber) {
        UserM4f i = dao.findByJobnumber(jobnumber);

        if (i == null) {
            return null;
        }

        //Initialize the lazy loading relative object
        Hibernate.initialize(i.getUnit());
        Hibernate.initialize(i.getFloor());
        Hibernate.initialize(i.getUserProfiles());

        return i;
    }

    public List<UserProfile> findUserProfiles(int user_id) {
        List l = new ArrayList();
        UserM4f u = this.findByPrimaryKey(user_id);
        l.addAll(u.getUserProfiles());
        return l;
    }

    public List<UserProfile> findUserNotifications(int user_id) {
        List l = new ArrayList();
        UserM4f u = this.findByPrimaryKey(user_id);
        l.addAll(u.getUserNotifications());
        return l;
    }

    public List<UserM4f> findByUnitName(String... unitName) {
        return dao.findByUnitName(unitName);
    }

    public List<UserM4f> findActive() {
        return dao.findActive();
    }

    public int delete(int id) {
        UserM4f user = this.findByPrimaryKey(id);
        return dao.delete(user);
    }

    public int resetPsw() {
        CustomPasswordEncoder encoder = new CustomPasswordEncoder();
        List<UserM4f> l = this.findAll();
        for (UserM4f user : l) {
            String encryptPassord = encoder.encode(user.getJobnumber());
            user.setPassword(encryptPassord);
            this.update(user);
        }
        return 1;
    }

}
