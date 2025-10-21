/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.advantech.controller;

import com.advantech.converter.CrudActionControllerConverter;
import static com.advantech.helper.JqGridResponseUtils.toJqGridResponse;
import com.advantech.jqgrid.PageInfo;
import com.advantech.model.LabelVariable;
import com.advantech.jqgrid.JqGridResponse;
import com.advantech.service.LabelVariableGroupService;
import com.advantech.service.LabelVariableService;
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
 * @author Wei.Cheng
 */
@Controller
@RequestMapping(value = "/LabelVariable")
public class LabelVariableController extends CrudController<LabelVariable> {

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        dataBinder.registerCustomEditor(CrudAction.class, new CrudActionControllerConverter());
    }

    @Autowired
    private LabelVariableService labelVariableService;

    @Autowired
    private LabelVariableGroupService labelVariableGroupService;

    @ResponseBody
    @RequestMapping(value = SELECT_URL, method = {RequestMethod.GET})
    @Override
    protected JqGridResponse read(PageInfo info) {
        return toJqGridResponse(labelVariableService.findAll(info), info);
    }

    @ResponseBody
    @RequestMapping(value = INSERT_URL, method = {RequestMethod.POST})
    @Override
    protected ResponseEntity insert(LabelVariable labelVariable, BindingResult bindingResult) {
        String modifyMessage;
        if (isLabelVariableExist(labelVariable)) {
            modifyMessage = "This name is already exist";
        } else {
            modifyMessage = labelVariableService.insert(labelVariable) == 1 ? this.SUCCESS_MESSAGE : this.FAIL_MESSAGE;
        }
        return serverResponse(modifyMessage);
    }

    @ResponseBody
    @RequestMapping(value = UPDATE_URL, method = {RequestMethod.POST})
    @Override
    protected ResponseEntity update(LabelVariable labelVariable, BindingResult bindingResult) {
        String modifyMessage;
        if (isLabelVariableExist(labelVariable)) {
            modifyMessage = "This name is already exist";
        } else {
            LabelVariable existLabelVariable = labelVariableService.findByPrimaryKey(labelVariable.getId());
            labelVariable.setLabelVariablesForChildId(existLabelVariable.getLabelVariablesForChildId());
            labelVariable.setLabelVariablesForParentId(existLabelVariable.getLabelVariablesForParentId());
            modifyMessage = labelVariableService.update(labelVariable) == 1 ? this.SUCCESS_MESSAGE : this.FAIL_MESSAGE;
        }
        return serverResponse(modifyMessage);
    }

    @ResponseBody
    @RequestMapping(value = DELETE_URL, method = {RequestMethod.POST})
    @Override
    protected ResponseEntity delete(int id) {
        String modifyMessage = labelVariableService.delete(id) == 1 ? this.SUCCESS_MESSAGE : this.FAIL_MESSAGE;
        return serverResponse(modifyMessage);
    }

    private boolean isLabelVariableExist(LabelVariable labelVariable) {
        return labelVariableService.findByLabelVariableName(labelVariable.getName()) != null;
    }

    //編輯subgroup用
    @ResponseBody
    @RequestMapping(value = SELECT_URL + "_sub", method = {RequestMethod.GET})
    protected List getLabelVariableOptionByParent(
            @RequestParam int id,
            @ModelAttribute PageInfo info) {
        return labelVariableService.findByParent(id);
    }

    //編輯subgroup用
    @ResponseBody
    @RequestMapping(value = UPDATE_URL + "_sub", method = {RequestMethod.POST})
    protected ResponseEntity updateSubLabelVariable(CrudAction oper, LabelVariable labelVariable, @RequestParam int parentLabelVariableId) {
        String modifyMessage;
        int responseFlag;

        switch (oper) {
            case ADD:
                List<Integer> addSubIds = new ArrayList();
                addSubIds.add(labelVariable.getId());
                responseFlag = labelVariableService.addSub(parentLabelVariableId, addSubIds);
                modifyMessage = responseFlag == 1 ? this.SUCCESS_MESSAGE : this.FAIL_MESSAGE;
                break;
            case EDIT:
                modifyMessage = this.FAIL_MESSAGE;
                break;
            case DEL:
                List deleteSubIds = new ArrayList();
                deleteSubIds.add(labelVariable.getId());
                responseFlag = labelVariableService.deleteSub(parentLabelVariableId, deleteSubIds);
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
