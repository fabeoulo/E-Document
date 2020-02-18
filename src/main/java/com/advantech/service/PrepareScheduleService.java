/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.advantech.service;

import com.advantech.dao.BabSettingHistoryDAO;
import com.advantech.dao.LineDAO;
import com.advantech.dao.LineUserReferenceDAO;
import com.advantech.model.PrepareSchedule;
import com.advantech.dao.PrepareScheduleDAO;
import com.advantech.model.BabSettingHistory;
import com.advantech.model.Floor;
import com.advantech.model.Line;
import com.advantech.model.LineUserReference;
import com.advantech.model.User;
import static com.google.common.collect.Lists.newArrayList;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import static java.util.Comparator.comparing;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import static java.util.stream.Collectors.toList;
import org.apache.commons.lang3.SerializationUtils;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.Minutes;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Wei.Cheng
 */
@Service
@Transactional
public class PrepareScheduleService {

    @Autowired
    private PrepareScheduleDAO dao;

    public List<PrepareSchedule> findAll() {
        return dao.findAll();
    }

    public PrepareSchedule findByPrimaryKey(Object obj_id) {
        return dao.findByPrimaryKey(obj_id);
    }

    public List<PrepareSchedule> findByFloorAndDate(Floor floor, DateTime sD) {
        return dao.findByFloorAndDate(floor, sD);
    }

    public int insert(PrepareSchedule pojo) {
        return dao.insert(pojo);
    }

    public int update(PrepareSchedule pojo) {
        return dao.update(pojo);
    }

    public int updateAndResortPriority(PrepareSchedule pojo) {
        dao.update(pojo);

        List<PrepareSchedule> l = dao.findByLineAndDate(pojo.getLine(), new DateTime(pojo.getOnBoardDate()));

        l = l.stream().sorted(comparing(PrepareSchedule::getPriority)).collect(toList());

        //Check pojo priority if > list size auto update to last one
        //Update and resort again
        int od = 1;
        for (PrepareSchedule ps : l) {
            if (!Objects.equals(ps, pojo)) {
                if (ps.getPriority() == pojo.getPriority()) {
                    ps.setPriority(++od);
                } else {
                    ps.setPriority(od);
                }
            } else if (Objects.equals(ps, pojo) && ps.getPriority() > l.size()) {
                ps.setPriority(l.size());
            }
            od++;
            dao.update(ps);
        }

        return 1;
    }
    
    public int separateCnt(PrepareSchedule pojo, List<Integer> cnt) {
        int rowCnt = 0;
        int baseCnt = pojo.getScheduleQty();
        BigDecimal baseTimeCost = pojo.getTimeCost();

        Integer sum = cnt.stream().reduce(0, Integer::sum);
        if (baseCnt - sum > 0) {
            cnt.add(baseCnt - sum);
        } else if (sum > baseCnt) {
            return 1;
        }

        for (int c : cnt) {
            if (rowCnt++ == 0) {
                pojo.setScheduleQty(c);
                pojo.setTimeCost(baseTimeCost.divide(new BigDecimal(baseCnt), 2, BigDecimal.ROUND_UP)
                        .multiply(new BigDecimal(c))
                        .setScale(2, BigDecimal.ROUND_UP));
                dao.update(pojo);
            } else {
                PrepareSchedule clone = SerializationUtils.clone(pojo);
                clone.setId(0);
                clone.setScheduleQty(c);
                clone.setTimeCost(baseTimeCost.divide(new BigDecimal(baseCnt), 2, BigDecimal.ROUND_UP)
                        .multiply(new BigDecimal(c))
                        .setScale(2, BigDecimal.ROUND_UP));
                dao.insert(clone);
            }
        }

        return 1;
    }

    public int delete(PrepareSchedule pojo) {
        return dao.delete(pojo);
    }

}
