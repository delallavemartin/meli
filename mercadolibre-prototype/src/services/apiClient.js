import axios from 'axios';
import { API_BASE_URL } from '@/params/params.js';

const apiClient = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

// Interceptor para manejar errores
apiClient.interceptors.response.use(
  (response) => response,
  (error) => {
    const errorMessage = error.response?.data?.message || `Error: ${error.response?.status || 'Unknown'}`;
    return Promise.reject(new Error(errorMessage));
  }
);

export default apiClient;