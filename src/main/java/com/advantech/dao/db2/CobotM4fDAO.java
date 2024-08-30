/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.advantech.dao.db2;

import com.advantech.jqgrid.PageInfo;
import com.advantech.model.db2.CobotM4f;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Justin.Yeh
 */
@Repository
public class CobotM4fDAO extends BasicDAOImpl<Integer, CobotM4f> {

    public List<CobotM4f> findAll(PageInfo info) {
        return super.getByPaginateInfo(info);
    }
}
