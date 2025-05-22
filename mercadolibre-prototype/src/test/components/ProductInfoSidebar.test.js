import { mount } from '@vue/test-utils'
import { describe, it, expect } from 'vitest'
import ProductInfo from '@/components/product/ProductInfoSidebar.vue'

describe('ProductInfo.vue', () => {
  const mockProductData = {
    condition: 'Nuevo',
    reviews: { count: 1250 },
    title: 'Producto de prueba',
    price: { amount: 185000 },
    stock: 25,
    seller: { name: 'Vendedor', reputation: 'Excelente', transactions: 15020 },
    paymentMethods: ['Tarjeta de crédito', 'Efectivo']
  }

  it('renderiza correctamente con datos válidos (happy path)', () => {
    const wrapper = mount(ProductInfo, {
      props: { productData: mockProductData }
    })

    expect(wrapper.text()).toContain(mockProductData.condition)
    expect(wrapper.text()).toContain(mockProductData.title)
    expect(wrapper.text()).toContain(mockProductData.reviews.count)
    expect(wrapper.text()).toContain('$185.000')
    expect(wrapper.text()).toContain('Stock disponible (25 unidades)')
    expect(wrapper.text()).toContain(mockProductData.seller.name)
    expect(wrapper.text()).toContain(mockProductData.seller.reputation)
    expect(wrapper.text()).toContain(mockProductData.paymentMethods[0])
  })

  it('muestra "Cargando información..." si no se proporciona productData', () => {
    const wrapper = mount(ProductInfo, {
      props: { productData: null }
    })

    expect(wrapper.text()).toContain('Cargando información...')
  })

  it('muestra "Sin Stock" si el stock es 0', () => {
    const wrapper = mount(ProductInfo, {
      props: { productData: { ...mockProductData, stock: 0 } }
    })

    expect(wrapper.text()).toContain('Sin Stock')
  })

  it('maneja correctamente un producto sin reseñas', () => {
    const wrapper = mount(ProductInfo, {
      props: { productData: { ...mockProductData, reviews: null } }
    })

    expect(wrapper.text()).toContain('0 vendidos')
    expect(wrapper.text()).not.toContain('opiniones')
  })

  it('maneja correctamente un producto sin métodos de pago', () => {
    const wrapper = mount(ProductInfo, {
      props: { productData: { ...mockProductData, paymentMethods: [] } }
    })

    expect(wrapper.text()).not.toContain('Medios de pago')
  })
})