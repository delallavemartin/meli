package com.martindelallave.ml.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.martindelallave.ml.views.ProductDetailView;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ProductRepositoryImpl implements ProductRepository{


  @Override
  public ProductDetailView getProduct(String id) {
    ObjectMapper mapper = new ObjectMapper();
    File file = new File("src/main/resources/products-data.json");
    try (InputStream inputStream = new FileInputStream(file)) {
      // Deserializar el archivo JSON como una lista de ProductDetailView
      List<ProductDetailView> products = List.of(mapper.readValue(inputStream, ProductDetailView[].class));

      // Buscar el producto que coincida con el ID
      return products.stream()
              .filter(product -> product.id().equals(id))
              .findFirst()
              .orElseThrow(() -> new NoSuchElementException("Product: " + id + " not found."));
    } catch (IOException e) {
      throw new RuntimeException("Error parsing data for product: " + id, e);
    }
  }
}
