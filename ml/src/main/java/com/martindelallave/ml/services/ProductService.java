package com.martindelallave.ml.services;

import com.martindelallave.ml.views.ProductDetailView;

public interface ProductService {
  ProductDetailView getProduct(String id);
}
