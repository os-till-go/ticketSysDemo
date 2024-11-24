import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router'

const routes: Array<RouteRecordRaw> = [
    {
        path: '/',
        redirect: '/login'
    },
    {
        path: '/login',
        name: 'Login',
        component: () => import('../../views/Login.vue')
    },
    {
        path: '/dashboard',
        name: 'Dashboard',
        component: () => import('../../views/Dashboard.vue'),
        meta: { requiresAuth: true }
    },
    {
        path: '/tickets',
        name: 'TicketList',
        component: () => import('../../views/TicketList.vue'),
        meta: { requiresAuth: true }
    },
    {
        path: '/tickets/:id',
        name: 'TicketDetail',
        component: () => import('../../views/TicketDetail.vue'),
        meta: { requiresAuth: true }
    },
    {
        path: '/seats/:ticketId',
        name: 'SeatSelection',
        component: () => import('../../views/SeatSelection.vue'),
        meta: { requiresAuth: true }
    },
    {
        path: '/orders',
        name: 'OrderManagement',
        component: () => import('../../views/OrderManagement.vue'),
        meta: { requiresAuth: true }
    }
]

const router = createRouter({
    history: createWebHistory(),
    routes
})