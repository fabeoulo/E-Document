$('#enable').on('click', 'li', function () {
    $(this).toggleClass('selected');  // 切换选中样式
    $('#disable').append(this);       // 移动到右边 UL 中
});
  
$('#disable').on('click', 'li', function () {
    $(this).toggleClass('selected');  // 切换选中样式
    $('#enable').append(this);        // 移动到左边 UL 中
});

$(document).ready(function() {
    $('.tab-option').click(function() {
        // 切換選項
        $('.tab-option').removeClass('active');
        $(this).addClass('active');

        // 切換面板
        var tabId = $(this).data('tab');
        $('.tab-panel').removeClass('active');
        $('#' + tabId).addClass('active');
    });
    });