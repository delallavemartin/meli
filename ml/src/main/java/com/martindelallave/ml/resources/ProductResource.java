package com.martindelallave.ml.resources;

import com.martindelallave.ml.logging.LogExecution;
import com.martindelallave.ml.services.ProductService;
import com.martindelallave.ml.views.ProductDetailView;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@LogExecution
public class ProductResource {

  private final ProductService productService;

  public ProductResource(ProductService productService) {
    this.productService = productService;
  }

  @GetMapping
  public List<ProductDetailView> getProducts() {
    return productService.getProducts();
  }

  @GetMapping("/{id}")
  public ProductDetailView getProduct(@PathVariable String id) {
    return productService.getProduct(id);
  }
}