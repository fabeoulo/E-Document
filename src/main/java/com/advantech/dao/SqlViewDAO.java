/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.advantech.dao;

import java.util.List;
import java.util.Map;
import javax.persistence.Tuple;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Justin.Yeh
 */
@Repository(value = "sqlViewDAO")
public class SqlViewDAO extends BasicDAOImpl<Integer, Object> {

    public List<Map> findSuggestionWorkTime() {
        String sql = "SELECT modelName , station, totalCnt, "
                + "CONVERT (numeric(10,1),  [standardTime])  standardTime, "
                + "CONVERT (numeric(10,2),  [opTime]) opTime, "
                + "CONVERT (numeric(10,1),  [suggestSt]) suggestSt, "
                + "decide "
                + "FROM Vw_SuggestionWorkTime_Down ";

        List<Tuple> tuples = super.getSession().createNativeQuery(sql, Tuple.class).getResultList();
        return super.setResultTransformer(tuples);
    }
}
