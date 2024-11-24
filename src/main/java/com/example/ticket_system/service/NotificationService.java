package com.example.ticket_system.service;

import org.springframework.stereotype.Service;
import com.example.ticket_system.model.Order;
@Service
public class NotificationService {
    public void notifyPurchaseResult(Order order, boolean success) {
        if (success) {
            System.out.println("向用户发送购票成功通知：订单号 " + order.getId() + " 已成功购买票务");
            // 实际在前端交互中，可返回相应成功消息给前端展示弹窗
        } else {
            System.out.println("向用户发送购票失败通知：订单号 " + order.getId() + " 购票失败");
            // 同样返回失败消息给前端展示错误弹窗
        }
    }
}
