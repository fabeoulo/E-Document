/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.advantech.dao.db2;

import com.advantech.jqgrid.PageInfo;
import com.advantech.model.db2.CartonLabelM4f;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Justin.Yeh
 */
@Repository
public class CartonLabelM4fDAO extends BasicDAOImpl<Integer, CartonLabelM4f> {

    public List<CartonLabelM4f> findAll(PageInfo info) {
        return super.getByPaginateInfo(info);
    }

}
