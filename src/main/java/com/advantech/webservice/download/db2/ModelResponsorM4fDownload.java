/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.advantech.webservice.download.db2;

import com.advantech.helper.CustomPasswordEncoder;
import com.advantech.model.Floor;
import com.advantech.model.Unit;
import com.advantech.model.User;
import com.advantech.model.UserProfile;
import com.advantech.model2.FloorM4f;
import com.advantech.model2.IUserM9;
import com.advantech.model2.UnitM4f;
import com.advantech.model2.UserM4f;
import com.advantech.model2.UserProfileM4f;
import com.advantech.model2.WorktimeM4f;
import com.advantech.security.State;
import com.advantech.service.FloorService;
import com.advantech.service.UnitService;
import com.advantech.service.UserProfileService;
import com.advantech.service.UserService;
import com.advantech.service.db2.FloorM4fService;
import com.advantech.service.db2.UnitM4fService;
import com.advantech.service.db2.UserM4fService;
import com.advantech.service.db2.UserProfileM4fService;
import com.advantech.webservice.Factory;
import com.advantech.webservice.port.ModelResponsorQueryPort;
import com.advantech.webservice.root.DeptIdM9;
import com.advantech.webservice.unmarshallclass.ModelResponsor;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Justin.Yeh
 */
@Component
public class ModelResponsorM4fDownload extends BasicM4fDownload<WorktimeM4f> {

    private static final Logger logger = LoggerFactory.getLogger(ModelResponsorM4fDownload.class);

    @Autowired
    private ModelResponsorQueryPort modelResponsorQueryPort;

    @Autowired
    private UserM4fService userService;

    @Autowired
    private UserProfileM4fService userProfileService;

    @Autowired
    private UnitM4fService unitService;

    @Autowired
    private FloorM4fService floorService;

    private Map<String, UserM4f> userOptions = new HashMap<>();

    private Map<String, UnitM4f> unitOptions = new HashMap<>();

    private Set<UserProfileM4f> userProfiles = new HashSet<>();

    private FloorM4f floor;

    @Autowired
    private UserService userService1;

    @Autowired
    private UserProfileService userProfileService1;

    @Autowired
    private UnitService unitService1;

    @Autowired
    private FloorService floorService1;

    private Map<String, User> userOptions1 = new HashMap<>();

    private Map<String, Unit> unitOptions1 = new HashMap<>();

    private Set<UserProfile> userProfiles1 = new HashSet<>();

    private Floor floor1;

    public void initOptions() {
        try {
            userOptions = super.toSelectOptions(userService.findAll());
            unitOptions = super.toSelectOptions(unitService.findAll());
            UserProfileM4f userProfile = userProfileService.findByType("USER");
            userProfiles = new HashSet<>(Arrays.asList(userProfile));
            floor = floorService.findByPrimaryKey(6);

            userOptions1 = super.toSelectOptions(userService1.findAll());
            unitOptions1 = super.toSelectOptions(unitService1.findAll());
            UserProfile userProfile1 = userProfileService1.findByType("USER");
            userProfiles1 = new HashSet<>(Arrays.asList(userProfile1));
            floor1 = floorService1.findByPrimaryKey(6);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public WorktimeM4f download(WorktimeM4f wt) throws Exception {
        List<ModelResponsor> mesOwners = modelResponsorQueryPort.queryM(wt, Factory.TWM9_OG);
        Map<String, String> errorFields = new HashMap();

        CustomPasswordEncoder encoder = new CustomPasswordEncoder();

        for (DeptIdM9 deptIdM9 : DeptIdM9.values()) {
            try {
                ModelResponsor mr = mesOwners.stream().filter(mo -> mo.getDeptId() == deptIdM9.getCode()).findFirst().orElse(null);
                if (mr == null) {
                    continue;
                }

                String jobNo = mr.getUserNo();
                String email = mr.getEmail();
                if (super.isNullOrEmpty(jobNo) || super.isNullOrEmpty(email)) {
                    continue;
                }
                String username = email.split("@")[0];
                String password = encoder.encode(jobNo);

                User u1 = (User) userOptions1.get(jobNo);
                if (u1 == null) {
                    String state = State.INACTIVE.getState();
                    Unit unit = (Unit) unitOptions1.get(deptIdM9.toString());
                    u1 = new User(0, floor1, unit, email, jobNo, password, 0, username, state, userProfiles1, null, null, null, null);
                    userService1.insert(u1);
                    userOptions1.put(jobNo, u1);
                }
                UserM4f u = (UserM4f) userOptions.get(jobNo);
                if (u == null) {
                    String state = State.ACTIVE.getState();
                    UnitM4f unit = (UnitM4f) unitOptions.get(deptIdM9.toString());
                    u = new UserM4f(0, floor, unit, email, jobNo, password, 0, username, state, userProfiles, null, null, null, null);
                    userService.insert(u);
                    userOptions.put(jobNo, u);
                }

                switch (deptIdM9) {
                    case SPE:
                        wt.setUserBySpeOwnerId(u);
                        break;
                    case EE:
                        wt.setUserByEeOwnerId(u);
                        break;
                    case QC:
                        wt.setUserByQcOwnerId(u);
                        break;
                    case MPM:
                        wt.setUserByMpmOwnerId(u);
                        break;
                    default:
                        break;
                }
            } catch (Exception e) {
                errorFields.put(deptIdM9 + "_Owner", e.getMessage());
            }
        }
        if (!errorFields.isEmpty()) {
            throw new Exception(wt.getModelName() + " 機種負責人從MES讀取失敗: " + errorFields.toString());
        }

        return wt;
    }
}
