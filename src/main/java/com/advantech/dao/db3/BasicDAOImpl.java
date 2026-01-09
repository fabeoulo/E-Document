/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.advantech.dao.db3;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 *
 * @author Justin.Yeh
 * @param <PK> Unique private key
 * @param <T> Entity type
 */
public abstract class BasicDAOImpl<PK extends Serializable, T> extends com.advantech.dao.BasicDAOImpl<PK, T> {

    @Autowired
    @Qualifier("sessionFactory3")
    private SessionFactory sessionFactory;

    @PostConstruct
    protected void setSessionFactory() {
        super.setSessionFactory(sessionFactory);
    }

}
