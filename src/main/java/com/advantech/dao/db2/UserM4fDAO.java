/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.advantech.dao.db2;

import com.advantech.jqgrid.PageInfo;
import com.advantech.model.Unit;
import com.advantech.model2.UserM4f;
import com.advantech.security.State;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Justin.Yeh
 */
@Repository
public class UserM4fDAO extends BasicDAOImpl<Integer, UserM4f> {

    public List<UserM4f> findAll(PageInfo info) {
        return getByPaginateInfo(info);
    }

    public List<UserM4f> findAll(PageInfo info, Unit usersUnit) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("unit.id", usersUnit.getId()));

        String searchField = info.getSearchField();
        String searchString = info.getSearchString();
        if (searchField != null && !"".equals(searchField) && searchString != null && !"".equals(searchString)) {
            criteria.add(Restrictions.eq(info.getSearchField(), info.getSearchString()));
        }
        criteria.setFirstResult((info.getPage() - 1) * info.getRows());
        criteria.setMaxResults(info.getRows());
        return criteria.list();
    }

    public UserM4f findByJobnumber(String jobnumber) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("jobnumber", jobnumber));
        UserM4f i = (UserM4f) criteria.uniqueResult();
        return i;
    }

    public List<UserM4f> findByUnitName(String... userTypeName) {
        Criteria criteria = createEntityCriteria();
        criteria.createAlias("unit", "u");
        criteria.add(Restrictions.in("u.name", userTypeName));
        criteria.add(Restrictions.eq("state", State.ACTIVE.getState()));
        criteria.addOrder(Order.asc("username"));
        criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        return criteria.list();
    }

    public List<UserM4f> findActive() {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("state", State.ACTIVE.getState()));
        return criteria.list();
    }

}
