import apiClient from '@/services/apiClient.js';

export async function fetchProduct(productId) {
  const response = await apiClient.get(`/products/${productId}`);
  return response.data;
}

export async function fetchProducts() {
  const response = await apiClient.get('/products');
  return response.data;
}