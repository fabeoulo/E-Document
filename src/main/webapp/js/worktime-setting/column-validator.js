//Custom param
var not_null_and_zero_message = "需有值，不可為0";
var or_message = "，其一需有值，不可為0";
var when_not_empty_or_null = "不等於0時";
var preAssy = "preAssy\\.id",
        babFlow = "flowByBabFlowId\\.id",
        testFlow = "flowByTestFlowId\\.id",
        packingFlow = "flowByPackingFlowId\\.id";

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

//Flow check logic setting
var flow_check_logic = {
    "PRE-ASSY": [
        {keyword: ["PRE_ASSY"], checkColumn: ["pi"], message: not_null_and_zero_message, prmValid: notZeroOrNull}
    ],
    BAB: [
        {keyword: ["ASSY"], checkColumn: ["arFilmAttachment", "cleanPanel", "assy"], message: or_message, checkType: "OR", prmValid: notZeroOrNull},
        {keyword: ["ASSY1"], checkColumn: ["bondedSealingFrame"], message: not_null_and_zero_message, prmValid: notZeroOrNull},
        {keyword: ["ASSY2"], checkColumn: ["assy2"], message: not_null_and_zero_message, prmValid: notZeroOrNull},
        {keyword: ["HB"], checkColumn: ["highBright"], message: not_null_and_zero_message, prmValid: notZeroOrNull},
        {keyword: ["SL"], checkColumn: ["seal"], message: not_null_and_zero_message, prmValid: notZeroOrNull},
        {keyword: ["SL1"], checkColumn: ["seal1"], message: not_null_and_zero_message, prmValid: notZeroOrNull},
        {keyword: ["OB"], checkColumn: ["opticalBonding"], message: not_null_and_zero_message, prmValid: notZeroOrNull},
        {keyword: ["OB1"], checkColumn: ["opticalBonding1"], message: not_null_and_zero_message, prmValid: notZeroOrNull},
        {keyword: ["OB2"], checkColumn: ["opticalBonding2"], message: not_null_and_zero_message, prmValid: notZeroOrNull},
        {keyword: ["T1"], checkColumn: ["t1"], message: not_null_and_zero_message, prmValid: notZeroOrNull},
        {keyword: ["BI", "RI"], checkColumn: ["upBiRi", "downBiRi", "biCost"], message: not_null_and_zero_message, prmValid: notZeroOrNull},
        {keyword: ["BI"], checkColumn: ["burnIn"], message: "內容須為BI", prmValid: needBI},
        {keyword: ["RI"], checkColumn: ["burnIn"], message: "內容須為RI", prmValid: needRI}
    ],
    TEST: [
        {keyword: ["T2"], checkColumn: ["t2"], message: not_null_and_zero_message, prmValid: notZeroOrNull},
        {keyword: ["T3"], checkColumn: ["t3"], message: not_null_and_zero_message, prmValid: notZeroOrNull}
    ],
    PKG: [
        {keyword: ["PKG", "PKG(WET)"], checkColumn: ["packing"], message: not_null_and_zero_message, prmValid: notZeroOrNull},
        {keyword: ["PKG(WET)"], checkColumn: ["weight"], message: not_null_and_zero_message, prmValid: notZeroOrNull}
    ]
};

var field_check_flow_logic = [
    {checkColumn: {name: "pi", equals: false, value: 0}, description: when_not_empty_or_null, targetColumn: {name: preAssy, keyword: ["PRE_ASSY"]}},
    {checkColumn: {name: "arFilmAttachment", equals: false, value: 0}, description: when_not_empty_or_null, targetColumn: {name: babFlow, keyword: ["ASSY"]}},
    {checkColumn: {name: "cleanPanel", equals: false, value: 0}, description: when_not_empty_or_null, targetColumn: {name: babFlow, keyword: ["ASSY"]}},
    {checkColumn: {name: "highBright", equals: false, value: 0}, description: when_not_empty_or_null, targetColumn: {name: babFlow, keyword: ["HB"]}},
    {checkColumn: {name: "assy", equals: false, value: 0}, description: when_not_empty_or_null, targetColumn: {name: babFlow, keyword: ["ASSY"]}},
    {checkColumn: {name: "bondedSealingFrame", equals: false, value: 0}, description: when_not_empty_or_null, targetColumn: {name: babFlow, keyword: ["ASSY1"]}},
    {checkColumn: {name: "assy2", equals: false, value: 0}, description: when_not_empty_or_null, targetColumn: {name: babFlow, keyword: ["ASSY2"]}},
    {checkColumn: {name: "seal", equals: false, value: 0}, description: when_not_empty_or_null, targetColumn: {name: babFlow, keyword: ["SL"]}},
    {checkColumn: {name: "seal1", equals: false, value: 0}, description: when_not_empty_or_null, targetColumn: {name: babFlow, keyword: ["SL1"]}},
    {checkColumn: {name: "opticalBonding", equals: false, value: 0}, description: when_not_empty_or_null, targetColumn: {name: babFlow, keyword: ["OB"]}},
    {checkColumn: {name: "opticalBonding1", equals: false, value: 0}, description: when_not_empty_or_null, targetColumn: {name: babFlow, keyword: ["OB1"]}},
    {checkColumn: {name: "opticalBonding2", equals: false, value: 0}, description: when_not_empty_or_null, targetColumn: {name: babFlow, keyword: ["OB2"]}},
    {checkColumn: {name: "t1", equals: false, value: 0}, description: when_not_empty_or_null, targetColumn: {name: babFlow, keyword: ["T1"]}},
    {checkColumn: {name: "burnIn", equals: true, value: "BI"}, description: "內容為BI", targetColumn: {name: babFlow, keyword: ["BI"]}},
    {checkColumn: {name: "burnIn", equals: true, value: "RI"}, description: "內容為RI", targetColumn: {name: babFlow, keyword: ["RI"]}},
    {checkColumn: {name: "biCost", equals: false, value: 0}, description: when_not_empty_or_null, targetColumn: {name: babFlow, keyword: ["BI"]}},
    {checkColumn: {name: "t2", equals: false, value: 0}, description: when_not_empty_or_null, targetColumn: {name: testFlow, keyword: ["T2"]}},
    {checkColumn: {name: "t3", equals: false, value: 0}, description: when_not_empty_or_null, targetColumn: {name: testFlow, keyword: ["T3"]}},
    {checkColumn: {name: "packing", equals: false, value: 0}, description: when_not_empty_or_null, targetColumn: {name: packingFlow, keyword: ["PKG", "PKG(WET)"]}},
    {checkColumn: {name: "weight", equals: false, value: 0}, description: when_not_empty_or_null, targetColumn: {name: packingFlow, keyword: ["PKG(WET)"]}}
];

//Flow check logic
function fieldCheck(formid, postdata, preAssyVal, babFlowVal, testFlowVal, packingFlowVal) {
    var validationErrors = [];
    for (var i = 0; i < field_check_flow_logic.length; i++) {
        var logic = field_check_flow_logic[i];
        var colInfo = logic.checkColumn;

        var colName = colInfo.name;
        var checkBool = colInfo.equals;
        var fieldVal = postdata[colName];
        var checkVal = colInfo.value;

        var description = logic.description;

        var targetColInfo = logic.targetColumn;
        var targetColName = targetColInfo.name;
        var targetKeyword = targetColInfo.keyword;
        var targetColVal;

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
                throw 'TargetColName not found!';
        }

        if (checkBool != null) {
            var errorResult = checkFlow(
                    (fieldVal != null && (checkBool == true ? fieldVal == checkVal : fieldVal != checkVal)),
                    targetColName, targetColVal, targetKeyword);
            if (errorResult.field != null) {
                appendFieldInfo(getColLabel(formid, colName), description, errorResult);
                validationErrors.push(errorResult);
            }
        } else {
            throw "CheckBool is not setting!";
        }
    }
    return validationErrors;
}

function appendFieldInfo(field, description, error) {
    error.code = field + description + ' , ' + error.code;
}

//Field check flow
function checkFlow(bool, targetColName, targetColVal, keyword) {
    var err = {};
    if (bool) {
        if (targetColVal != null && targetColVal != "") {

            var flowParts = flowSplit(targetColName, targetColVal);

            var keyCheckFlag = false;
            for (var i = 0; i < keyword.length; i++) {
                if (flowParts.includes(keyword[i])) {
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

function flowSplit(category, fullFlow) {
    var flowParts;
    if (category === "PRE-ASSY" || category === preAssy) {
        flowParts = fullFlow.split(/[-]/);
    } else {
        flowParts = fullFlow.split(/[-_]/); // split by '-' or '_'    
    }
    return flowParts;
}
//Flow check field
function flowCheck(logicArrName, flowName, formObj, formid) {
    var logicArr = flow_check_logic[logicArrName];

    if (logicArr == null) {
        throw 'logicArr ' + logicArrName + ' not found!';
    }

    if (flowName == null) {
        flowName = '';
    }

    var flowParts = flowSplit(logicArrName, flowName);

    var validationErrors = [];
    for (var i = 0; i < logicArr.length; i++) {
        var logic = logicArr[i];
        var keyword = logic.keyword;
        for (var j = 0; j < keyword.length; j++) {
            if (flowParts.includes(keyword[j])) {
                var checkCol = logic.checkColumn;
                var checkType = logic.checkType;
                if (checkType == null) { //And logic check
                    for (var k = 0; k < checkCol.length; k++) {
                        var colName = checkCol[k];
                        if (!logic.prmValid(formObj[colName])) {
                            validationErrors.push({
                                field: colName,
                                code: logic.message
                            });
                        }
                    }
                } else if (checkType == 'OR') { //Or logic check
                    var checkFlag = false;
                    var tempArr = [];
                    for (var k = 0; k < checkCol.length; k++) {
                        var colName = checkCol[k];
                        if (!logic.prmValid(formObj[colName])) {
                            tempArr.push({
                                field: colName,
                                code: getColLabels(formid, checkCol) + logic.message
                            });
                            checkFlag = checkFlag || false;
                        } else {
                            checkFlag = checkFlag || true;
                        }
                    }
                    if (checkFlag == false) {
                        validationErrors = validationErrors.concat(tempArr);
                    }
                }
            }
        }
    }
    return validationErrors;
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
    {srcColumn: {name: "pressureCooker", operate: op_eq, value: "Y", description: "內容為Y"}, targetColumn: {name: "pressureCookerCost", operate: op_neq, value: 0, description: not_null_and_zero_message}},
    {srcColumn: {name: "pressureCookerCost", operate: op_neq, value: 0, description: when_not_empty_or_null}, targetColumn: {name: "pressureCooker", operate: op_eq, value: "Y", description: "內容為Y"}},
    {srcColumn: {name: "modelName", operate: op_endS, value: "-ES", description: " do contain \"-ES\""}, targetColumn: {name: "businessGroup.id", selOption: "businessGroup_options", operate: op_eq, value: "ES", description: "內容為ES"}},
    {srcColumn: {name: "businessGroup.id", selOption: "businessGroup_options", operate: op_eq, value: "ES", description: "內容為ES"}, targetColumn: {name: "modelName", operate: op_endS, value: "-ES", description: " do contain \"-ES\""}}
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

        if (srcFieldVal && srcInfo.operate(srcFieldVal, srcVal)) {
            var isTarValid = targetColInfo.operate(targetFieldVal, targetVal);
            if (targetFieldVal && !isTarValid) {
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

function checkLevelIsValid(postdata) {
    var validationErrors = [];
    var roomLevel = postdata["cleanRoomLevel"];
    switch (roomLevel) {
        case "1000":
            if (!$(".level-checkbox").is(":checked")) {
                var err = {};
                err.field = "cleanRoomLevel";
                err.code = "至少勾選一個套入1k級";
                validationErrors.push(err);
            }
            if ($("#f_productionWt").is(":not(:checked)")) {
                var err = {};
                err.field = "productionWt";
                err.code = "1k級必須勾選套入公式";
                validationErrors.push(err);
            }
            break;
        case "10000":
            $(".level-checkbox").prop("checked", false);
            break;
        default:
            var err = {};
            err.field = "cleanRoomLevel";
            err.code = "確認機種作業環境等級";
            validationErrors.push(err);
    }
    return validationErrors;
}
