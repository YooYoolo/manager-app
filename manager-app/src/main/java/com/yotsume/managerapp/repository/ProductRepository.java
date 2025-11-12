package com.yotsume.managerapp.repository;

import com.yotsume.managerapp.entity.Product;

import java.util.List;

public interface ProductRepository {
    List<Product> findAll();

    Product save(Product product);
}
