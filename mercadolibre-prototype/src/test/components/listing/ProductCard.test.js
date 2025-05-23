import { mount } from '@vue/test-utils'
import { describe, it, expect, vi } from 'vitest'
import ProductCard from '@/components/listing/ProductCard.vue'
import { useRouter } from 'vue-router'

vi.mock('vue-router', () => ({
  useRouter: vi.fn()
}))

describe('ProductCard.vue', () => {
  const mockProduct = {
    id: 'MLA12345678',
    title: 'Producto de prueba',
    images: ['image1.jpg'],
    price: { amount: 1000 },
    installments: '12 cuotas sin interés',
    seller: { name: 'Vendedor' },
    isNew: true,
    discount: 20
  }

  const mockRouterPush = vi.fn()
  useRouter.mockReturnValue({ push: mockRouterPush })

  it('renderiza correctamente con los datos del producto', () => {
    const wrapper = mount(ProductCard, {
      props: { product: mockProduct }
    })

    expect(wrapper.find('img').attributes('src')).toBe(mockProduct.images[0])
    expect(wrapper.find('img').attributes('alt')).toBe(mockProduct.title)
    expect(wrapper.text()).toContain(mockProduct.title)
    expect(wrapper.text()).toContain('NUEVO')
    expect(wrapper.text()).toContain(`${mockProduct.discount}% OFF`)
    expect(wrapper.text()).toContain('1.000')
    expect(wrapper.text()).toContain(mockProduct.installments)
    expect(wrapper.text()).toContain(`Vendido por ${mockProduct.seller.name}`)
  })

  it('navega al detalle del producto al hacer clic', async () => {
    const wrapper = mount(ProductCard, {
      props: { product: mockProduct }
    })

    await wrapper.trigger('click')
    expect(mockRouterPush).toHaveBeenCalledWith({ name: 'ProductDetail', params: { id: mockProduct.id } })
  })

  it('no muestra etiquetas NUEVO o descuento si no están presentes', () => {
    const wrapper = mount(ProductCard, {
      props: { product: { ...mockProduct, isNew: false, discount: null } }
    })

    expect(wrapper.text()).not.toContain('NUEVO')
    expect(wrapper.text()).not.toContain('% OFF')
  })

  it('maneja correctamente un producto sin precio', () => {
    const wrapper = mount(ProductCard, {
      props: { product: { ...mockProduct, price: { amount: null } } }
    })

    expect(wrapper.text()).not.toContain('1.000')
  })
})