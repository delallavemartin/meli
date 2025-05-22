// src/main.js
import { createApp } from 'vue'
import App from './App.vue'
import router from './router' // Lo crearemos en el siguiente paso
import './assets/style.css' // Importa tu archivo CSS de Tailwind

const app = createApp(App)
app.use(router)
app.mount('#app')