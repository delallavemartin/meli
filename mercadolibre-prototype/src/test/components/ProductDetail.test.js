import { mount } from '@vue/test-utils'
import { describe, it, expect, vi } from 'vitest'
import ProductDetail from '@/components/product/ProductDetail.vue'

describe('ProductDetail.vue', () => {
  const mockProduct = {
    id: 'MLA12345678',
    images: ['image1.jpg', 'image2.jpg'],
    description: 'Descripción del producto',
    features: [{ name: 'Feature1', value: 'Value1' }],
    condition: 'Nuevo',
    reviews: { count: 10 },
    title: 'Producto de prueba',
    price: { amount: 1000 },
    stock: 5,
    seller: { name: 'Vendedor', reputation: 'Excelente', transactions: 100 },
    paymentMethods: ['Tarjeta de crédito', 'Efectivo']
  }

  const fetchMock = vi.fn(() =>
    Promise.resolve({
      ok: true,
      json: () => Promise.resolve(mockProduct)
    })
  )

  global.fetch = fetchMock

  it('renderiza correctamente con datos válidos (happy path)', async () => {
    const wrapper = mount(ProductDetail, {
      props: { productId: 'MLA12345678' }
    })

    // Esperar a que se carguen los datos
    await new Promise(resolve => setTimeout(resolve, 0))

    expect(wrapper.find('h1').text()).toBe(mockProduct.title)
    expect(wrapper.find('p').text()).toContain(mockProduct.description)
    expect(wrapper.findAll('img').length).toBe(mockProduct.images.length + 1) // Thumbnails + imagen principal
  })

  it('muestra un mensaje de error si la API falla', async () => {
    fetchMock.mockImplementationOnce(() =>
      Promise.resolve({ ok: false })
    )

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
    fetchMock.mockImplementationOnce(() =>
      Promise.resolve({
        ok: true,
        json: () => Promise.resolve({ ...mockProduct, images: [] })
      })
    )

    const wrapper = mount(ProductDetail, {
      props: { productId: 'MLA12345678' }
    })

    // Esperar a que se carguen los datos
    await new Promise(resolve => setTimeout(resolve, 0))

    expect(wrapper.findAll('img').length).toBe(0)
  })
})