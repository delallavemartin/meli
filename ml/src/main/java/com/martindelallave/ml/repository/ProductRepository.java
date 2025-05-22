package com.martindelallave.ml.repository;

import com.martindelallave.ml.views.ProductDetailView;

public interface ProductRepository {
  ProductDetailView getProduct(String id);
}
