/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.advantech.dao.db2;

import com.advantech.jqgrid.PageInfo;
import com.advantech.model.db2.OutLabelM4f;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Justin.Yeh
 */
@Repository
public class OutLabelM4fDAO extends BasicDAOImpl<Integer, OutLabelM4f> {

    public List<OutLabelM4f> findAll(PageInfo info) {
        return super.getByPaginateInfo(info);
    }

}
