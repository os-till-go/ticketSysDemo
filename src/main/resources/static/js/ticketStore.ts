import { reactive } from 'vue';
import { ticketApi, seatApi, orderApi } from '../api';

export const ticketStore = reactive({
    tickets: [],
    selectedTicket: null,
    selectedSeats: [],

    async fetchTickets() {
        try {
            this.tickets = await ticketApi.getTickets();
        } catch (error) {
            console.error('获取票务失败:', error);
        }
    },

    async selectTicket(ticket) {
        this.selectedTicket = ticket;
        try {
            const seats = await seatApi.getSeatsByTicket(ticket.id);
            this.seats = seats;
        } catch (error) {
            console.error('获取座位信息失败:', error);
        }
    },

    async selectSeat(seat) {
        try {
            const result = await seatApi.selectSeat(seat.id);
            if (result.success) {
                this.selectedSeats.push(seat);
            }
        } catch (error) {
            console.error('选择座位失败:', error);
        }
    }
});