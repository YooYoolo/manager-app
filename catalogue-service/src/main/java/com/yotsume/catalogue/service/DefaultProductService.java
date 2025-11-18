package com.yotsume.catalogue.service;

import com.yotsume.catalogue.entity.Product;
import com.yotsume.catalogue.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DefaultProductService implements ProductService {
    private final ProductRepository productRepository;

    @Override
    public Iterable<Product> findAllProducts(String filter) {
        if (filter != null && !filter.isBlank()) {
            return this.productRepository.findAllByTitleLikeIgnoreCase(filter);
        } else {
            return this.productRepository.findAll();
        }
    }

    @Override
    @Transactional
    public Product createProduct(String title, String details) {
        return this.productRepository.save(new Product(null, title, details));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Product> findProduct(int productId) {
        return this.productRepository.findById(productId);
    }

    @Override
    @Transactional
    public void updateProduct(Integer id, String title, String details) {
        Product product = this.productRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Product not found with id: " + id));
        product.setTitle(title);
        product.setDetails(details);
    }

    @Override
    @Transactional
    public void deleteProduct(Integer id) {
        this.productRepository.deleteById(id);
    }
}
