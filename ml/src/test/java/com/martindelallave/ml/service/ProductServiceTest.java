package com.martindelallave.ml.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.martindelallave.ml.repository.ProductRepository;
import com.martindelallave.ml.services.ProductServiceImpl;
import com.martindelallave.ml.views.ProductDetailView;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceTest {

  private static final String PRODUCTS_DATA_FILE = "src/main/resources/products-data.json";
  private static final String PRODUCT_NOT_FOUND_MESSAGE = "Product: 999 not found.";
  private static final String NULL_ID_MESSAGE = "Product id can't be null.";
  private static final String NO_RESULTS_MESSAGE = "No results found.";

  private final ProductRepository productRepository = mock(ProductRepository.class);
  private final ProductServiceImpl productService = new ProductServiceImpl(productRepository);

  @Test
  void shouldReturnProductWhenIdExists() throws Exception {
    ProductDetailView mockProduct = loadTestProducts().getFirst();

    when(productRepository.getProduct(mockProduct.id())).thenReturn(mockProduct);

    ProductDetailView result = productService.getProduct(mockProduct.id());

    assertNotNull(result);
    assertEquals(mockProduct.id(), result.id());
    assertEquals(mockProduct.title(), result.title());
    verify(productRepository, times(1)).getProduct(mockProduct.id());
  }

  @Test
  void shouldThrowExceptionWhenProductNotFound() {
    when(productRepository.getProduct("999")).thenThrow(new RuntimeException(PRODUCT_NOT_FOUND_MESSAGE));

    Exception exception = assertThrows(RuntimeException.class, () -> productService.getProduct("999"));
    assertEquals(PRODUCT_NOT_FOUND_MESSAGE, exception.getMessage());
    verify(productRepository, times(1)).getProduct("999");
  }

  @Test
  void shouldThrowExceptionWhenIdIsNull() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> productService.getProduct(null));
    assertEquals(NULL_ID_MESSAGE, exception.getMessage());
    verify(productRepository, never()).getProduct(anyString());
  }

  @Test
  void shouldReturnAllProductsWhenAvailable() {
    List<ProductDetailView> mockProducts = createMockProducts();
    when(productRepository.getProducts()).thenReturn(mockProducts);

    List<ProductDetailView> result = productService.getProducts();

    assertNotNull(result);
    assertFalse(result.isEmpty());
    assertEquals(mockProducts.size(), result.size());
    assertEquals(mockProducts.get(0).title(), result.getFirst().title());
    verify(productRepository, times(1)).getProducts();
  }

  @Test
  void shouldThrowExceptionWhenNoProductsFound() {
    when(productRepository.getProducts()).thenReturn(List.of());

    Exception exception = assertThrows(RuntimeException.class, productService::getProducts);
    assertEquals(NO_RESULTS_MESSAGE, exception.getMessage());
    verify(productRepository, times(1)).getProducts();
  }

  @Test
  void shouldThrowExceptionWhenProductsResponseIsNull() {
    when(productRepository.getProducts()).thenReturn(null);

    Exception exception = assertThrows(RuntimeException.class, productService::getProducts);
    assertEquals(NO_RESULTS_MESSAGE, exception.getMessage());
    verify(productRepository, times(1)).getProducts();
  }

  private List<ProductDetailView> loadTestProducts() throws Exception {
    ObjectMapper mapper = new ObjectMapper();
    File file = new File(PRODUCTS_DATA_FILE);
    return List.of(mapper.readValue(file, ProductDetailView[].class));
  }

  private List<ProductDetailView> createMockProducts() {
    return List.of(
            new ProductDetailView(
                    "1", "Producto 1", "Descripción 1",
                    new ProductDetailView.Price("USD", 100, 0),
                    List.of("image1.jpg", "image2.jpg"), 0,
                    List.of("Credit Card", "PayPal"),
                    new ProductDetailView.Seller("Vendedor 1", "Ubicación 1", "Alta", 100),
                    10, "Nuevo",
                    new ProductDetailView.Reviews(4.5, 10),
                    List.of(new ProductDetailView.Feature("Color", "Rojo"))
            ),
            new ProductDetailView(
                    "2", "Producto 2", "Descripción 2",
                    new ProductDetailView.Price("USD", 200, 0),
                    List.of("image3.jpg", "image4.jpg"), 1,
                    List.of("Credit Card"),
                    new ProductDetailView.Seller("Vendedor 2", "Ubicación 2", "Media", 50),
                    5, "Usado",
                    new ProductDetailView.Reviews(3.8, 5),
                    List.of(new ProductDetailView.Feature("Tamaño", "Grande"))
            )
    );
  }
}