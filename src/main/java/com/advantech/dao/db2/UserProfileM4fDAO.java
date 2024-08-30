/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.advantech.dao.db2;

import com.advantech.model.db2.UserProfileM4f;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Justin.Yeh
 */
@Repository
public class UserProfileM4fDAO extends BasicDAOImpl<Integer, UserProfileM4f> {

    public UserProfileM4f findByType(String typeName) {
        Criteria c = createEntityCriteria();
        c.add(Restrictions.eq("name", typeName));
        return (UserProfileM4f) c.uniqueResult();
    }

}
