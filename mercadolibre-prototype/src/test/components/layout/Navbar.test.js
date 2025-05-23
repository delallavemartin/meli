import { mount } from '@vue/test-utils'
import { describe, it, expect } from 'vitest'
import NavBar from '@/components/layout/NavBar.vue'

describe('NavBar.vue', () => {
  it('renderiza correctamente el logo', () => {
    const wrapper = mount(NavBar)
    const logo = wrapper.find('img[alt="MercadoLibre"]')
    expect(logo.exists()).toBe(true)
    expect(logo.attributes('src')).toBe('/logo_large_plus.webp')
  })

  it('tiene la clase de estilo correcta en el contenedor principal', () => {
    const wrapper = mount(NavBar)
    expect(wrapper.classes()).toContain('bg-yellow-400')
  })
})