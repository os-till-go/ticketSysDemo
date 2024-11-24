import { API_BASE_URL } from '../config'
import { Seat, ApiResponse } from '../types'

export const seatApi = {
    getSeatsByTicket: (ticketId: number): Promise<ApiResponse<Seat[]>> =>
        fetch(`${API_BASE_URL}/seats/${ticketId}`)
            .then(res => res.json()),

    selectSeat: (seatId: number): Promise<ApiResponse<boolean>> =>
        fetch(`${API_BASE_URL}/seats/select/${seatId}`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' }
        }).then(res => res.json()),

    releaseSeat: (seatId: number): Promise<ApiResponse<boolean>> =>
        fetch(`${API_BASE_URL}/seats/release/${seatId}`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' }
        }).then(res => res.json())
}