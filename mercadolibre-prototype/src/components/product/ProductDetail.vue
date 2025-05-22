<template>
  <div>
    <!-- Mostrar mensaje de carga -->
    <div v-if="!product && !error" class="text-center text-ml-gray">
      Cargando producto...
    </div>

    <!-- Mostrar mensaje de error -->
    <div v-if="error" class="text-center text-red-500">
      {{ error }}
    </div>

    <!-- Mostrar detalles del producto -->
    <div
      v-if="product && product.images"
      class="container mx-auto p-4 max-w-6xl bg-white rounded-lg shadow-lg mt-6"
    >
      <div class="grid grid-cols-1 md:grid-cols-10 gap-6">
        <div class="md:col-span-7">
          <div class="mb-6">
            <div class="border border-gray-200 rounded-lg p-2">
              <div
                v-if="product.images.length > 0"
                class="flex space-x-2 mt-2 overflow-x-auto"
              >
                <img
                  :src="product.images[currentImageIndex]"
                  alt="Product Image"
                  class="w-full h-auto max-h-[500px] object-contain rounded"
                />
              </div>
            </div>
            <div
              v-if="product.images.length > 0"
              class="flex space-x-2 mt-2 overflow-x-auto"
            >
              <img
                v-for="(image, index) in product.images"
                :key="index"
                :src="image"
                alt="Thumbnail"
                class="w-16 h-16 object-cover rounded cursor-pointer border-2"
                :class="{
                  'border-ml-blue': index === currentImageIndex,
                  'border-gray-300': index !== currentImageIndex
                }"
                @click="currentImageIndex = index"
              />
            </div>
          </div>

          <div class="mt-8 p-6 border border-gray-200 rounded-lg">
            <h2 class="text-2xl font-semibold mb-4 text-ml-dark-gray">
              Descripción
            </h2>
            <p class="text-ml-gray whitespace-pre-line">
              {{ product.description }}
            </p>
          </div>

          <div
            class="mt-8 p-6 border border-gray-200 rounded-lg"
            v-if="product.features && product.features.length > 0"
          >
            <h2 class="text-2xl font-semibold mb-4 text-ml-dark-gray">
              Características Principales
            </h2>
            <ul class="list-disc list-inside space-y-1 text-ml-gray">
              <li v-for="feature in product.features" :key="feature.name">
                <strong>{{ feature.name }}:</strong> {{ feature.value }}
              </li>
            </ul>
          </div>
        </div>

        <div class="md:col-span-3">
          <div class="border border-gray-200 rounded-lg p-6 sticky top-6">
            <p class="text-xs text-ml-light-gray mb-1">
              {{ product.condition }} | {{ product.reviews.count }} vendidos
            </p>
            <h1 class="text-2xl font-semibold mb-2 text-ml-dark-gray">
              {{ product.title }}
            </h1>

            <div v-if="product.reviews" class="flex items-center mb-4">
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
                ({{ product.reviews.count }} opiniones)
              </span>
            </div>

            <p class="text-4xl font-light mb-1 text-ml-dark-gray">
              ${{ product.price.amount.toLocaleString('es-AR') }}
            </p>
            <p class="text-ml-gray mb-1">
              en
              <span class="text-green-600 font-semibold">
                12x $
                {{ (product.price.amount / 12).toLocaleString('es-AR', {
                  minimumFractionDigits: 2,
                  maximumFractionDigits: 2
                }) }}
              </span>
            </p>
            <a
              href="#"
              class="text-sm text-ml-blue hover:text-blue-700"
            >
              Ver los medios de pago
            </a>

            <div class="mt-4 mb-4">
              <p
                class="font-semibold"
                :class="product.stock > 0 ? 'text-green-600' : 'text-red-500'"
              >
                {{
                  product.stock > 0
                    ? `Stock disponible (${product.stock} unidades)`
                    : 'Sin stock'
                }}
              </p>
              <p class="text-sm text-ml-gray">
                Cantidad:
                <input
                  type="number"
                  value="1"
                  min="1"
                  :max="product.stock"
                  class="w-16 border border-gray-300 rounded p-1 text-center ml-2"
                />
              </p>
            </div>

            <button
              class="w-full bg-ml-blue text-white py-3 rounded-lg font-semibold hover:bg-blue-700 transition-colors mb-2"
            >
              Comprar ahora
            </button>
            <button
              class="w-full bg-blue-100 text-ml-blue py-3 rounded-lg font-semibold hover:bg-blue-200 transition-colors"
            >
              Agregar al carrito
            </button>

            <div class="mt-6 border-t border-gray-200 pt-4">
              <p class="text-sm text-ml-gray mb-1">
                Vendido por
                <a
                  href="#"
                  class="text-ml-blue hover:text-blue-700"
                >
                  {{ product.seller.name }}
                </a>
              </p>
              <p class="text-xs text-ml-light-gray">
                {{ product.seller.reputation }} | +{{
                  product.seller.transactions.toLocaleString('es-AR')
                }} ventas
              </p>
            </div>

            <div class="mt-6 border-t border-gray-200 pt-4">
              <h3 class="text-md font-semibold text-ml-dark-gray mb-2">
                Medios de pago
              </h3>
              <ul class="text-sm text-ml-gray space-y-1">
                <li
                  v-for="method in product.paymentMethods"
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
              >
                Conocé otros medios de pago
              </a>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'

const props = defineProps({
  productId: {
    type: String,
    required: true
  }
})

const product = ref(null)
const currentImageIndex = ref(0)
const error = ref(null)

onMounted(async () => {
  try {
    const response = await fetch(
      `http://localhost:8080/api/products/${props.productId}`
    )
    if (!response.ok) {
      throw new Error('Error al cargar el producto')
    }
    product.value = await response.json()
  } catch (err) {
    error.value = err.message
  }
})
</script>