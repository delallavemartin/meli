import { describe, it, expect } from 'vitest';
import router from '@/router';

describe('Router', () => {
  it('debería tener la ruta para ProductDetailPage', () => {
    const route = router.getRoutes().find(r => r.name === 'ProductDetail');
    expect(route).toBeDefined();
    expect(route.path).toBe('/item/:id');
  });

  it('debería tener la ruta para ProductDetailPage', () => {
    const route = router.getRoutes().find(r => r.name === 'ProductDetail');
    expect(route).toBeDefined();
    expect(route.path).toBe('/item/:id');
  });

  it('debería redirigir la ruta raíz a /items', async () => {
    const route = router.getRoutes().find(r => r.path === '/');
    expect(route).toBeDefined();
    expect(route.redirect).toBe('/items'); // Verifica la redirección
  });
});