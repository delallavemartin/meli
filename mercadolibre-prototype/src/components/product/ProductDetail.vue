<template>
  <div>
    <div v-if="!product && !error" class="text-center text-ml-gray py-10">
      Cargando producto...
    </div>

    <div v-if="error" class="text-center text-red-500 py-10">
      {{ error }}
    </div>

    <div
      v-if="product"
      class="container mx-auto p-4 max-w-6xl bg-white rounded-lg shadow-lg mt-6"
    >
      <div class="grid grid-cols-1 md:grid-cols-10 gap-6">
        <div class="md:col-span-7">
          <ImageGallery :images="product.images" />

          <div class="mt-8 p-6 border border-gray-200 rounded-lg">
            <h2 class="text-2xl font-semibold mb-4 text-ml-dark-gray">
              Descripción
            </h2>
            <p class="text-ml-gray whitespace-pre-line">
              {{ product.description || 'El vendedor no proporcionó una descripción.' }}
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
          <ProductInfoSideBar :productData="product" />
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import ImageGallery from './ImageGallery.vue' // Ajusta la ruta si es necesario
import ProductInfoSideBar from './ProductInfoSideBar.vue' // Ajusta la ruta si es necesario

const props = defineProps({
  productId: {
    type: String,
    required: true
  }
})

const product = ref(null)
const error = ref(null)

onMounted(async () => {
  try {
    // Asegúrate de que la URL base sea la correcta para tu entorno
    const response = await fetch(
      `http://localhost:8080/api/products/${props.productId}`
    )
    if (!response.ok) {
      throw new Error('Error al cargar el producto. Código: ' + response.status)
    }
    const data = await response.json()
    // Simulación de datos si la API no devuelve todo lo esperado por los componentes hijos
    // En un caso real, la API debería devolver una estructura completa.
    product.value = {
        ...data, // Datos de la API
        // Asegurarse que las propiedades esperadas por los hijos existan,
        // incluso si vienen vacías desde la API, para evitar errores de 'undefined'.
        images: data.images || [],
        condition: data.condition || 'No especificado',
        reviews: data.reviews || { count: 0, average: 0 }, // Asumiendo una estructura para reviews
        title: data.title || 'Producto sin título',
        price: data.price || { amount: 0, currency: 'ARS' }, // Asumiendo una estructura para price
        stock: data.stock !== undefined ? data.stock : 0,
        seller: data.seller || { name: 'Vendedor Desconocido', reputation: 'Sin reputación', transactions: 0 },
        paymentMethods: data.paymentMethods || [],
        description: data.description || 'No hay descripción disponible.',
        features: data.features || []
      };

  } catch (err) {
    console.error("Error fetching product:", err);
    error.value = err.message || 'Ocurrió un error desconocido al obtener el producto.'
  }
})
</script>