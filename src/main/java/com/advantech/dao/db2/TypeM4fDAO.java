/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.advantech.dao.db2;

import com.advantech.jqgrid.PageInfo;
import com.advantech.model.db2.TypeM4f;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Wei.Cheng
 */
@Repository
public class TypeM4fDAO extends BasicDAOImpl<Integer, TypeM4f> {

    public List<TypeM4f> findAll(PageInfo info) {
        return super.getByPaginateInfo(info);
    }

}
