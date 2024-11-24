// 示例代码：使用 jQuery 的 AJAX 调用获取票务信息
$.ajax({
    url: '/api/tickets', // 后端 API 接口
    type: 'GET',
    success: function(data) {
        // 处理返回的数据，将票务信息展示在页面上
        displayTickets(data);
    },
    error: function(error) {
        console.error('Error fetching tickets:', error);
    }
});
// 示例代码：显示票务信息的函数
function displayTickets(tickets) {
    var ticketList = $('#ticket-list'); // 票务列表容器
    for (var i = 0; i < tickets.length; i++) {
        var ticket = tickets[i];
        var ticketItem = $('<li>');
        ticketItem.text(ticket.name + ' - ' + ticket.price);
        ticketList.append(ticketItem);
    }
}