package com.advantech.service;

import com.advantech.model.Countermeasure;
import com.advantech.dao.CountermeasureDAO;
import com.advantech.model.Bab;
import com.advantech.model.ReplyStatus;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Wei.Cheng
 */
@Service
@Transactional
public class CountermeasureService {

    @Autowired
    private CountermeasureDAO countermeasureDAO;

    @Autowired
    private BabService babService;

    public List<Countermeasure> findAll() {
        return countermeasureDAO.findAll();
    }

    public Countermeasure findByPrimaryKey(Object obj_id) {
        return countermeasureDAO.findByPrimaryKey(obj_id);
    }

    public Countermeasure findByBab(int bab_id) {
        return countermeasureDAO.findByBab(bab_id);
    }

    public int insert(Countermeasure pojo) {
        countermeasureDAO.insert(pojo);
        Bab b = pojo.getBab();
        b.setReplyStatus(ReplyStatus.REPLIED);
        babService.update(b);
        return 1;
    }

    public int update(Countermeasure pojo) {
        return countermeasureDAO.update(pojo);
    }

    public int delete(Countermeasure pojo) {
        return countermeasureDAO.delete(pojo);
    }

}
