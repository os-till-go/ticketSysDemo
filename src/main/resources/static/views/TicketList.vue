<template>
  <div class="ticket-list">
    <div class="search-filters">
      <input v-model="searchRoute" placeholder="搜索线路" @input="handleSearch">
      <div class="date-filter">
        <input type="datetime-local" v-model="startTime">
        <input type="datetime-local" v-model="endTime">
        <button @click="handleDateFilter">筛选时间</button>
      </div>
    </div>

    <div class="ticket-grid">
      <div v-for="ticket in filteredTickets" :key="ticket.id" class="ticket-item">
        <h3>{{ ticket.route }}</h3>
        <p>价格: ¥{{ ticket.price }}</p>
        <p>出发时间: {{ formatDateTime(ticket.departureTime) }}</p>
        <p>剩余座位: {{ ticket.seatCount }}</p>
        <button @click="selectTicket(ticket)" :disabled="!ticket.available">
          选择座位
        </button>
      </div>
    </div>
  </div>
</template>

<script>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ticketApi } from '@/api'

export default {
  setup() {
    const tickets = ref([])
    const searchRoute = ref('')
    const startTime = ref('')
    const endTime = ref('')
    const router = useRouter()

    const fetchTickets = async () => {
      try {
        tickets.value = await ticketApi.getTickets()
      } catch (error) {
        console.error('获取票务失败:', error)
      }
    }

    const handleSearch = async () => {
      if (searchRoute.value) {
        tickets.value = await ticketApi.searchByRoute(searchRoute.value)
      } else {
        await fetchTickets()
      }
    }

    const handleDateFilter = async () => {
      if (startTime.value && endTime.value) {
        tickets.value = await ticketApi.searchByTime(startTime.value, endTime.value)
      }
    }

    const selectTicket = (ticket) => {
      router.push(`/seats/${ticket.id}`)
    }

    onMounted(fetchTickets)

    return {
      tickets,
      searchRoute,
      startTime,
      endTime,
      handleSearch,
      handleDateFilter,
      selectTicket
    }
  }
}
</script>