<%-- 
    Document   : page2
    Created on : 2026/1/16, 上午 09:35:51
    Author     : Justin.Yeh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<style>
    .danger{
        color: red;
    }
</style>
<script src="<c:url value="/js/jqgrid-custom-select-option-reader.js" />"></script>
<script src="<c:url value="/js/websocket/sockjs.min.js" />"></script>
<script src="<c:url value="/js/websocket/stomp.min.js" />"></script>
<script src="<c:url value="/js/jqgrid-custom-setting.js" />"></script>
<script>
    $(function () {
        var scrollPosition = 0;
        var grid = $("#list");
        var tableName = "WorktimeExtra";

        setSelectOptions({
            rootUrl: "<c:url value="/" />",
            columnInfo: [
                {name: "workCenter", isNullable: false},
                {name: "worktimeStations", isNullable: false},
                {name: "modelNames", isNullable: false}
            ]
        });

//        debugger;
//        var before_edit = function (form) {
////            var rowId = grid.jqGrid('getGridParam', 'selrow');
////            sendMessage(rowId, "LOCK");
//            $("#worktime\\.id").val();
//        };

        grid.jqGrid({
            url: '<c:url value="/WorktimeExtra/read" />',
            datatype: 'json',
            mtype: 'GET',
            autoencode: true,
            colModel: [
                {label: 'id', name: "id", width: 60, key: true, editable: true, editoptions: {readonly: 'readonly', disabled: true, defaultValue: "0"}, search: false},
                {label: 'Work Center', name: "workCenter", width: 60, editable: false, searchrules: {required: true}, searchoptions: search_string_options},
                {label: 'Model', name: "worktime.id", width: 60, editable: true, edittype: "select", editoptions: {value: selectOptions["modelNames"], dataInit: select2_onForm}, formatter: selectOptions["modelNames_func"], stype: "select", searchoptions: {sopt: ['eq'], dataInit: select2_onForm}},
                {label: '製程', name: "process", width: 60, editable: false, searchrules: {required: true}, searchoptions: search_string_options},
                {label: 'station', name: "worktimeAutouploadSetting.id", width: 60, editable: true, edittype: "select", editoptions: {value: selectOptions["worktimeStations"]}, formatter: selectOptions["worktimeStations_func"], stype: "select", searchoptions: {sopt: ['eq']}},
                {label: '項目', name: "item", width: 60, editable: true, editrules: {required: true}, searchrules: {required: true}, searchoptions: search_string_options},
                {label: '工時', name: "extraTime", width: 60, editable: true, editrules: {number: true, required: true}, editoptions: {defaultValue: '0'}, searchrules: number_search_rule, searchoptions: search_decimal_options}
            ],
            rowNum: 20,
            rowList: [20, 50, 100],
            pager: '#pager',
            viewrecords: true,
            autowidth: true,
            shrinkToFit: true,
            hidegrid: true,
            stringResult: true,
            gridview: true,
            jsonReader: {
                root: "rows",
                page: "page",
                total: "total",
                records: "records",
                repeatitems: false
            },
            afterSubmit: function () {
                $(this).jqGrid("setGridParam", {datatype: 'json'});
                return [true];
            },
            navOptions: {reloadGridOptions: {fromServer: true}},
            caption: tableName + " modify",
            height: 450,
            sortname: 'id', sortorder: 'asc',
            onSelectRow: function () {
                scrollPosition = grid.closest(".ui-jqgrid-bdiv").scrollTop();
            },
            gridComplete: function () {
                grid.closest(".ui-jqgrid-bdiv").scrollTop(scrollPosition);
            },
            error: function (xhr, ajaxOptions, thrownError) {
                alert("Ajax Error occurred\n"
                        + "\nstatus is: " + xhr.status
                        + "\nthrownError is: " + thrownError
                        + "\najaxOptions is: " + ajaxOptions
                        );
            }
        });
        grid.jqGrid('navGrid', '#pager',
                {edit: true, add: true, del: true, search: true},
                {
                    url: '<c:url value="/WorktimeExtra/update" />',
                    dataheight: 350,
                    width: 450,
                    closeAfterEdit: closed_after_edit,
                    reloadAfterSubmit: true,
                    errorTextFormat: customErrorTextFormat,
                    beforeShowForm: greyout,
//                    beforeSubmit: before_edit,
                    zIndex: 9999,
                    recreateForm: true,
                    viewPagerButtons: false
                },
                {
                    url: '<c:url value="/WorktimeExtra/create" />',
                    dataheight: 350,
                    width: 450,
                    closeAfterAdd: closed_after_add,
                    reloadAfterSubmit: true,
                    errorTextFormat: customErrorTextFormat,
                    beforeShowForm: greyout,
                    zIndex: 9999,
                    recreateForm: true
                },
                {
                    url: '<c:url value="/WorktimeExtra/delete" />',
                    zIndex: 9999,
                    reloadAfterSubmit: true
                },
                {
                    sopt: ['eq', 'ne', 'lt', 'gt', 'cn', 'bw', 'ew'],
                    closeAfterSearch: closed_after_search,
                    zIndex: 9999,
                    reloadAfterSubmit: true
                }
        );

        grid.navButtonAdd('#pager', {
            caption: "Export to Excel",
            buttonicon: "ui-icon-disk",
            id: "excelDownload1",
            onClickButton: function () {
                var button = $("#excelDownload1");
                excelDownload(button, "<c:url value="/Excel/downloadWorktimeExtra" />");
                return false;
            },
            position: "last"
        });

        function excelDownload(buttonId, url) {
            var button = $(buttonId);
            button.addClass('ui-state-disabled');
            $.fileDownload(url, {
                preparingMessageHtml: "We are preparing your report, please wait...",
                failMessageHtml: "There was a problem generating your report, please try again.",
                data: grid.getGridParam("postData"),
                successCallback: function (url) {
                    button.removeClass('ui-state-disabled');
                },
                failCallback: function (html, url) {
                    button.removeClass('ui-state-disabled');
                }
            });
        }
    });
</script>

<div id="flow-content">
    <table id="list"></table> 
    <div id="pager"></div>
</div>

