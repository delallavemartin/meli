import { describe, it, expect, vi } from 'vitest';
import apiClient from '@/services/apiClient';
import { fetchProduct, fetchProducts } from '@/services/apiClient';

vi.mock('@/services/apiClient', () => ({
  default: {
    get: vi.fn(),
  },
}));

describe('apiClient service', () => {
  it('fetchProduct debería devolver los datos del producto', async () => {
    const mockProduct = { id: '1', title: 'Producto 1' };
    apiClient.get.mockResolvedValueOnce({ data: mockProduct });

    const result = await fetchProduct('1');
    expect(apiClient.get).toHaveBeenCalledWith('/products/1');
    expect(result).toEqual(mockProduct);
  });

  it('fetchProduct debería manejar errores correctamente', async () => {
    const mockError = new Error('Error al obtener el producto');
    apiClient.get.mockRejectedValueOnce(mockError);

    await expect(fetchProduct('1')).rejects.toThrow('Error al obtener el producto');
    expect(apiClient.get).toHaveBeenCalledWith('/products/1');
  });

  it('fetchProducts debería devolver la lista de productos', async () => {
    const mockProducts = [{ id: '1', title: 'Producto 1' }, { id: '2', title: 'Producto 2' }];
    apiClient.get.mockResolvedValueOnce({ data: mockProducts });

    const result = await fetchProducts();
    expect(apiClient.get).toHaveBeenCalledWith('/products');
    expect(result).toEqual(mockProducts);
  });

  it('fetchProducts debería manejar errores correctamente', async () => {
    const mockError = new Error('Error al obtener los productos');
    apiClient.get.mockRejectedValueOnce(mockError);

    await expect(fetchProducts()).rejects.toThrow('Error al obtener los productos');
    expect(apiClient.get).toHaveBeenCalledWith('/products');
  });

  it('fetchProduct debería manejar un producto no encontrado', async () => {
    apiClient.get.mockResolvedValueOnce({ data: null });

    const result = await fetchProduct('999');
    expect(apiClient.get).toHaveBeenCalledWith('/products/999');
    expect(result).toBeNull();
  });
});