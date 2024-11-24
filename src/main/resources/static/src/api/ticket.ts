import { API_BASE_URL } from '../config'
import { Ticket, ApiResponse } from '../types'

export const ticketApi = {
    getTickets: (): Promise<ApiResponse<Ticket[]>> =>
        fetch(`${API_BASE_URL}/tickets/available`)
            .then(res => res.json()),

    getTicketById: (id: number): Promise<ApiResponse<Ticket>> =>
        fetch(`${API_BASE_URL}/tickets/${id}`)
            .then(res => res.json()),

    searchByRoute: (keyword: string): Promise<ApiResponse<Ticket[]>> =>
        fetch(`${API_BASE_URL}/tickets/search/route?keyword=${keyword}`)
            .then(res => res.json()),

    searchByTime: (startTime: string, endTime: string): Promise<ApiResponse<Ticket[]>> =>
        fetch(`${API_BASE_URL}/tickets/search/departure-time?startTime=${startTime}&endTime=${endTime}`)
            .then(res => res.json()),

    purchaseTicket: (userId: number, ticketId: number, seatIds: number[]) =>
        fetch(`${API_BASE_URL}/tickets/purchase`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ userId, ticketId, seatIds })
        }).then(res => res.json())
}