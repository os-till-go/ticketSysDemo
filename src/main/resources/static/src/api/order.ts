import { API_BASE_URL } from '../config'
import { Order, ApiResponse } from '../types'

export const orderApi = {
    getOrders: (): Promise<ApiResponse<Order[]>> =>
        fetch(`${API_BASE_URL}/orders`)
            .then(res => res.json()),

    getOrderById: (id: number): Promise<ApiResponse<Order>> =>
        fetch(`${API_BASE_URL}/orders/${id}`)
            .then(res => res.json()),

    createOrder: (data: { userId: number; ticketId: number; seatIds: number[] }): Promise<ApiResponse<Order>> =>
        fetch(`${API_BASE_URL}/orders/create`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(data)
        }).then(res => res.json()),

    cancelOrder: (orderId: number): Promise<ApiResponse<void>> =>
        fetch(`${API_BASE_URL}/orders/${orderId}/cancel`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' }
        }).then(res => res.json()),

    payOrder: (orderId: number): Promise<ApiResponse<Order>> =>
        fetch(`${API_BASE_URL}/orders/${orderId}/pay`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' }
        }).then(res => res.json())
}