package com.martindelallave.ml.repository;

import com.martindelallave.ml.views.ProductDetailView;

import java.util.List;

public interface ProductRepository {
  ProductDetailView getProduct(String id);
  List<ProductDetailView> getProducts();
}
