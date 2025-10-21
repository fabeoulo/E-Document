/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.advantech.controller;

import com.advantech.helper.WorktimeMailManager;
import com.advantech.excel.XlsWorkBook;
import com.advantech.excel.XlsWorkSheet;
import com.advantech.helper.WorktimeValidator;
import com.advantech.model.BusinessGroup;
import com.advantech.model.CartonLabel;
import com.advantech.model.Cobot;
import com.advantech.model.Floor;
import com.advantech.model.Flow;
import com.advantech.model.LabelVariable;
import com.advantech.model.OutLabel;
import com.advantech.model.Pending;
import com.advantech.model.PreAssy;
import com.advantech.model.Type;
import com.advantech.model.User;
import com.advantech.model.Worktime;
import com.advantech.service.WorktimeAuditService;
import com.advantech.service.BusinessGroupService;
import com.advantech.service.CartonLabelService;
import com.advantech.service.CobotService;
import com.advantech.service.FloorService;
import com.advantech.service.FlowService;
import com.advantech.service.LabelVariableService;
import com.advantech.service.OutLabelService;
import com.advantech.service.PendingService;
import com.advantech.service.PreAssyService;
import com.advantech.service.TypeService;
import com.advantech.service.UserService;
import com.advantech.service.WorktimeService;
import com.google.gson.Gson;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Wei.Cheng
 */
@Controller
@RequestMapping(value = "/WorktimeBatchMod")
public class WorktimeBatchModController {

    @Autowired
    private WorktimeMailManager worktimeMailManager;

    @Autowired
    private TypeService typeService;

    @Autowired
    private FloorService floorService;

    @Autowired
    private PendingService pendingService;

    @Autowired
    private UserService userService;

    @Autowired
    private FlowService flowService;

    @Autowired
    private PreAssyService preAssyService;

    @Autowired
    private WorktimeService worktimeService;

    @Autowired
    private BusinessGroupService businessGroupService;

    @Autowired
    private CobotService cobotService;

    @Autowired
    private OutLabelService outLabelService;

    @Autowired
    private CartonLabelService cartonLabelService;

    @Autowired
    private LabelVariableService labelVariableService;

    @Autowired
    private WorktimeAuditService auditService;

    @Autowired
    private WorktimeValidator worktimeValidator;

    @Autowired
    private Validator validator;

    @PostConstruct
    protected void initValidator() {
//        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
//        validator = factory.getValidator();
    }

    //Check model is exist.
    @ResponseBody
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    protected String batchInsert(@RequestParam("file") MultipartFile file) throws Exception {

        List<Worktime> hgList = this.transToWorktimes(file, false, false);

        hgList.forEach((w) -> {
            w.setId(0);
        });

        //Validate the column, throw exception when false.
        this.validateWorktime(hgList);

        if (worktimeService.insertByExcel(hgList) == 1) {
            worktimeMailManager.notifyUser(hgList, CrudAction.ADD);
            return "success";
        } else {
            return "fail";
        }

    }

    //Check current revision & model name is duplicate
    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    protected String batchUpdate(@RequestParam("file") MultipartFile file) throws Exception {

        List<Worktime> hgList = this.transToWorktimes(file, true, true);

//        hgList.forEach((w) -> {
//            if (w.getReasonCode() != null) {
//                String trimReasonCode = w.getReasonCode().trim();
//                String reasonCode = trimReasonCode.equals("") ? "0" : trimReasonCode;
//                w.setReasonCode(reasonCode);
//            }
//        });
//
        //Validate the column, throw exception when false.
        validateWorktime(hgList);

        return worktimeService.mergeByExcel(hgList) == 1 ? "success" : "fail";

    }

    //Check model is exist
    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    protected String batchDelete(@RequestParam("file") MultipartFile file) throws Exception {
        List<Worktime> hgList = this.transToWorktimes(file, false, false);
        Integer[] ids = new Integer[hgList.size()];
        for (int i = 0; i < hgList.size(); i++) {
            ids[i] = hgList.get(i).getId();
        }

        if (worktimeService.deleteWithMesUpload(ids) == 1) {
            worktimeMailManager.notifyUser(hgList, CrudAction.DEL);
            return "success";
        } else {
            return "fail";
        }
    }

    @ResponseBody
    @RequestMapping(value = "/reUpdateAllFormulaColumn", method = {RequestMethod.GET})
    protected boolean reUpdateAllFormulaColumn() throws Exception {
        worktimeService.reUpdateAllFormulaColumn();
        return true;
    }

    private List<Worktime> transToWorktimes(MultipartFile file, boolean checkRevision, boolean isKeepStandardTime) throws Exception {
        //固定sheet name為sheet1

        try ( XlsWorkBook workbook = new XlsWorkBook(file.getInputStream())) {
            XlsWorkSheet sheet = workbook.getSheet("sheet1");
            if (sheet == null) {
                throw new Exception("Sheet named \"sheet1\" not found");
            }

            Map<String, Worktime> modelMap = isKeepStandardTime
                    ? worktimeService.findAll().stream().collect(Collectors.toMap(Worktime::getModelName, w -> w))
                    : new HashMap<>();
            //Init not relative column first.
            List<Worktime> hgList = sheet.buildBeans(Worktime.class, modelMap);

            //If id is zero, the action is add.
            if (checkRevision) {
                Integer revisionNum = retriveRevisionNumber(sheet);
                if (revisionNum != null) {
                    checkRevision(hgList, revisionNum);
                }
            }

            hgList = retrieveRelativeColumns(sheet, hgList);
            return hgList;
        }

    }

    public List<Worktime> transToWorktimes(InputStream file, boolean checkRevision) throws Exception {

        try ( XlsWorkBook workbook = new XlsWorkBook(file)) {
            XlsWorkSheet sheet = workbook.getSheet("sheet1");
            if (sheet == null) {
                throw new Exception("Sheet named \"sheet1\" not found");
            }

            //Init not relative column first.
            List<Worktime> hgList = sheet.buildBeans(Worktime.class, new HashMap<>());

            //If id is zero, the action is add.
            if (checkRevision) {
                Integer revisionNum = retriveRevisionNumber(sheet);
                if (revisionNum != null) {
                    checkRevision(hgList, revisionNum);
                }
            }

            hgList = retrieveRelativeColumns(sheet, hgList);
            return hgList;
        }
    }

    //Check revision number is greater than current revision
    private void checkRevision(List<Worktime> l, Integer revisionNum) throws Exception {

        Integer maxAllowRevisionsGap = 10;
        Integer currentRevision = auditService.findLastRevisions().intValue();

        //Check revision history contain update datas or not.
        if (revisionNum < currentRevision) {
            if (currentRevision - revisionNum >= maxAllowRevisionsGap) {
                throw new Exception("資料版本與現有版本差異過多，請重新下載excel再上傳");
            }

            for (int i = revisionNum + 1; i <= currentRevision; i++) {
                List<Worktime> revData = auditService.findModifiedAtRevision(i);
                for (Worktime w : l) {
                    for (Worktime rev_w : revData) {
                        if (rev_w.getId() == w.getId()) {
                            throw new Exception("欲更改的資料包含已逾期的資料行 &lt;" + w.getModelName() + "&gt; ，請重新下載excel再上傳.");
                        }
                    }
                }
            }
        }
        //If all pass, begin update.
    }

    //Check the revision number into info is valid
    //Check the revision append on the last of the excel file
    private Integer retriveRevisionNumber(XlsWorkSheet sheet) throws Exception {
        Object revisionInfo = sheet.getValue(0, "Revision");
        String revKeyWord = "revision: ";
        if (revisionInfo == null || "".equals(revisionInfo)) {
            return null;
//            throw new Exception("Your revision number is not valid!");
        }
        String decodeString = new String(Base64.decodeBase64(revisionInfo.toString().getBytes()));
        if (!decodeString.contains(revKeyWord)) {
            throw new Exception("Can not retrive the revision number!");
        }
        String revNumString = decodeString.split(revKeyWord)[1];
        if (NumberUtils.isNumber(revNumString)) {
            return Integer.parseInt(revNumString);
        } else {
            throw new Exception("Invalid revision number!");
        }
    }

    private boolean validateWorktime(List<Worktime> l) throws Exception {
        worktimeValidator.checkModelNameExists(l);

        Map<String, Map<String, String>> checkResult = new HashMap();
        int count = 2;
        for (Worktime w : l) {
            Set<ConstraintViolation<Worktime>> constraintViolations = validator.validate(w);
            if (!constraintViolations.isEmpty()) {
                Iterator it = constraintViolations.iterator();
                Map errors = new HashMap();
                while (it.hasNext()) {
                    ConstraintViolation violation = (ConstraintViolation) it.next();
                    errors.put(violation.getPropertyPath().toString(), violation.getMessage());
                }
                checkResult.put("Row" + count, errors);
            }
            count++;
        }

        if (checkResult.isEmpty()) {
            return true;
        } else {
            throw new Exception(new Gson().toJson(checkResult));
        }
    }

    private List retrieveRelativeColumns(XlsWorkSheet sheet, List<Worktime> hgList) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException, Exception {
        Map<String, Type> typeOptions = toSelectOptions(typeService.findAll());
        Map<String, Floor> floorOptions = toSelectOptions(floorService.findAll());
        Map<String, User> userOptions = toSelectOptions(userService.findAll());
        Map<String, Flow> flowOptions = toSelectOptions(flowService.findAll());
        Map<String, Pending> pendingOptions = toSelectOptions(pendingService.findAll());
        Map<String, PreAssy> preAssyOptions = toSelectOptions(preAssyService.findAll());
        Map<String, BusinessGroup> businessGroupOptions = toSelectOptions(businessGroupService.findAll());
        Map<String, Cobot> cobotOptions = toSelectOptions(cobotService.findAll());
        Map<String, OutLabel> outLabelOptions = toSelectOptions(outLabelService.findAll());
        Map<String, CartonLabel> cartonLabelOptions = toSelectOptions(cartonLabelService.findAll());
        Map<String, LabelVariable> labelVariable11AffOptions = toSelectOptions(labelVariableService.findByLabelVariableGroup(1));
        Map<String, LabelVariable> labelVariable12AffOptions = toSelectOptions(labelVariableService.findByLabelVariableGroup(2));

        //設定關聯by name
        for (int i = 0; i < hgList.size(); i++) {
            Worktime w = hgList.get(i);
            w.setType(typeOptions.get(getCell(sheet, i, "typeName")));
            w.setFloor(floorOptions.get("3F"));

            String eeUserName = getCellUpperCase(sheet, i, "bpeOwnerName");
            String speUserName = getCellUpperCase(sheet, i, "speOwnerName");
            String qcUserName = getCellUpperCase(sheet, i, "qcOwnerName");
            String mpmUserName = getCellUpperCase(sheet, i, "mpmOwnerName");

            w.setUserByEeOwnerId(valid(eeUserName, userOptions.get(eeUserName)));
            w.setUserBySpeOwnerId(valid(speUserName, userOptions.get(speUserName)));
            w.setUserByQcOwnerId(valid(qcUserName, userOptions.get(qcUserName)));
            w.setUserByMpmOwnerId(valid(mpmUserName, userOptions.get(mpmUserName)));

            String babFlowName = getCellUpperCase(sheet, i, "babFlowName");
            String pkgFlowName = getCellUpperCase(sheet, i, "packingFlowName");
            String testFlowName = getCellUpperCase(sheet, i, "testFlowName");

            w.setFlowByBabFlowId(valid(babFlowName, flowOptions.get(babFlowName)));
            w.setFlowByPackingFlowId(valid(pkgFlowName, flowOptions.get(pkgFlowName)));
            w.setFlowByTestFlowId(valid(testFlowName, flowOptions.get(testFlowName)));

            String labelVariable11AffName = getCell(sheet, i, "labelVariable11AffName");
            String labelVariable12AffName = getCell(sheet, i, "labelVariable12AffName");

            w.setLabelVariable11AffId(valid(labelVariable11AffName, labelVariable11AffOptions.get(labelVariable11AffName)));
            w.setLabelVariable12AffId(valid(labelVariable12AffName, labelVariable12AffOptions.get(labelVariable12AffName)));

            w.setPending(pendingOptions.get(getCell(sheet, i, "pendingName")));

            String preAssyName = getCellUpperCase(sheet, i, "preAssyName");
            w.setPreAssy(valid(preAssyName, preAssyOptions.get(preAssyName)));

            String businessGroupName = getCell(sheet, i, "businessGroupName");
            w.setBusinessGroup(valid(businessGroupName, businessGroupOptions.get(businessGroupName)));

            String cobotNames = getCell(sheet, i, "cobots");
            String[] cobotNamesArr = (cobotNames == null ? new String[0] : cobotNames.trim().split(","));
            Set<Cobot> c = new HashSet();
            for (String cobotName : cobotNamesArr) {
                Cobot cobot = valid(cobotName, cobotOptions.get(cobotName));
                if (cobot != null) {
                    c.add(cobot);
                }
            }
            w.setCobots(c);

            String labelOuterName = getCell(sheet, i, "labelOuterName");
            String labelCartonName = getCell(sheet, i, "labelCartonName");
            w.setLabelOuterId(valid(labelOuterName, outLabelOptions.get(labelOuterName)));
            w.setLabelCartonId(valid(labelCartonName, cartonLabelOptions.get(labelCartonName)));
        }

        return hgList;

    }

    private <T extends Object> Map<String, T> toSelectOptions(List l) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        Map m = new HashMap();
        if (!l.isEmpty()) {
            Object firstObj = l.get(0);
            boolean isUserObject = firstObj instanceof User;
            boolean isUpperCase = firstObj instanceof Flow || firstObj instanceof PreAssy;
            for (Object obj : l) {
                String name = (String) PropertyUtils.getProperty(obj, isUserObject ? "username" : "name");
                m.put(isUserObject || isUpperCase ? name.toUpperCase() : name, obj);
            }
        }
        return m;
    }

    private String getCellUpperCase(XlsWorkSheet sheet, int row, String colName) throws Exception {
        return getCell(sheet, row, colName).toUpperCase();
    }

    private String getCell(XlsWorkSheet sheet, int row, String colName) throws Exception {
        return sheet.getValue(row, colName).toString().trim();
    }

    private <T extends Object> T valid(String objName, T obj) throws Exception {
        if (objName != null && !"".equals(objName) && obj == null) {
            throw new Exception("Object name " + objName + " found but object is not exist.");
        } else {
            return obj;
        }
    }

}
