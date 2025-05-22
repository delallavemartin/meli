// src/router/index.js
import { createRouter, createWebHistory } from 'vue-router'
import ProductDetailPage from '@/views/ProductDetailPage.vue'

const routes = [
  {
    path: '/item/:id', // Ruta dinámica para el ID del producto
    name: 'ProductDetail',
    component: ProductDetailPage,
    props: true // Pasa los params de la ruta como props al componente
  },
  // Puedes añadir una ruta raíz o para listado de productos
  {
    path: '/',
    redirect: '/item/MLA12345678' // Redirige a un producto de ejemplo
  }
]

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes
})

export default router