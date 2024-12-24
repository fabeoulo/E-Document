//Custom param
var not_null_and_zero_message = "需有值，不可為0";
var or_message = "，其一需有值，不可為0";
var alt_message = "擇一，不可多值";
var when_not_empty_or_null = "不等於0時";
var preAssy = "preAssy\\.id",
        babFlow = "flowByBabFlowId\\.id",
        testFlow = "flowByTestFlowId\\.id",
        packingFlow = "flowByPackingFlowId\\.id";
var AND = "AND", OR = "OR";
//Other field check logic
var notZeroOrNull = function (obj) {
    return obj != null && obj != 0;
};
var needBI = function (obj) {
    return obj != null && obj == 'BI';
};
var needRI = function (obj) {
    return obj != null && obj == 'RI';
};

var checkBurnIn = true;
var disabledWhenBiSampleCheck = function (formObj) {
    return formObj["biSampling"] == "Y";
};

var disabledProfile = function (formObj) {
    return formObj["testProfile"] === "EKI9516";
};

//Flow check logic setting
var flow_check_logic = {
    "PRE-ASSY": [
        {keyword: ["PRE_ASSY"], checkColumn: ["cleanPanel", "totalModule"], checkType: "OR", message: or_message, prmValid: notZeroOrNull}
    ],
    BAB: [
        {keyword: ["ASSY"], checkColumn: ["assy"], message: not_null_and_zero_message, prmValid: notZeroOrNull},
        {keyword: ["T1"], checkColumn: ["t1"], message: not_null_and_zero_message, prmValid: notZeroOrNull},
        {keyword: ["VB"], checkColumn: ["vibration"], message: not_null_and_zero_message, prmValid: notZeroOrNull},
        {keyword: ["H1", "LK"], checkColumn: ["acwVoltage", "dcwVoltage"], checkType: "ALT", message: alt_message, prmValid: notZeroOrNull, disabled: disabledProfile},
//        {keyword: ["H1"], checkColumn: ["gndValue"], message: not_null_and_zero_message, prmValid: notZeroOrNull},
        {keyword: ["H1", "LK"], checkColumn: ["hiPotLeakage", "testProfile"], message: not_null_and_zero_message, prmValid: notZeroOrNull},
        {keyword: ["LK"], checkColumn: ["acwVoltage", "irVoltage", "testProfile", "lltValue"], message: not_null_and_zero_message, prmValid: notZeroOrNull},
        {keyword: ["CB"], checkColumn: ["coldBoot"], message: not_null_and_zero_message, prmValid: notZeroOrNull},

        {keyword: ["BI", "RI"], checkColumn: ["upBiRi", "downBiRi", "biCost"], message: not_null_and_zero_message, prmValid: notZeroOrNull, disabled: disabledWhenBiSampleCheck},
        {keyword: ["BI"], checkColumn: ["burnIn"], message: "內容須為BI", prmValid: needBI, disabled: disabledWhenBiSampleCheck},
        {keyword: ["RI"], checkColumn: ["burnIn"], message: "內容須為RI", prmValid: needRI, disabled: disabledWhenBiSampleCheck}
    ],
    TEST: [
        {keyword: ["T2"], checkColumn: ["t2"], message: not_null_and_zero_message, prmValid: notZeroOrNull},
        {keyword: ["T3"], checkColumn: ["t3"], message: not_null_and_zero_message, prmValid: notZeroOrNull},
        {keyword: ["T4"], checkColumn: ["t4"], message: not_null_and_zero_message, prmValid: notZeroOrNull}
    ],
    PKG: [
        {keyword: ["PKG"], checkColumn: ["packing"], message: not_null_and_zero_message, prmValid: notZeroOrNull},
        {keyword: ["PKG(WET)"], checkColumn: ["weight"], message: not_null_and_zero_message, prmValid: notZeroOrNull}
    ]
};
var field_check_flow_logic = [
    {checkColumn: {name: ["cleanPanel"], equals: false, value: 0}, description: when_not_empty_or_null, targetColumns: [{name: preAssy, keyword: ["PRE_ASSY"]}]},
    {checkColumn: {name: ["totalModule"], equals: false, value: 0}, description: when_not_empty_or_null, targetColumns: [{name: preAssy, keyword: ["PRE_ASSY"]}]},
    {checkColumn: {name: ["assy"], equals: false, value: 0}, description: when_not_empty_or_null, targetColumns: [{name: babFlow, keyword: ["ASSY"]}]},
    {checkColumn: {name: ["t1"], equals: false, value: 0}, description: when_not_empty_or_null, targetColumns: [{name: babFlow, keyword: ["T1"]}]},
    {checkColumn: {name: ["vibration"], equals: false, value: 0}, description: when_not_empty_or_null, targetColumns: [{name: babFlow, keyword: ["VB"]}]},
    {checkColumn: {name: ["hiPotLeakage"], equals: false, value: 0}, description: when_not_empty_or_null, targetColumns: [{name: babFlow, keyword: ["H1", "LK"]}]},
    {checkColumn: {name: ["acwVoltage"], equals: false, value: 0}, description: when_not_empty_or_null, targetColumns: [{name: babFlow, keyword: ["H1"]}], disabled: disabledProfile},
    {checkColumn: {name: ["dcwVoltage"], equals: false, value: 0}, description: when_not_empty_or_null, targetColumns: [{name: babFlow, keyword: ["H1"]}], disabled: disabledProfile},
    //{checkColumn: {name: ["acwVoltage", "irVoltage", "testProfile", "lltValue"], equals: false, value: 0}, description: when_not_empty_or_null, targetColumn: {name: babFlow, keyword: ["LK"]}},
    {checkColumn: {name: ["gndValue"], equals: false, value: 0}, description: when_not_empty_or_null, targetColumns: [{name: babFlow, keyword: ["H1"]}]},
    {checkColumn: {name: ["testProfile"], equals: true, value: "601"}, description: "為601時", targetColumns: [{name: babFlow, keyword: ["LK"]}]},
    {checkColumn: {name: ["coldBoot"], equals: false, value: 0}, description: when_not_empty_or_null, targetColumns: [{name: babFlow, keyword: ["CB"]}]},

    {checkColumn: {name: ["upBiRi"], equals: false, value: 0}, description: when_not_empty_or_null, targetColumns: [{name: babFlow, keyword: ["BI", "RI"]}], disabled: disabledWhenBiSampleCheck},
    {checkColumn: {name: ["downBiRi"], equals: false, value: 0}, description: when_not_empty_or_null, targetColumns: [{name: babFlow, keyword: ["BI", "RI"]}], disabled: disabledWhenBiSampleCheck},
    {checkColumn: {name: ["biCost"], equals: false, value: 0}, description: when_not_empty_or_null, targetColumns: [{name: babFlow, keyword: ["BI", "RI"]}], disabled: disabledWhenBiSampleCheck},
    {checkColumn: {name: ["burnIn"], equals: true, value: "BI"}, description: "內容為BI", targetColumns: [{name: babFlow, keyword: ["BI"]}], disabled: disabledWhenBiSampleCheck},
    {checkColumn: {name: ["burnIn"], equals: true, value: "RI"}, description: "內容為RI", targetColumns: [{name: babFlow, keyword: ["RI"]}], disabled: disabledWhenBiSampleCheck},

    {checkColumn: {name: ["t2"], equals: false, value: 0}, description: when_not_empty_or_null, targetColumns: [{name: testFlow, keyword: ["T2"]}]},
    {checkColumn: {name: ["t3"], equals: false, value: 0}, description: when_not_empty_or_null, targetColumns: [{name: testFlow, keyword: ["T3"]}]},
    {checkColumn: {name: ["t4"], equals: false, value: 0}, description: when_not_empty_or_null, targetColumns: [{name: testFlow, keyword: ["T4"]}]},
    {checkColumn: {name: ["packing"], equals: false, value: 0}, description: when_not_empty_or_null, targetColumns: [{name: packingFlow, keyword: ["PKG"]}]},
    {checkColumn: {name: ["weight"], equals: false, value: 0}, description: when_not_empty_or_null, targetColumns: [{name: packingFlow, keyword: ["PKG(WET)"]}]}
];
//Flow check logic
function fieldCheck(postdata, preAssyVal, babFlowVal, testFlowVal, packingFlowVal) {
    var validationErrors = [];
    for (var i = 0; i < field_check_flow_logic.length; i++) {
        var logic = field_check_flow_logic[i];
        if ("disabled" in logic && logic.disabled(postdata) == true) {
            continue;
        }
        var colInfo = logic.checkColumn;
        var col = colInfo.name;
        var checkBool = colInfo.equals;
        var checkVal = colInfo.value;
        var description = logic.description;
        var targetColInfo = logic.targetColumns;
        var targetMatchStatement = logic.targetMatchStatement;
        var checkResult = false;
        var tempErrors = [];
        for (var j = 0; j < targetColInfo.length; j++) {
            var targetColName = targetColInfo[j].name;
            var targetKeyword = targetColInfo[j].keyword;
            var targetColVal;
            var targetMatchMode = targetColInfo[j].equals;
            switch (targetColName) {
                case preAssy:
                    targetColVal = preAssyVal;
                    break;
                case babFlow:
                    targetColVal = babFlowVal;
                    break;
                case testFlow:
                    targetColVal = testFlowVal;
                    break;
                case packingFlow:
                    targetColVal = packingFlowVal;
                    break;
                default:
                    if (targetColName != null) {
                        targetColVal = postdata[targetColName];
                    } else {
                        throw 'TargetColName not found!';
                    }
            }
            //Check col array's value all equals or not equals to checkCol.val
            var isMatchesRule = col.every(function (el, index, arr) {
                var colName = arr[index];
                var fieldVal = postdata[colName];
                return fieldVal != null && (checkBool == true ? fieldVal == checkVal : fieldVal != checkVal);
            });
            if (checkBool != null) {
                var errorResult = checkFlow(
                        isMatchesRule,
                        targetColName, targetColVal, targetKeyword, targetMatchMode);
                if (errorResult.field != null) {
                    appendFieldInfo(col, description, errorResult);
                    tempErrors.push(errorResult);
                }
                if (targetMatchStatement == OR) {
                    checkResult = checkResult || (errorResult.field == null);
                    if (checkResult == true) {
                        tempErrors = [];
                        break;
                    }
                }
            } else {
                throw "CheckBool is not setting!";
            }
        }
        if (tempErrors.length != 0) {
            validationErrors = validationErrors.concat(tempErrors);
        }
    }
    return validationErrors;
}
function appendFieldInfo(field, description, error) {
    error.code = field + description + ' , ' + error.code;
}
function checkFlow(bool, targetColName, targetColVal, keyword, equals) {
    var err = {};
    if (bool) {
        if (targetColVal != null && targetColVal != "") {
            var keyCheckFlag = false;
            for (var i = 0; i < keyword.length; i++) {
                if (targetColVal.indexOf(keyword[i]) > -1) {
                    keyCheckFlag = true;
                    break;
                }
            }
            if (keyCheckFlag == false) {
                err.field = targetColName;
                err.code = 'flow must conain: ' + keyword;
            }
        } else {
            err.field = targetColName;
            err.code = 'flow must conain: ' + keyword;
        }
    }
    return err;
}

var getColLabels = function (formid, colNames) {
    var labels = [];
    for (var i = 0; i < colNames.length; i++) {
        labels.push(getColLabel(formid, colNames[i]));
    }
    return labels;
};

function getColLabel(formid, colName) {
    return $(formid).find('#' + getSelectorFormat(colName)).parent().prev().html();
}

function getSelectorFormat(colName) {
    return colName.replace(/\./g, "\\.");
}

var op_eq = function (oField, oVal) {
    return oField == oVal;  // "0.0" == 0 is true
};
var op_neq = function (oField, oVal) {
    return oField != oVal;
};
var op_endS = function (oField, oVal) {
    return oField.endsWith(oVal);
};

var field_check_field_logic = [
    {srcColumn: {name: "modelName", operate: op_endS, value: "-ES", description: " 內容為 \"-ES\""}, targetColumn: {name: "businessGroup.id", selOption: "businessGroup_options", operate: op_eq, value: "ES", description: "內容為ES"}},
    {srcColumn: {name: "businessGroup.id", selOption: "businessGroup_options", operate: op_eq, value: "ES", description: "內容為ES"}, targetColumn: {name: "modelName", operate: op_endS, value: "-ES", description: " 內容為 \"-ES\""}},
    {srcColumn: {name: "ssnOnTag", operate: op_eq, value: "Y", description: "內容為Y"}, targetColumn: {name: "labelAssyInput", operate: op_neq, value: "", description: "不為空"}}
];

function fieldCheckField(postdata, formid) {
    var validationErrors = [];
    for (var i = 0; i < field_check_field_logic.length; i++) {
        var logic = field_check_field_logic[i];

        var srcInfo = logic.srcColumn;
        var srcColName = srcInfo.name;
        var srcFieldVal = !srcInfo.selOption ? postdata[srcColName] : getNameBySelectValue(srcInfo.selOption, postdata[srcColName]);
        var srcVal = srcInfo.value;
        var srcLabel = getColLabel(formid, srcColName);
        var srcDesc = srcInfo.description;

        var targetColInfo = logic.targetColumn;
        var targetColName = targetColInfo.name;
        var targetFieldVal = !targetColInfo.selOption ? postdata[targetColName] : getNameBySelectValue(targetColInfo.selOption, postdata[targetColName]);
        var targetVal = targetColInfo.value;
        var targetLabel = getColLabel(formid, targetColName);
        var targetDesc = targetColInfo.description;

        if (srcInfo.operate(srcFieldVal, srcVal)) {
            var isTarValid = targetColInfo.operate(targetFieldVal, targetVal);
            if (!isTarValid) {
                var errorResult = {};
                errorResult.field = getSelectorFormat(targetColName);
                errorResult.code = srcLabel + srcDesc + ' , ' + targetLabel + targetDesc;
                validationErrors.push(errorResult);
            }
        }
    }
    return validationErrors;
}

var getNameBySelectValue = function (selOptionName, selValue) {
    return selectOptions[selOptionName].get(parseInt(selValue));
};

var biSamplingCheckFields = [
    {
        name: "upBiRi",
        checkFunc: notZeroOrNull,
        msg: not_null_and_zero_message
    },
    {
        name: "downBiRi",
        checkFunc: notZeroOrNull,
        msg: not_null_and_zero_message
    },
    {
        name: "biCost",
        checkFunc: notZeroOrNull,
        msg: not_null_and_zero_message
    },
    {
        name: "burnIn",
        checkFunc: needBI,
        msg: "內容須為BI"
    },
    {
        name: babFlow,
        checkFunc: function (obj) {
            return obj.indexOf("BI") == -1 && obj.indexOf("RI") == -1;
        },
        msg: "When BiSampling, babFlow must not contain BI or RI"
    }
];

function checkWhenBiSampling(data, babFlowName) {
    var validationErrors = [];
    var biSampling = data["biSampling"];

    for (var i = 0; i < biSamplingCheckFields.length; i++) {
        var field = biSamplingCheckFields[i];
        var fieldName = field.name;
        var func = field.checkFunc;
        var fieldErrorMsg = field.msg;
        var fieldVal = fieldName == babFlow ? babFlowName : data[fieldName];
        if (func(fieldVal) == false) {
            var err = {};
            err.field = fieldName;
            err.code = fieldErrorMsg;
            validationErrors.push(err);
        }
    }

    if (biSampling == "N") {
        var i = validationErrors.length;
        validationErrors.length = 0;
        if (i == 0) {
            var err = {};
            err.field = "biSampling";
            err.code = "內容須為Y";
            validationErrors.push(err);
        }
    }

    return validationErrors;
}

function checkMacFieldIsValid(data) {
    var validationErrors = [];
//    if (data["macTotalQty"] < data["macPrintedQty"]) {
//        validationErrors.push({
//            field: "macTotalQty",
//            code: "Field Total Qty must large than Printed Qty"
//        });
//    }
    return validationErrors;
}

function registerEndsWithIfIe() {
    //https://developer.mozilla.org/en/docs/Web/JavaScript/Reference/Global_Objects/String/endsWith
    if (!String.prototype.endsWith) {
        String.prototype.endsWith = function (searchString, position) {
            var subjectString = this.toString();
            if (typeof position !== 'number' || !isFinite(position) || Math.floor(position) !== position || position > subjectString.length) {
                position = subjectString.length;
            }
            position -= searchString.length;
            var lastIndex = subjectString.indexOf(searchString, position);
            return lastIndex !== -1 && lastIndex === position;
        };
    }
}