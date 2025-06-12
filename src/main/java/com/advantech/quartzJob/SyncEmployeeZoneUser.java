/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.advantech.quartzJob;

import com.advantech.helper.EmployeeZoneUtils;
import com.advantech.model.User;
import com.advantech.security.State;
import com.advantech.service.UserService;
import static com.google.common.collect.Lists.newArrayList;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClientResponseException;

/**
 *
 * @author Wei.Cheng
 */
@Component
public class SyncEmployeeZoneUser {

    // keep jobnumber "000002593_1" map to MES for ModelResponsorUploadPort
    private List<String> specialAccount = newArrayList("guest", "sysop", "000002593_1");

    @Autowired
    private EmployeeZoneUtils ezUtils;

    @Autowired
    private UserService userService;

    public void execute() {
        //Don't remove system admin's user state
        //Prevent locking special account (guest, A-4757_1)
        List<User> users = userService.findActive();
        List<User> updatedUsers = new ArrayList<>(users.size());

        for (User user : users) {
            if (specialAccount.contains(user.getJobnumber())) {
                continue;
            }

            try {
                ezUtils.findUser(user.getJobnumber());
                //Remove user from database

                //Server send 200 when success, 500 when not found
                //How to resolve when server is in real server error?
            } catch (WebClientResponseException ex) {
                //Server will send 500 when user is not found
                //Delete non-exist users
                user.setState(State.LOCKED.getState());
                updatedUsers.add(user);
            }
        }
        
        //If get api data keep getting error, ignore update schedule
        if (users.size() - specialAccount.size() == updatedUsers.size()) {
            return;
        }
        userService.update(updatedUsers);
    }
}
