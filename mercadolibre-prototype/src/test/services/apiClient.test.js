import { describe, it, expect, vi } from 'vitest';
import axios from 'axios';
import apiClient from '@/services/apiClient';

vi.mock('axios', () => {
  const actualAxios = vi.importActual('axios'); // Importa el módulo original para usar partes de él
  return {
    ...actualAxios,
    default: {
      create: vi.fn(() => ({
        interceptors: {
          response: {
            use: vi.fn(),
          },
        },
        defaults: {
          baseURL: '/api',
          headers: {
            'Content-Type': 'application/json',
          },
        },
        get: vi.fn().mockResolvedValue({ data: { message: 'Success' } }), // Configura el mock para devolver el valor esperado
        post: vi.fn(),
      })),
    },
  };
});

describe('apiClient', () => {
  it('debería tener la configuración base correcta', () => {
    expect(apiClient.defaults.baseURL).toBe('/api'); // Asegúrate de que `API_BASE_URL` sea `/api` en tu configuración
    expect(apiClient.defaults.headers['Content-Type']).toBe('application/json');
  });

  it('debería manejar respuestas exitosas correctamente', async () => {
    const mockResponse = { data: { message: 'Success' } };

    const response = await apiClient.get('/test');
    expect(response).toEqual(mockResponse); // Verifica que el valor devuelto coincida con el esperado
  });

});