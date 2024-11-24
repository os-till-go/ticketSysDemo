export interface User {
    id: number
    name: string
    email: string
    password: string
    role: 'user' | 'admin'
}
export interface Ticket {
    id: number
    route: string
    price: number
    departureTime: string
    seatCount: number
    available: boolean
}

export interface Seat {
    id: number
    number: string
    status: 'available' | 'selected' | 'occupied'
    ticketId: number
}

export interface Order {
    id: number
    orderNumber: string
    userId: number
    ticketId: number
    seatIds: number[]
    status: 'pending' | 'paid' | 'cancelled'
    totalPrice: number
    createTime: string
}

export interface ApiResponse<T> {
    success: boolean
    data: T
    message?: string
}