package com.martindelallave.ml.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.martindelallave.ml.file.FileReaderService;
import com.martindelallave.ml.file.FileReaderServiceImpl;
import com.martindelallave.ml.views.ProductDetailView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductRepositoryTest {

  // Definir la ruta del archivo
  public static final String PRODUCTS_DATA_FILE = "src/main/resources/products-data.json";

  private ProductRepositoryImpl productRepository;
  private List<ProductDetailView> products;

  @BeforeEach
  void setUp() throws IOException {
    // Crear el ObjectMapper y FileReaderService
    ObjectMapper objectMapper = new ObjectMapper();
    FileReaderService fileReaderService = new FileReaderServiceImpl(objectMapper);

    // Inicializar ProductRepositoryImpl con los argumentos requeridos
    productRepository = new ProductRepositoryImpl(fileReaderService);

    // Leer el archivo JSON
    File file = new File(PRODUCTS_DATA_FILE);
    products = List.of(objectMapper.readValue(file, ProductDetailView[].class));
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

  @Test
  void testGetProducts_HappyPath() {
    List<ProductDetailView> result = productRepository.getProducts();

    assertNotNull(result);
    assertFalse(result.isEmpty());
    assertEquals(products.size(), result.size());
    assertEquals(products.getFirst().id(), result.getFirst().id());
  }
}