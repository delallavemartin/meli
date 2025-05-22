package com.martindelallave.ml.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.martindelallave.ml.repository.ProductRepository;
import com.martindelallave.ml.services.ProductServiceImpl;
import com.martindelallave.ml.views.ProductDetailView;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceTest {

  @Mock
  private ProductRepository productRepository;

  @InjectMocks
  private ProductServiceImpl productService;

  public ProductServiceTest() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testGetProduct_HappyPath_UsingJsonFile() throws Exception {
    // Leer el archivo JSON
    ObjectMapper mapper = new ObjectMapper();
    File file = new File("src/main/resources/products-data.json");
    ProductDetailView[] productsArray = mapper.readValue(file, ProductDetailView[].class);

    // Seleccionar un producto del arreglo como mock
    ProductDetailView mockProduct = productsArray[0];

    // Configuración del mock
    when(productRepository.getProduct(mockProduct.id())).thenReturn(mockProduct);

    // Llamada al método
    ProductDetailView result = productService.getProduct(mockProduct.id());

    // Verificaciones
    assertNotNull(result);
    assertEquals(mockProduct.id(), result.id());
    assertEquals(mockProduct.title(), result.title());
    verify(productRepository, times(1)).getProduct(mockProduct.id());
  }

  @Test
  void testGetProduct_ProductNotFound() {
    // Configuración del mock
    when(productRepository.getProduct("999")).thenThrow(new RuntimeException("Product: 999 not found."));

    // Llamada al método y verificación de excepción
    Exception exception = assertThrows(RuntimeException.class, () -> productService.getProduct("999"));
    assertEquals("Product: 999 not found.", exception.getMessage());
    verify(productRepository, times(1)).getProduct("999");
  }

  @Test
  void testGetProduct_NullId() {
    // Llamada al método y verificación de excepción
    Exception exception = assertThrows(IllegalArgumentException.class, () -> productService.getProduct(null));
    assertEquals("Product id can't be null.", exception.getMessage());
    verify(productRepository, never()).getProduct(anyString());
  }
}