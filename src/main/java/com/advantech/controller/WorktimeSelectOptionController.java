/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.advantech.controller;

import com.advantech.model.BusinessGroup;
import com.advantech.model.Cobot;
import com.advantech.model.Floor;
import com.advantech.model.Flow;
import com.advantech.model.FlowGroup;
import com.advantech.model.Pending;
import com.advantech.model.PreAssy;
import com.advantech.model.Type;
import com.advantech.model.Unit;
import com.advantech.model.User;
import com.advantech.model.UserNotification;
import com.advantech.model.UserProfile;
import com.advantech.model.OutLabel;
import com.advantech.model.CartonLabel;
import com.advantech.service.BusinessGroupService;
import com.advantech.service.CobotService;
import com.advantech.service.FloorService;
import com.advantech.service.FlowGroupService;
import com.advantech.service.FlowService;
import com.advantech.service.UserService;
import com.advantech.service.PendingService;
import com.advantech.service.PreAssyService;
import com.advantech.service.TypeService;
import com.advantech.service.UnitService;
import com.advantech.service.UserNotificationService;
import com.advantech.service.UserProfileService;
import com.advantech.service.OutLabelService;
import com.advantech.service.CartonLabelService;
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
 * @author Wei.Cheng 供大表的lazy loading欄位編輯與查詢
 */
@Controller
@RequestMapping(value = "/SelectOption")
public class WorktimeSelectOptionController {

    @Autowired
    private FloorService floorService;

    @Autowired
    private UserService userService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private FlowService flowService;

    @Autowired
    private FlowGroupService flowGroupService;

    @Autowired
    private PreAssyService preAssyService;

    @Autowired
    private PendingService pendingService;

    @Autowired
    private UnitService unitService;

    @Autowired
    private BusinessGroupService businessGroupService;

    @Autowired
    private UserProfileService userProfileService;

    @Autowired
    private UserNotificationService userNotificationService;

    @Autowired
    private StandardWorkReasonQueryPort standardWorkReasonQueryPort;

    @Autowired
    private CobotService cobotService;

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
    protected List<Type> getTypeOption() {
        return typeService.findAll();
    }

    @ResponseBody
    @RequestMapping(value = "/flow", method = {RequestMethod.GET})
    public List<Flow> getAllFlowOption() {
        return flowService.findAll();
    }

    @ResponseBody
    @RequestMapping(value = "/flow/{flowGroupId}", method = {RequestMethod.GET})
    protected List<Flow> getFlowOption(@PathVariable(value = "flowGroupId") final int flowGroupId) {
        return flowService.findByFlowGroup(flowGroupId);
    }

    @ResponseBody
    @RequestMapping(value = "/flow-byParent/{parentFlowId}", method = {RequestMethod.GET})
    protected List<Flow> getFlowOptionByParent(@PathVariable(value = "parentFlowId") final int parentFlowId) {
        return flowService.findByParent(parentFlowId);
    }

    @ResponseBody
    @RequestMapping(value = "/flowGroup", method = {RequestMethod.GET})
    protected List<FlowGroup> getFlowGroupOption() {
        return flowGroupService.findAll();
    }

    @ResponseBody
    @RequestMapping(value = "/preAssy", method = {RequestMethod.GET})
    protected List<PreAssy> getPreAssyOption() {
        return preAssyService.findAll();
    }

    @ResponseBody
    @RequestMapping(value = "/pending", method = {RequestMethod.GET})
    protected List<Pending> getPendingOption() {
        return pendingService.findAll();
    }


    @ResponseBody
    @RequestMapping(value = "/unit", method = {RequestMethod.GET})
    protected List<Unit> getUnitOption() {
        return unitService.findAll();
    }

    @ResponseBody
    @RequestMapping(value = "/businessGroup", method = {RequestMethod.GET})
    protected List<BusinessGroup> getBusinessGroupOption() {
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
    protected List<Cobot> getCobotOption() throws Exception {
        return cobotService.findAll();
    }
}
