var rootUrl;
var selectOptions = {};
var selectableColumns;
var rootPath;
var selectBiRiColumns;
var connPattern = "X";

function setSelectOptions(info) {
    rootUrl = info.rootUrl;
    selectableColumns = info.columnInfo;
    syncSelectOptionInfo();
}

function syncSelectOptionInfo() {
    for (var i = 0; i < selectableColumns.length; i++) {
        var column = selectableColumns[i];
        var columnName = column.name;

        var col_optionDatas = getSelectOption(columnName, column.isNullable, column.dataToServer);
        var col_options = getOptionNameMap(col_optionDatas);

        if (column.nameprefix != null) {
            columnName = column.nameprefix + columnName;
        }

        selectOptions[columnName + '_options'] = col_options;
        selectOptions[columnName] = optionsStringify(col_options);
        selectOptions[columnName + '_func'] = getFunc(columnName);
        selectOptions[columnName + '_init'] = getInitDisableFn(col_optionDatas, column.initFunc);
        selectOptions[columnName + '_sinit'] = getSinitFn();
    }
}

function getSelectOption(columnName, isNullable, data) {
    var result = new Map();
    var url = rootUrl + 'SelectOption/' + (data == null ? columnName : columnName + '/' + data);
    $.ajax({
        type: 'GET',
        url: url,
        async: false,
        success: function (response) {
            var arr = response;
            if (isNullable) {
                var optionData = {name: 'empty'};
                result.set(0, optionData);
            }
            for (var i = 0; i < arr.length; i++) {
                var obj = arr[i];
                var optionData = {};
                if (columnName === 'user') {
                    optionData['name'] = obj.username;
                } else {
                    optionData['name'] = obj.name;
                }

                optionData['disable'] = obj.disable;
                result.set(obj.id, optionData);
            }
        },
        error: function (xhr, ajaxOptions, thrownError) {
            console.log(xhr.responseText);
        }
    });
    return result;
}

function getOptionNameMap(map) {
    var colOption = new Map();
    map.forEach(function (value, key, map) {
        colOption.set(key, value.name);
    });
    return colOption;
}

function optionsStringify(map) {
    var str = '';
    map.forEach(function (value, key, map) {
        str += (key + ':' + value + ';');
    });
    return str.replace(/.$/, "");
}

//http://stackoverflow.com/questions/19696015/javascript-creating-functions-in-a-for-loop
//use closure 
function getFunc(columnName) {
    return function (cellvalue, options, rowObject) {
        var map = selectOptions[columnName + '_options'];
        var obj = map.get(cellvalue);
        return obj == null ? '' : obj;
    };
}

// for add/edit
// invoke by every add/edit, i.e. original $(elem>option) doesn't have 'disabled'
function getInitDisableFn(col_optionDatas, initFunc) {
    return function (elem) {
        col_optionDatas.forEach(function (value, key, map) {
            var disable = value.disable;
            $(elem).find('option[value="' + key + '"]').prop('disabled', disable === 1);
        });

        if (initFunc != null) {
            initFunc(elem);
        }
    };
}

// for search
function getSinitFn() {
    return function (elem) {
        // set this function to run , else search init will run edit init, i.e. getInitDisableFn().
        //$('option:disabled', this).prop('disabled', false);
    };
}

function businessGroupInit(elem) {
    $(elem).find('option:disabled').detach().appendTo(elem);
}

function setJsonOptions(info) {
    rootPath = info.rootPath;
    selectBiRiColumns = info.columnInfo;
    syncJsonOptionInfo();
}

function getJson(path) {
    var jdata;
    $.ajax({
        url: path,
        type: 'GET',
        dataType: 'json',
        async: false,
        success: function (data) {
            jdata = data;
        },
        error: function (xhr, status, error) {
            console.error(error);
        }
    });
    return jdata;
}

function syncJsonOptionInfo() {
    for (var i = 0; i < selectBiRiColumns.length; i++) {
        var column = selectBiRiColumns[i];
        var columnName = column.name;
        var tColumnName = column.targetColumn;

        var jdata = getJson(rootPath + column.filename);
        var bipowerObj = column.jsonHandleFn(jdata, column);
        var col_options = bipowerObj[columnName + '_options'];

        selectOptions[columnName + '_options'] = col_options;
        selectOptions[columnName] = optionsStringify(col_options);
        selectOptions[columnName + '_func'] = getFunc(columnName);
        selectOptions[tColumnName + '_tableMap'] = bipowerObj[tColumnName + '_tableMap'];
    }
}

function getBipowerObj(data, columnInfo) {
    var result = {};
    var optionMap = new Map();
    var minsMap = new Map();

    data.forEach(function (item) {
        var powerString = item["Power (W)"];
        optionMap.set(powerString, powerString);

        var category = item["Category"] === "Run in" ? "RI" : "BI";
        Object.keys(item).forEach(prop => {
            var p = prop;
            var v = item[prop];

            if (p !== "Category" && p !== "Power (W)") {
                var pNo = p.match(/\d+/);
                var mins = v.match(/\d+(\.\d+)?/);

                var key = pNo + connPattern + category + connPattern + powerString;
                var value = mins[0];
                minsMap.set(key, value);
            }
        });
    });

    var columnName = columnInfo.name;
    var tColumnName = columnInfo.targetColumn;

    result[columnName + '_options'] = optionMap;
    result[tColumnName + '_tableMap'] = minsMap;
    return result;
}
