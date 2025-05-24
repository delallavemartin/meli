<template>
  <div class="bg-gray-100 min-h-screen">
    <div class="container mx-auto p-4">
      <div class="flex flex-col md:flex-row gap-6">
        <main class="w-full md:w-3/4 lg:w-4/5">
          <div v-if="error" class="text-red-500 text-center">
            {{ error }}
          </div>
          <div v-else-if="products && products.length === 0" class="text-gray-500 text-center">
            No hay productos disponibles.
          </div>
          <div v-else class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 gap-4">
            <ProductCard v-for="product in products" :key="product.id" :product="product" />
          </div>
        </main>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import ProductCard from '@/components/listing/ProductCard.vue';
import { fetchProducts } from '@/services/productService.js';

const products = ref(null)
const error = ref(null)

onMounted(async () => {
  try {
    products.value = await fetchProducts();
  } catch (err) {
    console.error("Error fetching products:", err);
    error.value = err.message || 'Ocurrió un error desconocido al obtener los productos.'
  }
})
</script>