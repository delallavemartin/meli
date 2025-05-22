package com.martindelallave.ml.services;

import com.martindelallave.ml.views.ProductDetailView;

import java.util.List;

public interface ProductService {
  ProductDetailView getProduct(String id);

  List<ProductDetailView> getProducts();
}
