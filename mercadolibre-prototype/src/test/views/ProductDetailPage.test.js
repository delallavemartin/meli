import { mount } from '@vue/test-utils'
import { describe, it, expect, vi } from 'vitest'
import { createRouter, createWebHistory } from 'vue-router'
import ProductPage from '@/views/ProductDetailPage.vue'
import ProductDetail from '@/components/product/ProductDetail.vue'

vi.mock('@/components/product/ProductDetail.vue', () => ({
  default: {
    name: 'ProductDetail',
    props: ['productId'],
    template: '<div>Mock ProductDetail for {{ productId }}</div>'
  }
}))

describe('ProductPage.vue', () => {
  const routes = [
    { path: '/product/:id', component: ProductPage }
  ]

  const router = createRouter({
    history: createWebHistory(),
    routes
  })

  it('renderiza ProductDetail con el productId correcto (happy path)', async () => {
    router.push('/product/MLA12345678')
    await router.isReady()

    const wrapper = mount(ProductPage, {
      global: {
        plugins: [router]
      }
    })

    expect(wrapper.findComponent(ProductDetail).exists()).toBe(true)
    expect(wrapper.findComponent(ProductDetail).props('productId')).toBe('MLA12345678')
  })
})