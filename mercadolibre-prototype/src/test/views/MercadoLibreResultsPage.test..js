import { mount } from '@vue/test-utils'
import { describe, it, expect, vi } from 'vitest'
import ProductListing from '@/components/listing/ProductListing.vue'
import ProductCard from '@/components/listing/ProductCard.vue'
import { fetchProducts } from '@/services/productService.js'

vi.mock('@/services/productService.js', () => ({
  fetchProducts: vi.fn()
}))

describe('ProductListing.vue', () => {
  const mockProducts = [
    { id: '1', title: 'Producto 1', images: ['image1.jpg'], price: { amount: 1000 } },
    { id: '2', title: 'Producto 2', images: ['image2.jpg'], price: { amount: 2000 } }
  ]

  it('renderiza correctamente los productos', async () => {
    fetchProducts.mockResolvedValueOnce(mockProducts)

    const wrapper = mount(ProductListing)

    // Esperar a que se carguen los datos
    await new Promise(resolve => setTimeout(resolve, 0))

    const productCards = wrapper.findAllComponents(ProductCard)
    expect(productCards.length).toBe(mockProducts.length)
    expect(productCards[0].props('product')).toEqual(mockProducts[0])
    expect(productCards[1].props('product')).toEqual(mockProducts[1])
  })

  it('muestra un mensaje de error si la API falla', async () => {
    fetchProducts.mockRejectedValueOnce(new Error('Error al cargar los productos'))

    const wrapper = mount(ProductListing)

    // Esperar a que se carguen los datos
    await new Promise(resolve => setTimeout(resolve, 0))

    expect(wrapper.text()).toContain('Ocurrió un error desconocido al obtener los productos.')
  })

  it('no renderiza productos si la lista está vacía', async () => {
    fetchProducts.mockResolvedValueOnce([])

    const wrapper = mount(ProductListing)

    // Esperar a que se carguen los datos
    await new Promise(resolve => setTimeout(resolve, 0))

    const productCards = wrapper.findAllComponents(ProductCard)
    expect(productCards.length).toBe(0)
  })
})