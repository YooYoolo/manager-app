package com.yotsume.managerapp.service;

import com.yotsume.managerapp.entity.Product;

import java.util.List;

public interface ProductService {
    List<Product> findAllProducts();

    Product createProduct(String title, String details);
}
