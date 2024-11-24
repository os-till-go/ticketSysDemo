// 示例代码：使用 jQuery 的 AJAX 调用获取订单列表
$.ajax({
    url: '/api/orders', // 后端订单列表 API 接口
    type: 'GET',
    success: function(data) {
        // 处理返回的数据，将订单信息展示在页面上
        displayOrders(data);
    },
    error: function(error) {
        console.error('Error fetching orders:', error);
    }
});
// 示例代码：使用 jQuery 的 AJAX 调用获取订单详情
$.ajax({
    url: '/api/orders/123', // 后端订单详情 API 接口，假设订单 ID 为 123
    type: 'GET',
    success: function(data) {
        // 处理返回的数据，将订单详情展示在页面上
        displayOrderDetails(data);
    },
    error: function(error) {
        console.error('Error fetching order details:', error);
    }
});