import { API_BASE_URL } from '../config'
import { User, ApiResponse } from '../types'

export const userApi = {
    login: (email: string, password: string): Promise<ApiResponse<{ token: string; user: User }>> =>
        fetch(`${API_BASE_URL}/auth/login`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ email, password })
        }).then(res => res.json()),

    register: (userData: { email: string; password: string; name: string }): Promise<ApiResponse<User>> =>
        fetch(`${API_BASE_URL}/auth/register`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(userData)
        }).then(res => res.json()),

    getCurrentUser: (): Promise<ApiResponse<User>> =>
        fetch(`${API_BASE_URL}/users/current`, {
            headers: {
                'Authorization': `Bearer ${localStorage.getItem('token')}`
            }
        }).then(res => res.json()),

    updateProfile: (userData: Partial<User>): Promise<ApiResponse<User>> =>
        fetch(`${API_BASE_URL}/users/profile`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${localStorage.getItem('token')}`
            },
            body: JSON.stringify(userData)
        }).then(res => res.json())
}