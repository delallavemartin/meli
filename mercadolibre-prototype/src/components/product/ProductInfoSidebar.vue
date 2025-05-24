<template>
  <div v-if="productData" class="border border-gray-200 rounded-lg p-6 sticky top-6">
    <p class="text-xs text-ml-light-gray mb-1">
      {{ productData.condition || 'Condición no especificada' }} | {{ productData.reviews?.count || 0 }} vendidos
    </p>
    <h1 class="text-2xl font-semibold mb-2 text-ml-dark-gray">
      {{ productData.title || 'Título no disponible' }}
    </h1>

    <div v-if="productData.reviews" class="flex items-center mb-4">
      <span
        v-for="n in 5"
        :key="n"
        class="text-ml-yellow"
      >
        <svg
          xmlns="http://www.w3.org/2000/svg"
          viewBox="0 0 24 24"
          fill="currentColor"
          class="w-5 h-5"
        >
          <path
            fill-rule="evenodd"
            d="M10.788 3.21c.448-1.077 1.976-1.077 2.424 0l2.082 5.006 5.404.434c1.164.093 1.636 1.545.749 2.305l-4.116 3.986 1.242 5.36c.386 1.173-.949 2.123-2.033 1.519l-4.816-2.749-4.816 2.749c-1.084.604-2.419-.346-2.033-1.519l1.242-5.36-4.116-3.986c-.887-.76-.415-2.212.749-2.305l5.404-.434 2.082-5.006Z"
            clip-rule="evenodd"
          />
        </svg>
      </span>
      <span class="ml-2 text-sm text-ml-light-gray">
        ({{ productData.reviews.count }} opiniones)
      </span>
    </div>

    <p v-if="productData.price" class="text-4xl font-light mb-1 text-ml-dark-gray">
      ${{ productData.price?.amount?.toLocaleString('es-AR') }}
    </p>
    <p v-else class="text-4xl font-light mb-1 text-ml-dark-gray">
      Precio no disponible
    </p>
    <p v-if="productData.price" class="text-ml-gray mb-1">
      <span class="text-green-600 font-semibold">
        12x $
        {{ (productData.price?.amount / 12)?.toLocaleString('es-AR', {
            minimumFractionDigits: 2,
            maximumFractionDigits: 2
          })
        }}
      </span>
    </p>

    <div class="mt-4 mb-4">
      <p
        class="font-semibold"
        :class="productData.stock > 0 ? 'text-green-600' : 'text-red-500'"
      >
        {{
          productData.stock > 0
            ? `Stock disponible (${productData.stock} unidades)`
            : 'Sin stock'
        }}
      </p>
      <p v-if="productData.stock > 0" class="text-sm text-ml-gray">
        Cantidad:
        <input
            type="number"
            value="1"
            min="1"
            :max="productData.stock"
            :disabled="productData.stock === 0"
            class="w-16 border border-gray-300 rounded p-1 text-center ml-2"
        />
      </p>
    </div>
    <button
      v-if="productData.stock > 0"
      class="w-full bg-ml-blue text-white py-3 rounded-lg font-semibold hover:bg-blue-700 transition-colors mb-2"
    >
      Comprar ahora
    </button>
    <button
      v-if="productData.stock > 0"
      class="w-full bg-blue-100 text-ml-blue py-3 rounded-lg font-semibold hover:bg-blue-200 transition-colors"
    >
      Agregar al carrito
    </button>
    <button
      v-else
      class="w-full bg-gray-300 text-gray-500 py-3 rounded-lg font-semibold cursor-not-allowed mb-2"
    >
      Sin Stock
    </button>

    <div v-if="productData.seller" class="mt-6 border-t border-gray-200 pt-4">
      <p class="text-sm text-ml-gray mb-1">
        Vendido por
        <a
          href="#"
          class="text-ml-blue hover:text-blue-700"
          @click.prevent="showSellerInfo = true"
        >
          {{ productData.seller?.name || 'Vendedor no especificado' }}
        </a>
      </p>
      <p class="text-xs text-ml-light-gray">
        {{ productData.seller?.reputation || 'Reputación no disponible' }} | +{{
          productData.seller?.transactions?.toLocaleString('es-AR') || 0
        }} ventas
      </p>
    </div>

    <div
      v-if="showSellerInfo"
      class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50"
    >
      <div class="bg-white rounded-lg p-6 w-96">
        <h2 class="text-lg font-semibold mb-4">Información del vendedor</h2>
        <p><strong>Nombre:</strong> {{ productData.seller?.name || 'No disponible' }}</p>
        <p><strong>Ubicación:</strong> {{ productData.seller?.location || 'No disponible' }}</p>
        <p><strong>Reputación:</strong> {{ productData.seller?.reputation || 'No disponible' }}</p>
        <p><strong>Transacciones:</strong> {{
          productData.seller?.transactions?.toLocaleString('es-AR') || 0
        }}</p>
        <button
          class="mt-4 w-full bg-ml-blue text-white py-2 rounded-lg font-semibold hover:bg-blue-700 transition-colors"
          @click="showSellerInfo = false"
        >
          Cerrar
        </button>
      </div>
    </div>

    <div v-if="productData.paymentMethods && productData.paymentMethods.length > 0" class="mt-6 border-t border-gray-200 pt-4">
      <h3 class="text-md font-semibold text-ml-dark-gray mb-2">
        Medios de pago
      </h3>
      <ul class="text-sm text-ml-gray space-y-1">
        <li
          v-for="method in productData.paymentMethods.slice(0, 3)"
          :key="method"
        >
          <svg
            xmlns="http://www.w3.org/2000/svg"
            fill="none"
            viewBox="0 0 24 24"
            stroke-width="1.5"
            stroke="currentColor"
            class="w-4 h-4 inline-block mr-1 text-green-500"
          >
            <path
              stroke-linecap="round"
              stroke-linejoin="round"
              d="m4.5 12.75 6 6 9-13.5"
            />
          </svg>
          {{ method }}
        </li>
      </ul>
      <a
        href="#"
        class="text-sm text-ml-blue hover:text-blue-700 mt-2 inline-block"
        @click.prevent="showPaymentInfo = true"
      >
        Conocé otros medios de pago
      </a>
    </div>

    <div
      v-if="showPaymentInfo"
      class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50"
    >
      <div class="bg-white rounded-lg p-6 w-96">
        <h2 class="text-lg font-semibold mb-4">Información de medios de pago</h2>
        <ul class="list-disc pl-5">
          <li v-for="method in productData.paymentMethods" :key="method">
            {{ method }}
          </li>
        </ul>
        <button
          class="mt-4 w-full bg-ml-blue text-white py-2 rounded-lg font-semibold hover:bg-blue-700 transition-colors"
          @click="showPaymentInfo = false"
        >
          Cerrar
        </button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'

defineProps({
  productData: {
    type: Object,
    required: true
  }
})

const showSellerInfo = ref(false)
const showPaymentInfo = ref(false)
</script>