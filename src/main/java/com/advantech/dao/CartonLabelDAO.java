/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.advantech.dao;

import com.advantech.jqgrid.PageInfo;
import com.advantech.model.CartonLabel;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Eric.hong
 */
@Repository
public class CartonLabelDAO extends BasicDAOImpl<Integer, CartonLabel> {

    public List<CartonLabel> findAll(PageInfo info) {
        return super.getByPaginateInfo(info);
    }

}
