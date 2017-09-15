/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.advantech.converter;

import com.advantech.model.UserProfile;
import com.advantech.service.UserProfileService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 *
 * @author Wei.Cheng
 */
@Component
public class UserProfileConverter implements Converter<String, UserProfile> {

    @Autowired
    private UserProfileService userProfileService;

    @Override
    public UserProfile convert(String s) {
        return userProfileService.findByPrimaryKey(Integer.parseInt(s));
    }

}
