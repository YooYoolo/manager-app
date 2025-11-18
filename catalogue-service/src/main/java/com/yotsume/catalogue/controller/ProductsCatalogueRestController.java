package com.yotsume.catalogue.controller;

import com.yotsume.catalogue.controller.payload.NewProductPayload;
import com.yotsume.catalogue.entity.Product;
import com.yotsume.catalogue.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("catalogue-api/products")
public class ProductsCatalogueRestController {

    private final ProductService productService;

    @GetMapping
    public Iterable<Product> findProducts(
            @RequestParam(name = "filter", required = false) String filter) {
        return productService.findAllProducts(filter);
    }

    @PostMapping
    public ResponseEntity<?> createProduct(@RequestBody @Valid NewProductPayload payload,
                                                 UriComponentsBuilder uriBuilder) {
        Product product = productService.createProduct(payload.title(), payload.details());
        return ResponseEntity
                .created(uriBuilder
                        .replacePath("/catalogue-api/products/{productId}")
                        .build(Map.of("productId", product.getId())))
                .body(product);
    }
}
