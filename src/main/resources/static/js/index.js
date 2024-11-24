import Vue from 'vue'
import VueRouter from 'vue-router'

const routes = [
    {
        path: '/tickets',
        component: () => import('@/components/TicketList.vue')
    },
    {
        path: '/seats/:ticketId',
        component: () => import('@/components/SeatSelection.vue')
    },
    {
        path: '/orders',
        component: () => import('@/components/OrderManagement.vue')
    },
    {
        path: '/orders/:id',
        component: () => import('@/components/OrderDetail.vue')
    }
]

export default new VueRouter({
    routes
})