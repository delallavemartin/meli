// src/router/index.js
import { createRouter, createWebHistory } from 'vue-router'
import ProductDetailPage from '@/views/ProductDetailPage.vue'
import MercadoLibreResultsPage from '@/views/MercadoLibreResultsPage.vue'

const routes = [
  {
    path: '/item/:id', // Ruta dinámica para el ID del producto
    name: 'ProductDetail',
    component: ProductDetailPage,
    props: true // Pasa los params de la ruta como props al componente
  },
   {
    path: '/items',
    name: 'MercadoLibreResults',
    component: MercadoLibreResultsPage,
    props: true
  },
  {
    path: '/',
    redirect: '/items' // Redirige a un producto de ejemplo
  }
]

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes
})

export default router