package com.martindelallave.ml.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.martindelallave.ml.views.ProductDetailView;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.io.File;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ProductResourceIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  void testGetProduct_HappyPath() throws Exception {
    // Leer el archivo JSON para obtener un producto de prueba
    File file = new File("src/main/resources/products-data.json");
    ProductDetailView[] products = objectMapper.readValue(file, ProductDetailView[].class);
    ProductDetailView testProduct = products[0];

    // Realizar la solicitud GET y verificar la respuesta
    mockMvc.perform(get("/api/products/{id}", testProduct.id())
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(testProduct.id()))
            .andExpect(jsonPath("$.title").value(testProduct.title()));
  }

  @Test
  void testGetProduct_ProductNotFound() throws Exception {
    // Realizar la solicitud GET con un ID inexistente
    mockMvc.perform(get("/api/products/{id}", "999")
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$").value("Product: 999 not found."));
  }

  @Test
  void testGetProducts_HappyPath() throws Exception {
    // Leer el archivo JSON para obtener los productos de prueba
    File file = new File("src/main/resources/products-data.json");
    ProductDetailView[] products = objectMapper.readValue(file, ProductDetailView[].class);

    // Realizar la solicitud GET y verificar la respuesta
    mockMvc.perform(get("/api/products")
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.length()").value(products.length))
            .andExpect(jsonPath("$[0].id").value(products[0].id()))
            .andExpect(jsonPath("$[0].title").value(products[0].title()));
  }

}