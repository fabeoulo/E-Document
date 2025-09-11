//給予大表下拉式選單auto event

var babFlow_default_value = 344;
var testFlow_default_value = 345;
var preAssy_default_value = 10;
var pkgFlow_default_value = 352;

var burnIn_select_event = [
    {
        type: 'change',
        fn: function (e) {
            var selectOption = $('option:selected', this).text();
            var defaultValue = {
                BI: [0, 60],
                RI: [0, 0],
                N: [0, 0]
            };
            $('input#biTime').val(defaultValue[selectOption][0]);
            $('input#biTemperature').val(defaultValue[selectOption][1]);
        }
    }
];

var cleanRoom_select_event = [
    {
        type: 'change',
        fn: function (e) {
            var selVal = $(this).val();

            if (selVal === "1000") {
                $(".level-checkbox,.level-checkbox-label").show();
            } else {
                $(".level-checkbox,.level-checkbox-label").hide();
            }
        }
    }
];

var pending_select_event = [
    {
        type: 'change',
        fn: function (e) {
            var selectOption = $('option:selected', this).text();
            var defaultValue = {
                "SL": [24],
                "OB": [72],
                "SL+OB": [96],
                "N": [0]
            };
            $('input#pendingTime').val(defaultValue[selectOption][0]);
        }
    }
];

var babFlow_select_event = [
    {
        type: 'change', fn: function (e) {
            reset_value_zero($(this), babFlow_default_value);

//            var sel2 = $("#flowByTestFlowId\\.id");
//            var sel2Val = sel2.val();
//            var selectedValue = testFlow_default_value;
//            $.get('../SelectOption/flow-byParent/' + $(this).val(), function (data) {
//                sel2.html("");
//                sel2.append("<option role='option' value=" + testFlow_default_value + ">NO TEST PROCESS</option>");
//                for (var i = 0; i < data.length; i++) {
//                    sel2.append("<option role='option' value=" + data[i].id + ">" + data[i].name + "</option>");
//                    selectedValue = data[i].id == sel2Val ? sel2Val : selectedValue;
//                }
//                sel2.val(selectedValue);
//            });
        }
    }
];

var testFlow_select_event = [
    {
        type: 'change',
        fn: function (e) {
            reset_value_zero($(this), testFlow_default_value);
        }
    }
];

var preAssy_select_event = [
    {
        type: 'change',
        fn: function (e) {
            reset_value_zero($(this), preAssy_default_value);
        }
    }
];

var pkgFlow_select_event = [
    {
        type: 'change',
        fn: function (e) {
            reset_value_zero($(this), pkgFlow_default_value);
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

var businessGroup_select_event = [
    {
        type: 'change', fn: function (e) {
            var sel3 = $("#workCenter\\.id");
            $.get('../SelectOption/workCenter/' + $(this).val(), function (data) {
                sel3.html("");
//                sel2.append("<option role='option' value=0>empty</option>");
                for (var i = 0; i < data.length; i++) {
                    sel3.append("<option role='option' value=" + data[i].id + ">" + data[i].name + "</option>");
                }
                sel3.children().first().attr("selected", "selected");
            });
        }
    }
];