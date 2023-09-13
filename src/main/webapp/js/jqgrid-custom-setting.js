var closed_after_add = true;
var closed_after_edit = true;
var closed_after_search = true;

var search_string_options = {sopt: ['eq', 'cn', 'bw', 'ew', 'in']};
var search_decimal_options = {sopt: ['eq', 'lt', 'gt']};
var search_date_options = {sopt: ['eq', 'lt', 'gt'], dataInit: getDate};
var required_form_options = {elmsuffix: "(*必填)"}; //elmprefix: "SSAA", elmsuffix: ''
var formula_hint = {elmsuffix: "(F)"};

var number_search_rule = {number: true, required: true};
var date_search_rule = {date: true, required: true};
var group = [
    {
        Type0 : ['tr_modelName','tr_type.id','tr_businessGroup.id','tr_userBySpeOwnerId.id','tr_userByEeOwnerId.id','tr_userByQcOwnerId.id',
            'tr_userByMpmOwnerId.id','tr_floor.id','tr_keypartA','tr_keypartB','tr_partLink','tr_ce','tr_ul','tr_rohs','tr_weee','tr_madeInTaiwan',
            'tr_fcc','tr_eac','tr_kc','tr_nsInOneCollectionBox','tr_partNoAttributeMaintain','tr_weight','tr_weightAff','tr_tolerance','tr_preAssyModuleQty',
            'tr_burnInQuantity','tr_assyStation','tr_packingStation','tr_createDate','tr_modifiedDate','tr_twm2Flag','tr_cobots']
    },
    {
        Type1 : ['tr_preAssy.id','tr_flowByBabFlowId.id','tr_flowByTestFlowId.id','tr_flowByPackingFlowId.id','tr_cleanPanel','tr_totalModule','tr_assy','tr_t1','tr_t2','tr_t3','tr_t4','tr_packing',
            'tr_burnIn','tr_biTime','tr_biTemperature','tr_upBiRi','tr_downBiRi','tr_biCost','tr_vibration','tr_hiPotLeakage','tr_coldBoot','tr_warmBoot','tr_pending.id','tr_pendingTime','tr_biSampling',
            'tr_assyToT1','tr_t2ToPacking','tr_workCenter','tr_sapWt','tr_productionWt','tr_setupTime','tr_machineWorktime','tr_assyLeadTime','tr_assyKanbanTime','tr_packingLeadTime','tr_packingKanbanTime',
            'tr_cleanPanelAndAssembly','tr_packingKanbanTime']
    },   
    {
        Type2 : ['tr_labelYN','tr_labelOuterId.id','tr_labelOuterCustom','tr_labelCartonId.id','tr_labelCartonCustom','tr_labelBigCarton','tr_label2D','tr_labelCustomerSn','tr_labelSn','tr_labelPn','tr_labelNmodelA','tr_labelNmodelB',
            'tr_labelVariable1','tr_labelVariable2','tr_labelVariable3','tr_labelVariable4','tr_labelVariable5','tr_labelVariable6','tr_labelVariable7','tr_labelVariable8','tr_labelVariable9','tr_labelVariable10',
            'tr_labelVariable1Aff','tr_labelVariable2Aff','tr_labelVariable3Aff','tr_labelVariable4Aff','tr_labelVariable5Aff','tr_labelVariable6Aff','tr_labelVariable7Aff','tr_labelVariable8Aff','tr_labelVariable9Aff','tr_labelVariable10Aff',
            'tr_labelPacking1','tr_labelPacking2','tr_labelPacking3','tr_labelPacking4','tr_labelPacking5','tr_labelPacking6','tr_labelPacking7','tr_labelPacking8','tr_labelPacking9','tr_labelPacking10']
    },
    {
        Type3 : ['tr_macTotalQty','tr_macPrintedQty','tr_labelMac','tr_macPrintedLocation','tr_macPrintedFrom','tr_etlVariable1','tr_etlVariable2','tr_etlVariable3','tr_etlVariable1Aff','tr_etlVariable2Aff','tr_etlVariable3Aff']
    },
    {
        Type4 : ['tr_testProfile','tr_acwVoltage','tr_irVoltage','tr_gndValue','tr_lltValue']
    },
    {
        Type5 : ['tr_t1StatusQty','tr_t1ItemsQty','tr_t2StatusQty','tr_t2ItemsQty']
    }
 ];
 
var customErrorTextFormat = function (response) {
    return '<span class="ui-icon ui-icon-alert" ' +
            'style="float:left; margin-right:.3em;"></span>' +
            response.responseText;
//    alert(response.responseText);
};

var getForm_options = function(options){
    return  {elmsuffix: options ,rowpos:row ,colpos :col}    
};

 var getClassification_Type = function(cssid){
   for(var count=0 ;count < group.length;count++){
       if(group[count]['Type'+count].indexOf(cssid) !== -1){
           return 'Type'+count;
       };
   }
};

var greyout = function ($form) {
    $form.find(".FormElement[readonly]")
            .parent(".DataTD")
            .prop("disabled", true)
            .addClass("ui-state-disabled")
            .prev(".CaptionTD")
            .prop("disabled", true)
            .addClass("ui-state-disabled");
};

function getDate(el) {
    $(el).datepicker({dateFormat: "yy-mm-dd"});
}

    
var errorTextFormatF = function (data) {

    if (data != null && data.responseText != null) {
        return data.responseText;
    }

    // The JSON object that comes from the server contains an array of strings:
    // odd elements are field names, and even elements are error messages.
    // If your JSON has a different format, the code should be adjusted accordingly.

    var validationErrors = $.isArray(data) ? data : data.responseJSON;

    if (validationErrors != null) {
        for (var i = 0; i < validationErrors.length; i++) {
            var selector = ".DataTD #" + validationErrors[i].field;
            $(selector).after("<span title='" + validationErrors[i].code + "' class='glyphicon glyphicon-alert' style='color:red'></span>");
//            $(selector).after(validationErrors[i].code);
        }
    }

    return "There are some errors in the entered data. Hover over the error icons for details.";
};

var clearCheckErrorIcon = function () {
    $(".glyphicon-alert").remove();
};