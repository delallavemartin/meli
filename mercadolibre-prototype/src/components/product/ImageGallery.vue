<template>
  <div>
    <div class="border border-gray-200 rounded-lg p-2">
      <div v-if="images && images.length > 0">
        <img
          :src="images[currentImageIndex]"
          alt="Product Image"
          class="w-full h-auto max-h-[500px] object-contain rounded"
        />
      </div>
      <div v-else class="text-center text-ml-gray p-10">
        No hay imágenes disponibles.
      </div>
    </div>
    <div
      v-if="images && images.length > 1"
      class="flex space-x-2 mt-2 overflow-x-auto"
    >
      <img
        v-for="(image, index) in images"
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
</template>

<script setup>
import { ref, watch } from 'vue'

const props = defineProps({
  images: {
    type: Array,
    default: () => []
  }
})

const currentImageIndex = ref(0)

// Reiniciar el índice si las imágenes cambian y el índice actual está fuera de los límites
watch(() => props.images, (newImages) => {
  if (newImages && newImages.length > 0 && currentImageIndex.value >= newImages.length) {
    currentImageIndex.value = 0;
  } else if (!newImages || newImages.length === 0) {
    currentImageIndex.value = 0; // O manejar de otra forma si no hay imágenes
  }
}, { immediate: true });

</script>