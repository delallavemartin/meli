import { mount } from '@vue/test-utils'
import { describe, it, expect } from 'vitest'
import Component from '@/components/product/ImageGallery.vue'

describe('Component', () => {
  it('muestra un mensaje cuando no hay imágenes disponibles', () => {
    const wrapper = mount(Component, {
      props: {
        images: []
      }
    })
    expect(wrapper.text()).toContain('No hay imágenes disponibles.')
  })

  it('muestra la imagen principal cuando hay imágenes disponibles', () => {
    const wrapper = mount(Component, {
      props: {
        images: ['image1.jpg', 'image2.jpg']
      }
    })
    const mainImage = wrapper.find('img[alt="Product Image"]')
    expect(mainImage.exists()).toBe(true)
    expect(mainImage.attributes('src')).toBe('image1.jpg')
  })

  it('cambia la imagen principal al hacer clic en una miniatura', async () => {
    const wrapper = mount(Component, {
      props: {
        images: ['image1.jpg', 'image2.jpg']
      }
    })
    const thumbnails = wrapper.findAll('img[alt="Thumbnail"]')
    await thumbnails[1].trigger('click')
    const mainImage = wrapper.find('img[alt="Product Image"]')
    expect(mainImage.attributes('src')).toBe('image2.jpg')
  })

  it('aplica la clase activa a la miniatura seleccionada', async () => {
    const wrapper = mount(Component, {
      props: {
        images: ['image1.jpg', 'image2.jpg']
      }
    })
    const thumbnails = wrapper.findAll('img[alt="Thumbnail"]')
    await thumbnails[1].trigger('click')
    expect(thumbnails[1].classes()).toContain('border-ml-blue')
    expect(thumbnails[0].classes()).toContain('border-gray-300')
  })
})