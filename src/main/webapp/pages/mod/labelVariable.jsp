<%-- 
    Created on : 2025/10/18
    Author     : Justin.Yeh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script src="<c:url value="/js/jqgrid-custom-setting.js" />"></script>
<script src="<c:url value="/js/worktime-setting/column-custom-callback.js" />"></script>
<script>
    var scrollPosition = 0;

    $(function () {
        var grid = $("#list");
        var tableName = "LabelVariable";

        setSelectOptions({
            rootUrl: "<c:url value="/" />",
            columnInfo: [
                {name: "labelVariable", nameprefix: "12Aff_", isNullable: false, dataToServer: "2"},
                {name: "labelVariableGroup", isNullable: false}
            ]
        });

        grid.jqGrid({
            url: '<c:url value="/LabelVariable/read" />',
            datatype: 'json',
            mtype: 'GET',
            autoencode: true,
//            guiStyle: "bootstrap",
            colModel: [
                {label: 'id', name: "id", width: 60, key: true, editable: true, search: false, editoptions: {readonly: 'readonly', disabled: true, defaultValue: "0"}},
                {label: 'name', name: "name", width: 60, editable: true, editrules: {required: true}, editoptions: {dataEvents: trimText_event}, formoptions: {elmsuffix: "(*必填)"}},
                {label: 'labelVariable_group', name: "labelVariableGroup.id", editable: true, formatter: selectOptions["labelVariableGroup_func"], edittype: 'select', editoptions: {value: selectOptions["labelVariableGroup"]}, searchrules: {required: true}, stype: "select", searchoptions: {value: selectOptions["labelVariableGroup"], sopt: ['eq'], dataInit: selectOptions["labelVariableGroup_sinit"]}}
            ],
            rowNum: 20,
            rowList: [20, 50, 100, 1000],
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
            sortname: 'name', sortorder: 'asc',
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
            },
            subGrid: true,
            subGridOptions: {
                "plusicon": "ui-icon-triangle-1-e",
                "minusicon": "ui-icon-triangle-1-s",
                "openicon": "ui-icon-arrowreturn-1-e",
                "reloadOnExpand": false,
                "selectOnExpand": true,
                hasSubgrid: function (options) {
                    const hasChildIds = [1];
                    return hasChildIds.includes(options.data["labelVariableGroup.id"]);
                }
            },
            subGridRowExpanded: function (subgrid_id, row_id) {
                var subgrid_table_id, pager_id;
                subgrid_table_id = subgrid_id + "_t";
                pager_id = "p_" + subgrid_table_id;
                $("#" + subgrid_id).html("<table id='" + subgrid_table_id + "' class='scroll'></table><div id='" + pager_id + "' class='scroll'></div>");

                var subgrid = $("#" + subgrid_table_id);
                subgrid.jqGrid({
                    url: '<c:url value="/LabelVariable/read_sub" />',
                    mtype: 'GET',
                    datatype: "json",
                    postData: {
                        id: row_id
                    },
                    colNames: ['id', 'name'],
                    colModel: [
                        {name: "id", index: "id", width: 80, key: true, editable: true, sortable: true, edittype: 'select', editoptions: {value: selectOptions["12Aff_labelVariable"]}},
                        {name: "name", index: "name", width: 130, sortable: true}
                    ],
                    rowNum: 20,
                    pager: pager_id,
                    sortname: "id",
                    sortorder: "asc",
                    autowidth: true,
                    editurl: '<c:url value="/LabelVariable/update_sub" />',
                    jsonReader: {
                        root: "rows",
                        page: "page",
                        total: "total",
                        records: "records",
                        repeatitems: false
                    },
                    height: '100%'
                });
                subgrid.jqGrid('navGrid', "#" + pager_id,
                        {edit: false, add: true, del: true, search: false},
                        {},
                        {
                            zIndex: 9999,
                            beforeSubmit: function (postdata, form) {
                                if (row_id != null) {
                                    // additional parameters
                                    postdata.parentLabelVariableId = row_id;
                                }

                                return [true];
                            }
                        },
                        {
                            zIndex: 9999,
                            onclickSubmit: function (rowid) {
                                var val = $("#" + subgrid_table_id).getCell(rowid, 'id');
                                return {id: val, parentLabelVariableId: row_id};
                            }
                        },
                        {}
                );
            }
        });

        grid.jqGrid('navGrid', '#pager',
                {edit: true, add: true, del: true, search: true},
                {
                    url: '<c:url value="/LabelVariable/update" />',
                    closeAfterEdit: closed_after_edit,
                    reloadAfterSubmit: true,
                    errorTextFormat: customErrorTextFormat,
                    beforeShowForm: greyout,
                    zIndex: 9999
                },
                {
                    url: '<c:url value="/LabelVariable/create" />',
                    closeAfterAdd: closed_after_add,
                    reloadAfterSubmit: true,
                    errorTextFormat: customErrorTextFormat,
                    beforeShowForm: greyout,
                    zIndex: 9999
                },
                {
                    url: '<c:url value="/LabelVariable/delete" />',
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

    });
</script>

<div id="flow-content">
    <!--    <h5 style="color:red" class="form-control">
            ※各站點請以"-"區隔※
        </h5>-->
    <table id="list"></table> 
    <div id="pager"></div>
</div>
