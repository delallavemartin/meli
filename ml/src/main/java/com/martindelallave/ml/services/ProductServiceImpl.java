package com.martindelallave.ml.services;

import com.martindelallave.ml.logging.LogExecution;
import com.martindelallave.ml.repository.ProductRepository;
import com.martindelallave.ml.views.ProductDetailView;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@LogExecution
public class ProductServiceImpl implements ProductService {

  private final ProductRepository productRepository;

  public ProductServiceImpl(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  @Override
  public ProductDetailView getProduct(String id) {
    if (Objects.isNull(id)) {
      throw new IllegalArgumentException("Product id can't be null.");
    }
    return productRepository.getProduct(id);
  }

  @Override
  public List<ProductDetailView> getProducts() {
    List<ProductDetailView> products = productRepository.getProducts();
    if (products == null || products.isEmpty()) {
      throw new RuntimeException("No results found.");
    }
    return products;
  }
}