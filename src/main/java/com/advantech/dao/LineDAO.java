/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.advantech.dao;

import com.advantech.model.Line;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Wei.Cheng
 */
@Repository
public class LineDAO extends AbstractDao<Integer, Line> implements BasicDAO_1<Line> {

    @Override
    public List<Line> findAll() {
        return super.createEntityCriteria().list();
    }

    @Override
    public Line findByPrimaryKey(Object obj_id) {
        return super.getByKey((int) obj_id);
    }

    public List<Line> findBySitefloor(int floor_id) {
        Criteria c = super.createEntityCriteria();
        c.add(Restrictions.eq("floor.id", floor_id));
        return c.list();
    }

    @Override
    public int insert(Line pojo) {
        super.getSession().save(pojo);
        return 1;
    }

    @Override
    public int update(Line pojo) {
        super.getSession().update(pojo);
        return 1;
    }

    @Override
    public int delete(Line pojo) {
        super.getSession().delete(pojo);
        return 1;
    }

}
