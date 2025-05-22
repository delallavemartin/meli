package com.martindelallave.ml.services;

import com.martindelallave.ml.repository.ProductRepository;
import com.martindelallave.ml.views.ProductDetailView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class ProductServiceImpl implements ProductService{

  @Autowired
  public ProductRepository productRepository;

  @Override
  public ProductDetailView getProduct(String id) {
    if (Objects.isNull(id)){
      throw new IllegalArgumentException("Product id can't be null.");
    }
    return productRepository.getProduct(id);
  }
}
