/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.advantech.controller.db2;

import com.advantech.controller.CrudAction;
import com.advantech.controller.CrudController;
import com.advantech.converter.CrudActionControllerConverter;
import static com.advantech.helper.JqGridResponseUtils.toJqGridResponse;
import com.advantech.jqgrid.PageInfo;
import com.advantech.jqgrid.JqGridResponse;
import com.advantech.model.db2.FlowM4f;
import com.advantech.service.db2.FlowGroupM4fService;
import com.advantech.service.db2.FlowM4fService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Justin.Yeh
 */
@Controller
@RequestMapping(value = "/FlowM4f")
public class FlowM4fController extends CrudController<FlowM4f> {

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        dataBinder.registerCustomEditor(CrudAction.class, new CrudActionControllerConverter());
    }

    @Autowired
    private FlowM4fService flowService;

    @Autowired
    private FlowGroupM4fService flowGroupService;

    @ResponseBody
    @RequestMapping(value = SELECT_URL, method = {RequestMethod.GET})
    @Override
    protected JqGridResponse read(PageInfo info) {
        return toJqGridResponse(flowService.findAll(info), info);
    }

    @ResponseBody
    @RequestMapping(value = INSERT_URL, method = {RequestMethod.POST})
    @Override
    protected ResponseEntity insert(FlowM4f flow, BindingResult bindingResult) {
        String modifyMessage;
        if (isFlowExist(flow)) {
            modifyMessage = "This flow is already exist";
        } else {
            modifyMessage = flowService.insert(flow) == 1 ? this.SUCCESS_MESSAGE : this.FAIL_MESSAGE;
        }
        return serverResponse(modifyMessage);
    }

    @ResponseBody
    @RequestMapping(value = UPDATE_URL, method = {RequestMethod.POST})
    @Override
    protected ResponseEntity update(FlowM4f flow, BindingResult bindingResult) {
        String modifyMessage;
        if (isFlowExist(flow)) {
            modifyMessage = "This flow is already exist";
        } else {
            FlowM4f existFlow = flowService.findByPrimaryKey(flow.getId());
            flow.setFlowsForBabFlowId(existFlow.getFlowsForBabFlowId());
            flow.setFlowsForTestFlowId(existFlow.getFlowsForTestFlowId());
            modifyMessage = flowService.update(flow) == 1 ? this.SUCCESS_MESSAGE : this.FAIL_MESSAGE;
        }
        return serverResponse(modifyMessage);
    }

    @ResponseBody
    @RequestMapping(value = DELETE_URL, method = {RequestMethod.POST})
    @Override
    protected ResponseEntity delete(int id) {
        String modifyMessage = flowService.delete(id) == 1 ? this.SUCCESS_MESSAGE : this.FAIL_MESSAGE;
        return serverResponse(modifyMessage);
    }

    private boolean isFlowExist(FlowM4f flow) {
        return flowService.findByFlowName(flow.getName()) != null;
    }

    //flow 編輯頁面中group的下拉式選單
    @ResponseBody
    @RequestMapping(value = "/flowGroup", method = {RequestMethod.POST})
    protected List findFlowGroup() {
        return flowGroupService.findAll();
    }

    //編輯subgroup用
    @ResponseBody
    @RequestMapping(value = SELECT_URL + "_sub", method = {RequestMethod.GET})
    protected List getFlowOptionByParent(
            @RequestParam int id,
            @ModelAttribute PageInfo info) {
        return flowService.findByParent(id);
    }

    //編輯subgroup用
    @ResponseBody
    @RequestMapping(value = UPDATE_URL + "_sub", method = {RequestMethod.POST})
    protected ResponseEntity updateSubFlow(CrudAction oper, FlowM4f flow, @RequestParam int parentFlowId) {
        String modifyMessage;
        int responseFlag;

        switch (oper) {
            case ADD:
                List<Integer> addSubIds = new ArrayList();
                addSubIds.add(flow.getId());
                responseFlag = flowService.addSub(parentFlowId, addSubIds);
                modifyMessage = responseFlag == 1 ? this.SUCCESS_MESSAGE : this.FAIL_MESSAGE;
                break;
            case EDIT:
                modifyMessage = this.FAIL_MESSAGE;
                break;
            case DEL:
                List deleteSubIds = new ArrayList();
                deleteSubIds.add(flow.getId());
                responseFlag = flowService.deleteSub(parentFlowId, deleteSubIds);
                modifyMessage = responseFlag == 1 ? this.SUCCESS_MESSAGE : this.FAIL_MESSAGE;
                break;
            default:
                modifyMessage = this.FAIL_MESSAGE;
        }

        return ResponseEntity
                .status(SUCCESS_MESSAGE.equals(modifyMessage) ? HttpStatus.CREATED : HttpStatus.FORBIDDEN)
                .body(modifyMessage);
    }

}
