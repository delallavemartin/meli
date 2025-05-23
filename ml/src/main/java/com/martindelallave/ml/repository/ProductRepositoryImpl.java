package com.martindelallave.ml.repository;

import com.martindelallave.ml.file.FileReaderService;
import com.martindelallave.ml.views.ProductDetailView;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ProductRepositoryImpl implements ProductRepository {

  private final FileReaderService fileReaderService;
  public static final String PRODUCTS_DATA_FILE = "src/main/resources/products-data.json";;

  public ProductRepositoryImpl(FileReaderService fileReaderService) {
    this.fileReaderService = fileReaderService;
  }

  @Override
  public ProductDetailView getProduct(String id) {
    List<ProductDetailView> products = fileReaderService.readDataFromFile(PRODUCTS_DATA_FILE, ProductDetailView[].class);
    return products.stream()
            .filter(product -> product.id().equals(id))
            .findFirst()
            .orElseThrow(() -> new NoSuchElementException("Product: " + id + " not found."));
  }

  @Override
  public List<ProductDetailView> getProducts() {
    return fileReaderService.readDataFromFile(PRODUCTS_DATA_FILE, ProductDetailView[].class);
  }
}