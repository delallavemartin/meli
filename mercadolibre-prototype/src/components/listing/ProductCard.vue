<template>
  <div
    class="bg-white border border-gray-200 rounded-lg shadow-sm overflow-hidden flex flex-col h-full cursor-pointer"
    @click="navigateToProductDetail"
  >
    <div class="relative">
      <img :src="product.images[0]" :alt="product.title" class="w-full h-48 object-contain p-4 bg-gray-50" />
      <span v-if="product.isNew" class="absolute top-2 left-2 bg-blue-500 text-white text-xs font-semibold px-2 py-1 rounded">NUEVO</span>
      <span v-if="product.discount" class="absolute top-2 right-2 bg-green-500 text-white text-xs font-bold px-2 py-1 rounded">{{ product.discount }}% OFF</span>
    </div>

    <div class="p-4 flex flex-col flex-grow">
      <h3 class="text-sm text-gray-600 mb-2 h-10 overflow-hidden">
        {{ product.title }}
      </h3>

      <div class="mt-auto">
        <p v-if="product.price.amount" class="text-xs text-gray-400">
          {{ formatCurrency(product.price.amount) }}
        </p>

        <p v-if="product.installments" class="text-sm text-gray-500">
          en <span class="text-green-600">{{ product.installments }}</span>
        </p>

        <p v-if="product.seller.name" class="text-xs text-gray-400 mt-1">
          Vendido por <span class="text-blue-500">{{ product.seller.name }}</span>
        </p>
      </div>
    </div>
  </div>
</template>

<script setup>
import { defineProps } from 'vue';
import { useRouter } from 'vue-router';

const props = defineProps({
  product: {
    type: Object,
    required: true,
  },
});

const router = useRouter();

const navigateToProductDetail = () => {
  router.push({ name: 'ProductDetail', params: { id: props.product.id } });
};

const formatCurrency = (value) => {
  if (typeof value !== 'number') return value;
  return value.toLocaleString('es-AR', { style: 'currency', currency: 'ARS', minimumFractionDigits: 0, maximumFractionDigits: 0 });
};
</script>

<style scoped>
/* Puedes añadir estilos adicionales aquí si Tailwind no es suficiente */
.h-10 { /* Para limitar la altura del título y permitir elipsis si es necesario */
  max-height: 2.5rem; /* Ajusta según el line-height */
}
</style>