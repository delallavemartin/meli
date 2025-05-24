import { mount } from '@vue/test-utils'
import { describe, it, expect, vi } from 'vitest'
import MercadoLibreResultsPage from '@/views/MercadoLibreResultsPage.vue'
import ProductCard from '@/components/listing/ProductCard.vue'
import { fetchProducts } from '@/services/productService.js'

vi.mock('@/services/productService.js', () => ({
  fetchProducts: vi.fn()
}))

describe('MercadoLibreResultsPage.vue', () => {
  const mockProducts = [
    { id: '1', title: 'Producto 1', images: ['image1.jpg'], price: { amount: 1000 }, seller: { name: 'Vendedor 1' } },
    { id: '2', title: 'Producto 2', images: ['image2.jpg'], price: { amount: 2000 }, seller: { name: 'Vendedor 2' } }
  ]

  it('renderiza correctamente los productos', async () => {
    fetchProducts.mockResolvedValueOnce(mockProducts)

    const wrapper = mount(MercadoLibreResultsPage)

    // Esperar a que se carguen los datos
    await new Promise(resolve => setTimeout(resolve, 100))

    const productCards = wrapper.findAllComponents(ProductCard)
    expect(productCards.length).toBe(mockProducts.length)
    expect(productCards[0].props('product')).toEqual(mockProducts[0])
    expect(productCards[1].props('product')).toEqual(mockProducts[1])
  })

  it('no renderiza productos si la lista está vacía', async () => {
    fetchProducts.mockResolvedValueOnce([])

    const wrapper = mount(MercadoLibreResultsPage)

    // Esperar a que se carguen los datos
    await new Promise(resolve => setTimeout(resolve, 0))

    const productCards = wrapper.findAllComponents(ProductCard)
    expect(productCards.length).toBe(0)
  })
})