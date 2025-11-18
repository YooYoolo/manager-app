package com.yotsume.catalogue.controller;

import com.yotsume.catalogue.controller.payload.UpdateProductPayload;
import com.yotsume.catalogue.entity.Product;
import com.yotsume.catalogue.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("catalogue-api/products/{productId:\\d+}")
@RequiredArgsConstructor
public class ProductRestController {

    private final ProductService productService;

    @GetMapping
    public Product findProductById(@PathVariable int productId) {
        return productService.findProduct(productId)
                .orElseThrow(() -> new NoSuchElementException("catalogue.errors.product.notFound"));
    }

    @PatchMapping
    public ResponseEntity<?> updateProduct(
            @PathVariable("productId") int productId,
            @Valid @RequestBody UpdateProductPayload payload) {

            this.productService.updateProduct(productId, payload.title(), payload.details());
            return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteProduct(@PathVariable("productId") int productId) {
        this.productService.deleteProduct(productId);
        return ResponseEntity.noContent().build();
    }

}
