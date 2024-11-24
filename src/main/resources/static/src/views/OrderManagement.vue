<template>
  <div class="order-management">
    <h2>订单管理</h2>
    <div class="order-list">
      <div v-for="order in orders" :key="order.id" class="order-item">
        <h3>订单号: {{ order.orderNumber }}</h3>
        <p>总价: ¥{{ order.totalPrice }}</p>
        <p>状态: {{ order.status }}</p>
        <div class="order-actions">
          <button @click="viewOrderDetail(order)">查看详情</button>
          <button
              v-if="order.status === 'pending'"
              @click="payOrder(order)"
          >
            支付
          </button>
          <button
              v-if="order.status === 'pending'"
              @click="cancelOrder(order)"
          >
            取消订单
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      orders: []
    }
  },
  methods: {
    async fetchOrders() {
      try {
        const response = await fetch('/api/orders');
        this.orders = await response.json();
      } catch (error) {
        console.error('获取订单列表失败:', error);
      }
    },
    async payOrder(order) {
      try {
        const response = await fetch(`/api/orders/${order.id}/pay`, {
          method: 'POST'
        });
        if (response.ok) {
          order.status = 'paid';
          this.$message.success('支付成功');
        }
      } catch (error) {
        console.error('支付失败:', error);
      }
    },
    async cancelOrder(order) {
      try {
        const response = await fetch(`/api/orders/${order.id}/cancel`, {
          method: 'POST'
        });
        if (response.ok) {
          order.status = 'cancelled';
          this.$message.success('订单已取消');
        }
      } catch (error) {
        console.error('取消订单失败:', error);
      }
    },
    viewOrderDetail(order) {
      this.$router.push(`/orders/${order.id}`);
    }
  },
  mounted() {
    this.fetchOrders();
  }
}
</script>