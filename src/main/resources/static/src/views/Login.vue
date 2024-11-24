<template>
  <div class="login-container">
    <form @submit.prevent="handleSubmit">
      <div class="form-group">
        <input
            type="email"
            v-model="email"
            @blur="validateEmail"
            placeholder="邮箱"
        >
        <span v-if="emailError" class="error">{{ emailError }}</span>
      </div>
      <div class="form-group">
        <input
            type="password"
            v-model="password"
            placeholder="密码"
        >
      </div>
      <button type="submit">登录</button>
    </form>
  </div>
</template>

<script lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'

export default {
  setup() {
    const router = useRouter()
    const email = ref('')
    const password = ref('')
    const emailError = ref('')

    const validateEmail = () => {
      const re = /^[^\s@]+@[^\s@]+\.[^\s@]+$/
      if (!re.test(email.value)) {
        emailError.value = '请输入正确的邮箱格式'
      } else {
        emailError.value = ''
      }
    }

    const handleSubmit = async () => {
      try {
        const response = await fetch('/api/login', {
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify({
            email: email.value,
            password: password.value
          })
        })
        const data = await response.json()

        if (data.success) {
          localStorage.setItem('token', data.token)
          router.push('/dashboard')
        } else {
          alert(data.message || '登录失败')
        }
      } catch (error) {
        console.error('登录失败:', error)
      }
    }

    return {
      email,
      password,
      emailError,
      validateEmail,
      handleSubmit
    }
  }
}
</script>