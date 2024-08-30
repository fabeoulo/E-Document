/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.advantech.controller.db2;

import com.advantech.model.Floor;
import com.advantech.model.User;
import com.advantech.model.UserNotification;
import com.advantech.model.UserProfile;
import com.advantech.model.OutLabel;
import com.advantech.model.CartonLabel;
import com.advantech.model.db2.BusinessGroupM4f;
import com.advantech.model.db2.CobotM4f;
import com.advantech.model.db2.FlowGroupM4f;
import com.advantech.model.db2.FlowM4f;
import com.advantech.model.db2.PendingM4f;
import com.advantech.model.db2.PreAssyM4f;
import com.advantech.model.db2.TypeM4f;
import com.advantech.model.db2.UnitM4f;
import com.advantech.service.FloorService;
import com.advantech.service.UserService;
import com.advantech.service.UserNotificationService;
import com.advantech.service.UserProfileService;
import com.advantech.service.OutLabelService;
import com.advantech.service.CartonLabelService;
import com.advantech.service.db2.BusinessGroupM4fService;
import com.advantech.service.db2.CobotM4fService;
import com.advantech.service.db2.FlowGroupM4fService;
import com.advantech.service.db2.FlowM4fService;
import com.advantech.service.db2.PendingM4fService;
import com.advantech.service.db2.PreAssyM4fService;
import com.advantech.service.db2.TypeM4fService;
import com.advantech.service.db2.UnitM4fService;
import com.advantech.service.db2.UserM4fService;
import com.advantech.webservice.port.StandardWorkReasonQueryPort;
import com.advantech.webservice.unmarshallclass.StandardWorkReason;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Justin.Yeh 供大表的lazy loading欄位編輯與查詢
 */
@Controller
@RequestMapping(value = "/SelectOptionM4f")
public class WorktimeSelectOptionM4fController {

    @Autowired
    private FloorService floorService;

//    @Autowired 
//    private UserService userServiceM3;
//
    @Autowired
    private UserM4fService userService;

    @Autowired
    private TypeM4fService typeService;

    @Autowired
    private FlowM4fService flowService;

    @Autowired
    private FlowGroupM4fService flowGroupService;

    @Autowired
    private PreAssyM4fService preAssyService;

    @Autowired
    private PendingM4fService pendingService;

    @Autowired
    private UnitM4fService unitService;

    @Autowired
    private BusinessGroupM4fService businessGroupService;

    @Autowired
    private UserProfileService userProfileService;

    @Autowired
    private UserNotificationService userNotificationService;

    @Autowired
    private StandardWorkReasonQueryPort standardWorkReasonQueryPort;

    @Autowired
    private CobotM4fService cobotService;

    @Autowired
    private OutLabelService outlabelService;

    @Autowired
    private CartonLabelService cartonlabelService;

    @ResponseBody
    @RequestMapping(value = "/outlabel", method = {RequestMethod.GET})
    protected List<OutLabel> getOutLabelOption() {
        return outlabelService.findAll();
    }

    @ResponseBody
    @RequestMapping(value = "/cartonlabel", method = {RequestMethod.GET})
    protected List<CartonLabel> getCartonLabelOption() {
        return cartonlabelService.findAll();
    }

    @ResponseBody
    @RequestMapping(value = "/floor", method = {RequestMethod.GET})
    protected List<Floor> getFloorOption() {
        return floorService.findByPrimaryKeys(1, 2);
    }

    @ResponseBody
    @RequestMapping(value = "/user-floor", method = {RequestMethod.GET})
    protected List<Floor> getUserFloorOption() {
        return floorService.findAll();
    }

    @ResponseBody
    @RequestMapping(value = "/user/{unitName}", method = {RequestMethod.GET})
    protected List<User> getUserOption(@PathVariable(value = "unitName") final String unitName) {
        List l = userService.findByUnitName(unitName);
        if ("SPE".equals(unitName) || "EE".equals(unitName)) {
            l.addAll(userService.findByUnitName("SPE_AND_EE"));
        }
        return l;
    }

    @ResponseBody
    @RequestMapping(value = "/type", method = {RequestMethod.GET})
    protected List<TypeM4f> getTypeOption() {
        return typeService.findAll();
    }

    @ResponseBody
    @RequestMapping(value = "/flow", method = {RequestMethod.GET})
    public List<FlowM4f> getAllFlowOption() {
        return flowService.findAll();
    }

    @ResponseBody
    @RequestMapping(value = "/flow/{flowGroupId}", method = {RequestMethod.GET})
    protected List<FlowM4f> getFlowOption(@PathVariable(value = "flowGroupId") final int flowGroupId) {
        return flowService.findByFlowGroup(flowGroupId);
    }

    @ResponseBody
    @RequestMapping(value = "/flow-byParent/{parentFlowId}", method = {RequestMethod.GET})
    protected List<FlowM4f> getFlowOptionByParent(@PathVariable(value = "parentFlowId") final int parentFlowId) {
        return flowService.findByParent(parentFlowId);
    }

    @ResponseBody
    @RequestMapping(value = "/flowGroup", method = {RequestMethod.GET})
    protected List<FlowGroupM4f> getFlowGroupOption() {
        return flowGroupService.findAll();
    }

    @ResponseBody
    @RequestMapping(value = "/preAssy", method = {RequestMethod.GET})
    protected List<PreAssyM4f> getPreAssyOption() {
        return preAssyService.findAll();
    }

    @ResponseBody
    @RequestMapping(value = "/pending", method = {RequestMethod.GET})
    protected List<PendingM4f> getPendingOption() {
        return pendingService.findAll();
    }

    @ResponseBody
    @RequestMapping(value = "/unit", method = {RequestMethod.GET})
    protected List<UnitM4f> getUnitOption() {
        return unitService.findAll();
    }

    @ResponseBody
    @RequestMapping(value = "/businessGroup", method = {RequestMethod.GET})
    protected List<BusinessGroupM4f> getBusinessGroupOption() {
        return businessGroupService.findAll();
    }

    @ResponseBody
    @RequestMapping(value = "/userProfiles", method = {RequestMethod.GET})
    protected List<UserProfile> getUserProfileOption(HttpServletRequest request) {
        List<UserProfile> l = userProfileService.findAll();
        if (!request.isUserInRole("ROLE_ADMIN")) {
            l.removeIf(item -> "ADMIN".equals(item.getName()));
        }
        return l;
    }

    @ResponseBody
    @RequestMapping(value = "/userUserNotifications", method = {RequestMethod.GET})
    protected List<UserNotification> getUserNotificationOption() {
        return userNotificationService.findAll();
    }

    @ResponseBody
    @RequestMapping(value = "/modReasonCode", method = {RequestMethod.GET})
    protected List<StandardWorkReason> getModReasonCode() throws Exception {
        try {
            return standardWorkReasonQueryPort.query();
        } catch (Exception ex) {
            throw new Exception("Error while getting mod reason code select options");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/cobots", method = {RequestMethod.GET})
    protected List<CobotM4f> getCobotOption() throws Exception {
        return cobotService.findAll();
    }
}
