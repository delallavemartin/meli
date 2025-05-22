package com.martindelallave.ml.resources;

import com.martindelallave.ml.services.ProductService;
import com.martindelallave.ml.views.ProductDetailView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductResource {

  @Autowired
  private ProductService productService;

  @GetMapping
  public List<ProductDetailView> getProducts() {
    return productService.getProducts();
  }

  @GetMapping("/{id}")
  public ProductDetailView getProduct(@PathVariable String id) {
    return productService.getProduct(id);
  }
}
