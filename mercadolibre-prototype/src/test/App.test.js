import { describe, it, expect } from 'vitest';
import { mount } from '@vue/test-utils';
import App from '@/App.vue';

describe('App.vue', () => {
  it('renderiza correctamente el componente Navbar', () => {
    const wrapper = mount(App, {
      global: {
        stubs: {
          Navbar: { template: '<nav>Mock Navbar</nav>' }, // Stub para Navbar
        },
      },
    });
    const navbar = wrapper.find('nav');
    expect(navbar.exists()).toBe(true);
    expect(navbar.text()).toBe('Mock Navbar');
  });

  it('contiene el elemento router-view', () => {
    const wrapper = mount(App, {
      global: {
        stubs: {
          'router-view': { template: '<div>Mock Router View</div>' }, // Stub para router-view
        },
      },
    });
    const routerView = wrapper.find('div');
    expect(routerView.exists()).toBe(true);
    expect(routerView.text()).toBe('Mock Router View');
  });
});