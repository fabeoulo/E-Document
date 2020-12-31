/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.advantech.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.advantech.repo.BwFieldRepository;

/**
 *
 * @author Wei.Cheng
 */
@Service
@Transactional
public class BwFieldService {

    @Autowired
    private BwFieldRepository repo;

    public int update() {
        return repo.procUpdate();
    }

}
