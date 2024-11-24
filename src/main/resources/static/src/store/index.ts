import { reactive } from 'vue'
import { Ticket, Seat, Order } from '../types'
import {ticketApi} from "../api/ticket";
import {seatApi} from "../api/seat";
import {orderApi} from "../api/order";
import {userApi} from "../api/user";

export const store = reactive({
    tickets: [] as Ticket[],
    selectedTicket: null as Ticket | null,
    selectedSeats: [] as Seat[],
    orders: [] as Order[],

    async fetchTickets() {
        try {
            const response = await ticketApi.getTickets()
            if (response.success) {
                this.tickets = response.data
            }
        } catch (error) {
            console.error('获取票务失败:', error)
        }
    },

    async selectTicket(ticket: Ticket) {
        this.selectedTicket = ticket
        this.selectedSeats = []
        try {
            const response = await seatApi.getSeatsByTicket(ticket.id)
            if (response.success) {
                return response.data
            }
        } catch (error) {
            console.error('获取座位信息失败:', error)
        }
    }
})