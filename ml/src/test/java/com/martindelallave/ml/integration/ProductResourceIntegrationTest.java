package com.martindelallave.ml.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.martindelallave.ml.views.ProductDetailView;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ProductResourceIntegrationTest {

  private static final String PRODUCTS_DATA_FILE = "src/main/resources/products-data.json";
  private static final String BASE_ENDPOINT = "/api/products";
  private static final String PRODUCT_NOT_FOUND_MESSAGE = "Product: 999 not found.";

  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private ObjectMapper objectMapper;

  @Test
  void testGetProduct_HappyPath() throws Exception {
    ProductDetailView testProduct = getTestProducts().get(0);

    mockMvc.perform(get(BASE_ENDPOINT + "/{id}", testProduct.id())
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(testProduct.id()))
            .andExpect(jsonPath("$.title").value(testProduct.title()));
  }

  @Test
  void testGetProduct_ProductNotFound() throws Exception {
    mockMvc.perform(get(BASE_ENDPOINT + "/{id}", "999")
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.status").value(404))
            .andExpect(jsonPath("$.message").value(PRODUCT_NOT_FOUND_MESSAGE));
  }

  @Test
  void testGetProducts_HappyPath() throws Exception {
    List<ProductDetailView> products = getTestProducts();

    mockMvc.perform(get(BASE_ENDPOINT)
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.length()").value(products.size()))
            .andExpect(jsonPath("$[0].id").value(products.get(0).id()))
            .andExpect(jsonPath("$[0].title").value(products.get(0).title()));
  }

  private List<ProductDetailView> getTestProducts() throws Exception {
    File file = new File(PRODUCTS_DATA_FILE);
    ProductDetailView[] productsArray = objectMapper.readValue(file, ProductDetailView[].class);
    return Arrays.asList(productsArray);
  }
}