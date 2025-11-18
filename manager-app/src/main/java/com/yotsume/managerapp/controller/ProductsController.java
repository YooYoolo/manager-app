package com.yotsume.managerapp.controller;

import com.yotsume.managerapp.client.BadRequestException;
import com.yotsume.managerapp.client.ProductRestClient;
import com.yotsume.managerapp.controller.payload.NewProductPayload;
import com.yotsume.managerapp.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("catalogue/products")
public class ProductsController {

    private final ProductRestClient productRestClient;

    @GetMapping(value = "list")
    public String getProductsList(
            Model model,
            @RequestParam(name = "filter", required = false) String filter) {

        model.addAttribute("products",
                this.productRestClient.findAllProducts(filter));
        model.addAttribute("filter", filter);
        return "catalogue/products/list";
    }

    @GetMapping("create")
    public String getNewProductPage() {
        return "catalogue/products/new_product";
    }

    @PostMapping("create")
    public String CreateProduct(NewProductPayload payload,
                                Model model){
        try {
            Product product = this.productRestClient.createProduct(payload.title(), payload.details());
            return "redirect:/catalogue/products/%d".formatted(product.id());
        } catch (BadRequestException exception){
            model.addAttribute("payload", payload);
            model.addAttribute("errors", exception.getErrors());
            return "catalogue/products/new_product";
        }
    }
}

