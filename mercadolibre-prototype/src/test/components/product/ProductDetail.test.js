import { mount } from '@vue/test-utils'
import { describe, it, expect, vi } from 'vitest'
import ProductDetail from '@/components/product/ProductDetail.vue'
import { fetchProduct } from '@/services/productService.js'

vi.mock('@/services/productService.js', () => ({
  fetchProduct: vi.fn()
}))

describe('ProductDetail.vue', () => {
  const mockProduct = {
    id: 'MLA12345678',
    images: ['image1.jpg', 'image2.jpg'],
    description: 'Descripción del producto',
    features: [{ name: 'Feature1', value: 'Value1' }],
    condition: 'Nuevo',
    reviews: { count: 10, average: 4.5 },
    title: 'Producto de prueba',
    price: { amount: 1000, currency: 'ARS' },
    stock: 5,
    seller: { name: 'Vendedor', reputation: 'Excelente', transactions: 100 },
    paymentMethods: ['Tarjeta de crédito', 'Efectivo']
  }

  it('renderiza correctamente con datos válidos (happy path)', async () => {
    fetchProduct.mockResolvedValueOnce(mockProduct)

    const wrapper = mount(ProductDetail, {
      props: { productId: 'MLA12345678' }
    })

    // Esperar a que se carguen los datos
    await new Promise(resolve => setTimeout(resolve, 0))

    expect(wrapper.find('h2').text()).toBe('Descripción')
    expect(wrapper.text()).toContain(mockProduct.description)
    expect(wrapper.findAll('img').length).toBe(mockProduct.images.length+1)
  })

  it('muestra un mensaje de error si la API falla', async () => {
    fetchProduct.mockRejectedValueOnce(new Error('Error al cargar el producto'))

    const wrapper = mount(ProductDetail, {
      props: { productId: 'MLA12345678' }
    })

    // Esperar a que se carguen los datos
    await new Promise(resolve => setTimeout(resolve, 0))

    expect(wrapper.text()).toContain('Error al cargar el producto')
  })

  it('muestra un mensaje de carga mientras se obtienen los datos', () => {
    const wrapper = mount(ProductDetail, {
      props: { productId: 'MLA12345678' }
    })

    expect(wrapper.text()).toContain('Cargando producto...')
  })

  it('maneja correctamente un producto sin imágenes', async () => {
    fetchProduct.mockResolvedValueOnce({ ...mockProduct, images: [] })

    const wrapper = mount(ProductDetail, {
      props: { productId: 'MLA12345678' }
    })

    // Esperar a que se carguen los datos
    await new Promise(resolve => setTimeout(resolve, 0))

    expect(wrapper.findAll('img').length).toBe(0)
  })
})