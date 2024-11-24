import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router'
import { API_BASE_URL } from '../config'

interface Ticket {
    id: number
    route: string
    price: number
    departureTime: string
    seatCount: number
    available: boolean
}

interface ApiResponse<T> {
    success: boolean
    data: T
    message?: string
}

export const ticketApi = {
    getTickets: (): Promise<ApiResponse<Ticket[]>> =>
        fetch(`${API_BASE_URL}/tickets/available`)
            .then(res => res.json()),

    getTicketById: (id: number): Promise<ApiResponse<Ticket>> =>
        fetch(`${API_BASE_URL}/tickets/${id}`)
            .then(res => res.json()),

    purchaseTicket: (userId: number, ticketId: number) =>
        fetch(`${API_BASE_URL}/tickets/purchase`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ userId, ticketId })
        }).then(res => res.json())
}

// ... 其他 API 定义
const routes: Array<RouteRecordRaw> = [
    {
        path: '/',
        redirect: '/login'
    },
    {
        path: '/login',
        name: 'Login',
        component: () => import('../views/Login.vue')
    },
    {
        path: '/dashboard',
        name: 'Dashboard',
        component: () => import('../views/Dashboard.vue'),
        meta: { requiresAuth: true }
    },
    {
        path: '/tickets',
        name: 'TicketList',
        component: () => import('../views/TicketList.vue'),
        meta: { requiresAuth: true }
    },
    {
        path: '/tickets/:id',
        name: 'TicketDetail',
        component: () => import('../views/TicketDetail.vue'),
        meta: { requiresAuth: true }
    },
    {
        path: '/seats/:ticketId',
        name: 'SeatSelection',
        component: () => import('../views/SeatSelection.vue'),
        meta: { requiresAuth: true }
    },
    {
        path: '/orders',
        name: 'OrderManagement',
        component: () => import('../views/OrderManagement.vue'),
        meta: { requiresAuth: true }
    }
]

const router = createRouter({
    history: createWebHistory(),
    routes
})

router.beforeEach((to, from, next) => {
    if (to.meta.requiresAuth && !localStorage.getItem('token')) {
        next('/login')
    } else {
        next()
    }
})

export default router

export const API_BASE_URL = process.env.VUE_APP_API_BASE_URL || '/api'

// Extract API_BASE_URL into a configuration file
const API_BASE_URL = process.env.VUE_APP_API_BASE_URL || '/api';

export const ticketApi = {
    getTickets: () => fetch(`${API_BASE_URL}/tickets/available`).then(res => res.json()),
    getTicketById: (id) => fetch(`${API_BASE_URL}/tickets/${id}`).then(res => res.json()),
    purchaseTicket: (userId, ticketId) =>
        fetch(`${API_BASE_URL}/tickets/purchase`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ userId, ticketId })
        }).then(res => res.json())
};

export const seatApi = {
    getSeatsByTicket: (ticketId) =>
        fetch(`${API_BASE_URL}/seats/${ticketId}`).then(res => res.json()),
    selectSeat: (seatId) =>
        fetch(`${API_BASE_URL}/seats/select/${seatId}`, {
            method: 'POST'
        }).then(res => res.json())
};

export const orderApi = {
    getOrders: () => fetch(`${API_BASE_URL}/orders`).then(res => res.json()),
    getOrderById: (id) => fetch(`${API_BASE_URL}/orders/${id}`).then(res => res.json()),
    createOrder: (userId, ticketId) =>
        fetch(`${API_BASE_URL}/orders/create`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ userId, ticketId })
        }).then(res => res.json())
};
