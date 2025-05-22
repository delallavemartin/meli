<template>
  <div>
    <ProductDetail v-if="route.params.id" :productId="route.params.id" />
    <div v-else class="text-center p-10">
      Cargando producto...
    </div>
  </div>
</template>

<script setup>
import { useRoute } from 'vue-router'
import ProductDetail from '@/components/product/ProductDetail.vue'

const route = useRoute()
</script>