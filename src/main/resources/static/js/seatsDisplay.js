// 示例代码：使用 jQuery 实现座位选择功能
$('.seat').click(function() {
    var seatId = $(this).data('seat-id');
    // 调用后端 API 选择座位
    $.ajax({
        url: '/api/seats/select/' + seatId,
        type: 'POST',
        success: function(data) {
            // 更新座位状态显示
            updateSeatStatus(seatId, data.status);
        },
        error: function(error) {
            console.error('Error selecting seat:', error);
        }
    });
});
// 示例代码：更新座位状态显示
function updateSeatStatus(seatId, status) {
    var seatElement = $('.seat[data-seat-id="' + seatId + '"]');
    if (status === 'selected') {
        seatElement.addClass('selected');
    } else if (status === 'available') {
        seatElement.removeClass('selected');
    }
}