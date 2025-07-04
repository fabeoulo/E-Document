
//給予大表下拉式選單auto event

var babFlow_default_value_m4f = 313;
var testFlow_default_value_m4f = 314;
var preAssy_default_value_m4f = 16;
var pkgFlow_default_value_m4f = 391;

var burnIn_select_event_m4f = [
    {
        type: 'change',
        fn: function (e) {
            var selectOption = $('option:selected', this).text();
            var defaultValue = {
                BI: [4, 40],
                RI: [4, 0],
                N: [0, 0]
            };
            $('input#biTime').val(defaultValue[selectOption][0]);
            $('input#biTemperature').val(defaultValue[selectOption][1]);
        }
    }
];

var testProfile_select_event_m4f = [
    {
        type: 'change',
        fn: function (e) {
            var selectOption = $('option:selected', this).text();
            var defaultValue = {
                "0": ["0", "0", "0", "0", "0"],
                "M5": ["1770", "0", "25", "500", "3500,3500,3500,3500"],
                "601": ["1776", "0", "0", "500", "100,500,500,1000,500,100,500,500,1000,500"],
                "B-B": ["0", "2100", "0", "0", "0"],
                "EKI9528": ["0", "1500", "0", "0", "0"],
                "EKI9516": ["0", "0", "0", "100", "0"],
                "EKI9520": ["0", "1200,1200,1200,600,600", "0", "0", "0"],
                "ITA": ["0", "1500,750", "0", "500", "0"],
                "EKI-9508": ["0", "1200,1200,600", "0", "0", "0"]
            };
            $('input#acwVoltage').val(defaultValue[selectOption][0]);
            $('input#dcwVoltage').val(defaultValue[selectOption][1]);
            $('input#gndValue').val(defaultValue[selectOption][2]);
            $('input#irVoltage').val(defaultValue[selectOption][3]);
            $('input#lltValue').val(defaultValue[selectOption][4]);
        }
    }
];

var pending_select_event_m4f = [
    {
        type: 'change',
        fn: function (e) {
            var selectOption = $('option:selected', this).text();
            $('input#pendingTime').val(selectOption == 'N' ? 0 : '');
        }
    }
];

var babFlow_select_event_m4f = [
    {
        type: 'change',
        fn: function (e) {
            reset_value_zero($(this), babFlow_default_value_m4f);

            flow_LK_hint();
            var sel2 = $("#flowByTestFlowId\\.id");
            var sel2Val = sel2.val();
            var selectedValue = testFlow_default_value_m4f;

            $.get('../SelectOptionM4f/flow-byParent/' + $(this).val(), function (data) {
                sel2.html("");
                sel2.append("<option role='option' value=" + testFlow_default_value_m4f + ">No TEST Process</option>");
                for (var i = 0; i < data.length; i++) {
                    sel2.append("<option role='option' value=" + data[i].id + ">" + data[i].name + "</option>");
                    selectedValue = data[i].id == sel2Val ? sel2Val : selectedValue;
                }
                sel2.val(selectedValue);
            });
        }
    }
];

var testFlow_select_event_m4f = [
    {
        type: 'change',
        fn: function (e) {
            flow_LK_hint();
        }
    }
];

var preAssy_select_event_m4f = [
    {
        type: 'change',
        fn: function (e) {
            reset_value_zero($(this), preAssy_default_value_m4f);
        }
    }
];

var pkgFlow_select_event_m4f = [
    {
        type: 'change',
        fn: function (e) {
            reset_value_zero($(this), pkgFlow_default_value_m4f);
        }
    }
];

function reset_value_zero($sel, selectedValue) {
    var val = $sel.val();
    if (val == 0) {
        $sel.val(selectedValue);
    }
    $sel.find('option[value="0"]').remove();
}

var flow_LK_hint = function (sel) {
    var selVal = $("#flowByBabFlowId\\.id :selected").text();
    var selVal2 = $("#flowByTestFlowId\\.id :selected").text();
    var keypartField = $("#keypartB");
    keypartField.parent().find("b").remove();
    if ((selVal != null && selVal.indexOf("LK") != -1) || (selVal2 != null && selVal2.indexOf("LK") != -1)) {
        keypartField.after("<b style='color:red'>後段Key part?</b>");
    }
};

var businessGroup_select_event_m4f = [
    {
        type: 'change',
        fn: function (e) {
            var selectOption = $('option:selected', this).text();
            var defaultValue = {
                M4F: "ASSY-A",
                M4FES: "ES-M9"
            };
            $('input#workCenter').val(defaultValue[selectOption]);
        }
    }
];

var nsInOneCollectionBox_change_event_m4f = [
    {
        type: 'change',
        fn: function (e) {
            var t = $(this).val();
            $('input#weightAff').val(t >= 2 ? 0.05 : 0);
        }
    },
    {
        type: 'keyup',
        fn: function (e) {
            var t = $(this).val();
            $('input#weightAff').val(t >= 2 ? 0.05 : 0);
        }
    }
];
