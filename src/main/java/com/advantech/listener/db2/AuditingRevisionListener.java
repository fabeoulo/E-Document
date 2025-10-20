/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 * 
 */
package com.advantech.listener.db2;

import com.advantech.model2.AuditedRevisionEntity;
import org.hibernate.envers.RevisionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author Justin.Yeh
 */
public class AuditingRevisionListener implements RevisionListener {

    private static final Logger log = LoggerFactory.getLogger(AuditingRevisionListener.class);

    @Override
    public void newRevision(Object revisionEntity) {
        AuditedRevisionEntity revEntity = (AuditedRevisionEntity) revisionEntity;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        //Prevent NullPointerException on testing CRUD data 
        if (auth == null) {
            revEntity.setUsername("sysop");
        } else {
            String userName = auth.getName();
            revEntity.setUsername(userName);
        }
    }
}
