import { API_BASE_URL } from '@/params/params.js';

export async function fetchProduct(productId) {
  const response = await fetch(`${API_BASE_URL}/products/${productId}`);
  if (!response.ok) {
    throw new Error('Error al cargar el producto. Código: ' + response.status);
  }
  return response.json();
}

export async function fetchProducts() {
  const response = await fetch(`${API_BASE_URL}/products`);
  if (!response.ok) {
    throw new Error('Error al cargar los productos. Código: ' + response.status);
  }
  return response.json();
}