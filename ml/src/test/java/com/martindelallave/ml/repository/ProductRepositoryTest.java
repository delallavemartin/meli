package com.martindelallave.ml.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.martindelallave.ml.repository.ProductRepositoryImpl;
import com.martindelallave.ml.views.ProductDetailView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductRepositoryTest {

  private ProductRepositoryImpl productRepository;
  private List<ProductDetailView> products;

  @BeforeEach
  void setUp() throws IOException {
    productRepository = new ProductRepositoryImpl();

    // Leer el archivo JSON
    ObjectMapper mapper = new ObjectMapper();
    File file = new File("src/main/resources/products-data.json");
    products = List.of(mapper.readValue(file, ProductDetailView[].class));
  }

  @Test
  void testGetProduct_HappyPath() {
    // Usar el primer producto como dato de prueba
    ProductDetailView testProduct = products.getFirst();

    ProductDetailView result = productRepository.getProduct(testProduct.id());

    assertNotNull(result);
    assertEquals(testProduct.id(), result.id());
    assertEquals(testProduct.title(), result.title());
  }

  @Test
  void testGetProduct_ProductNotFound() {
    Exception exception = assertThrows(RuntimeException.class, () -> productRepository.getProduct("999"));
    assertEquals("Product: 999 not found.", exception.getMessage());
  }

}