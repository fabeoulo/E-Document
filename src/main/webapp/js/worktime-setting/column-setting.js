//Js檔用來給予下拉式表單使用(unit可編輯欄位需要區隔，記錄在db之中)
var worktimeCol = [
    {name: "id", editable: false},
    {name: "modelName"},
    {name: "type.id"},
    {name: "productionWt"},
    {name: "totalModule"},
    {name: "setupTime"},
    {name: "cleanPanel"},
    {name: "preAssyModuleQty"},
    {name: "assy"},
    {name: "t1"},
    {name: "t2"},
    {name: "t3"},
    {name: "t4"},
    {name: "packing"},
    {name: "upBiRi"},
    {name: "downBiRi"},
    {name: "biCost"},
    {name: "vibration"},
    {name: "hiPotLeakage"},
    {name: "coldBoot"},
    {name: "warmBoot"},
    {name: "assyToT1"},
    {name: "t2ToPacking"},
    {name: "floor.id"},
    {name: "pending.id"},
    {name: "pendingTime"},
    {name: "burnIn"},
    {name: "biSampling"},
    {name: "biTime"},
    {name: "biTemperature"},
    {name: "userBySpeOwnerId.id"},
    {name: "userByEeOwnerId.id"},
    {name: "userByQcOwnerId.id"},
    {name: "userByMpmOwnerId.id"},
    {name: "assyPackingSop"},
    {name: "testSop"},
    {name: "keypartA"},
    {name: "keypartB"},
    {name: "preAssy.id"},
    {name: "flowByBabFlowId.id"},
    {name: "flowByTestFlowId.id"},
    {name: "flowByPackingFlowId.id"},
    {name: "partLink"},
    {name: "ce"},
    {name: "ul"},
    {name: "rohs"},
    {name: "weee"},
    {name: "madeInTaiwan"},
    {name: "fcc"},
    {name: "eac"},
    {name: "kc"},
    {name: "nsInOneCollectionBox"},
    {name: "partNoAttributeMaintain"},
    {name: "acwVoltage"},
    {name: "irVoltage"},
    {name: "testProfile"},
    {name: "lltValue"},
    {name: "gndValue"},
    {name: "weight"},
    {name: "weightAff"},
    {name: "tolerance"},
    {name: "assyStation"},
    {name: "packingStation"},
    {name: "assyLeadTime"},
    {name: "assyKanbanTime"},
    {name: "packingLeadTime"},
    {name: "packingKanbanTime"},
    {name: "cleanPanelAndAssembly"},
    {name: "modifiedDate", editable: false},
    {name: "bwFields.0.assyAvg", editable: false},
    {name: "bwFields.0.packingAvg", editable: false},
    {name: "businessGroup.id"},
    {name: "workCenter"},
    {name: "burnInQuantity"},
    {name: "sapWt"},
    {name: "macTotalQty"},
    {name: "macPrintedQty"},
    {name: "twm2Flag"},
    {name: "hrcValues"},
    {name: "t1StatusQty"},
    {name: "t1ItemsQty"},
    {name: "t2StatusQty"},
    {name: "t2ItemsQty"},
    {name: "machineWorktime"},
    {name: "cobots"},
    {name:"labelMac"},
    {name:"macPrintedLocation"},
    {name:"macPrintedFrom"},
    {name:"etlVariable1"},
    {name:"etlVariable2"},
    {name:"etlVariable3"},
    {name:"etlVariable1Aff"},
    {name:"etlVariable2Aff"},
    {name:"etlVariable3Aff"},
    {name:"labelYN"},
    {name:"labelOuterId.id"},
    {name:"labelCartonId.id"},
    {name:"labelOuterCustom"},
    {name:"labelCartonCustom"},
    {name:"labelBigCarton"},
    {name:"label2D"},
    {name:"labelCustomerSn"},
    {name:"labelSn"},
    {name:"labelPn"},
    {name:"labelNmodelA"},
    {name:"labelNmodelB"},
    {name:"labelVariable1"},
    {name:"labelVariable2"},
    {name:"labelVariable3"},
    {name:"labelVariable4"},
    {name:"labelVariable5"},
    {name:"labelVariable6"},
    {name:"labelVariable7"},
    {name:"labelVariable8"},
    {name:"labelVariable9"},
    {name:"labelVariable10"},
    {name:"labelVariable1Aff"},
    {name:"labelVariable2Aff"},
    {name:"labelVariable3Aff"},
    {name:"labelVariable4Aff"},
    {name:"labelVariable5Aff"},
    {name:"labelVariable6Aff"},
    {name:"labelVariable7Aff"},
    {name:"labelVariable8Aff"},
    {name:"labelVariable9Aff"},
    {name:"labelVariable10Aff"},
    {name:"labelPacking1"},
    {name:"labelPacking2"},
    {name:"labelPacking3"},
    {name:"labelPacking4"},
    {name:"labelPacking5"},
    {name:"labelPacking6"},
    {name:"labelPacking7"},
    {name:"labelPacking8"},
    {name:"labelPacking9"},
    {name:"labelPacking10"}     
];

//不受show / hide 影響
var do_not_change_columns = [
    "id", "rowId", "modifiedDate", "bwFields.0.assyAvg", "bwFields.0.packingAvg", "createDate"
];

//指定的column要有checkbox
var formulaColumn = [
    "productionWt",
    "setupTime",
//    "assyToT1",
//    "t2ToPacking",
    "assyStation",
    "packingStation",
    "assyKanbanTime",
    "packingKanbanTime",
    "cleanPanelAndAssembly",
    "machineWorktime"
];