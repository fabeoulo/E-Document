var ltrimReg = /^\s+/;
var rtrimReg = /\s+$/;
var ltrimAndRtrim = function (str) {
    return str.replace(ltrimReg, "").replace(rtrimReg, "");
};
var upperCase_event = [
    {
        type: 'input',
        fn: function (el) {
            var textbox = $(this);
            textbox.val(ltrimAndRtrim(textbox.val()).toUpperCase());
        }
    }
];

var autoUpperCase = function (el) {
    $(el).css('text-transform', 'uppercase');
};

var trimText_event = [
    {
        type: 'blur',
        fn: function (e) {
            $(this).val($.trim($(this).val()));
        }
    }
];
