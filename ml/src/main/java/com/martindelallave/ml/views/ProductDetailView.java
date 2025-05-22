package com.martindelallave.ml.views;

import java.io.Serializable;
import java.util.List;

public record ProductDetailView(
        String id,
        String title,
        String description,
        Price price,
        List<String> images,
        int mainImageIndex,
        List<String> paymentMethods,
        Seller seller,
        int stock,
        String condition,
        Reviews reviews,
        List<Feature> features
) implements Serializable {

  public record Price(String currency, int amount, int decimals) {}

  public record Seller(String name, String location, String reputation, int transactions) {}

  public record Reviews(double rating, int count) {}

  public record Feature(String name, String value) {}
}