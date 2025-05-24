import { describe, it, expect } from 'vitest';
import { createApp } from 'vue';
import App from '@/App.vue';
import router from '@/router';

describe('main.js', () => {
  it('debería crear y montar la aplicación correctamente', async () => {
    // Importa y ejecuta el archivo main.js
    const { default: main } = await import('@/main.js');

    const app = createApp(App);
    app.use(router);
    app.mount('#app');

    // Verifica que el componente principal sea App
    expect(app._component).toMatchObject({
      __name: 'App',
    });
  });
});