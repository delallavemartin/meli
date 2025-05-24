import { defineConfig } from 'vite'
import tailwindcss from '@tailwindcss/vite'
import vue from '@vitejs/plugin-vue'
import path from 'path'

export default defineConfig({
  plugins: [vue(),tailwindcss()],
  resolve: {
    alias: {
      '@': path.resolve(__dirname, './src'),
    },
  },
  test:{
    globals: true,
    environment: 'jsdom',
    setupFiles: './src/test/setup.js',
     coverage: {
      exclude: [
        '**/*.test.js', // Excluir archivos de prueba
        '**/*.spec.js', // Excluir archivos de especificación
        'node_modules/**', // Excluir dependencias
        'dist/**', // Excluir archivos generados
        'vite.config.js', // Excluir configuración de Vite
      ],
    },
  },
})
