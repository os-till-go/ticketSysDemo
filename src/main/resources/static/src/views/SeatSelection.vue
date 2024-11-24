<template>
  <div class="seat-selection">
    <h2>选择座位</h2>
    <div class="ticket-info">
      <h3>{{ ticket?.route }}</h3>
      <p>出发时间: {{ formatDateTime(ticket?.departureTime) }}</p>
    </div>

    <div class="seat-grid">
      <div
          v-for="seat in seats"
          :key="seat.id"
          :class="['seat', seat.status, { 'selected': isSelected(seat) }]"
          @click="handleSeatClick(seat)"
      >
        {{ seat.number }}
      </div>
    </div>

    <div class="selected-seats">
      <h3>已选座位</h3>
      <ul>
        <li v-for="seat in selectedSeats" :key="seat.id">
          {{ seat.number }}
          <button @click="cancelSeat(seat)">取消</button>
        </li>
      </ul>
      <button
          class="confirm-btn"
          @click="confirmSelection"
          :disabled="!selectedSeats.length"
      >
        确认选座
      </button>
    </div>
  </div>
</template>

<script>
import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { ticketApi, seatApi } from '@/api'

export default {
  setup() {
    const route = useRoute()
    const router = useRouter()
    const ticket = ref(null)
    const seats = ref([])
    const selectedSeats = ref([])

    const fetchTicketAndSeats = async () => {
      try {
        ticket.value = await ticketApi.getTicketById(route.params.ticketId)
        seats.value = await seatApi.getSeatsByTicket(route.params.ticketId)
      } catch (error) {
        console.error('获取数据失败:', error)
      }
    }

    const handleSeatClick = async (seat) => {
      if (seat.status === 'occupied') return

      try {
        const result = await seatApi.selectSeat(seat.id)
        if (result.success) {
          selectedSeats.value.push(seat)
          seat.status = 'selected'
        }
      } catch (error) {
        console.error('选择座位失败:', error)
      }
    }

    const confirmSelection = async () => {
      try {
        const result = await ticketApi.purchaseTicket(
            localStorage.getItem('userId'),
            ticket.value.id,
            selectedSeats.value.map(seat => seat.id)
        )
        if (result.success) {
          router.push(`/orders/${result.orderId}`)
        }
      } catch (error) {
        console.error('购票失败:', error)
      }
    }

    onMounted(fetchTicketAndSeats)

    return {
      ticket,
      seats,
      selectedSeats,
      handleSeatClick,
      confirmSelection
    }
  }
}
</script>