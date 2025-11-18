package com.yotsume.managerapp.controller;

import com.yotsume.managerapp.client.BadRequestException;
import com.yotsume.managerapp.client.ProductRestClient;
import com.yotsume.managerapp.controller.payload.UpdateProductPayload;
import com.yotsume.managerapp.entity.Product;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.Locale;
import java.util.NoSuchElementException;

@Controller
@RequiredArgsConstructor
@RequestMapping("catalogue/products/{productId:\\d+}")
public class ProductController {

    private final ProductRestClient productRestClient;

    private final MessageSource messageSource;

    @ModelAttribute("product")
    public Product product(@PathVariable("productId") int productId){
        return this.productRestClient.findProduct(productId).orElseThrow(
                () -> new NoSuchElementException("catalogue.errors.product.notFound")
        );
    }

    @GetMapping()
    public String getProduct() {
        return "catalogue/products/product";
    }

    @GetMapping("edit")
    public String getProductEditPage(){
        return "catalogue/products/edit";
    }

    @PostMapping("edit")
    public String updateProduct(
            @ModelAttribute(value = "product", binding = false) Product product,
            UpdateProductPayload payload,
            Model model){

        try {
            this.productRestClient.updateProduct(product.id(), payload.title(), payload.details());
            return "redirect:/catalogue/products/%d".formatted(product.id());

        } catch (BadRequestException e) {
            model.addAttribute("payload", payload);
            model.addAttribute("errors",e.getErrors());
            return "catalogue/products/edit";
            }
        }

    @PostMapping("delete")
    public String DeleteProduct(@ModelAttribute("product") Product product) {
        this.productRestClient.deleteProduct(product.id());
        return "redirect:/catalogue/products/list";
    }

    @ExceptionHandler(NoSuchElementException.class)
    public String handleNoSuchElementException(NoSuchElementException e,
                                               Model model,
                                               HttpServletResponse response,
                                               Locale locale) {
        response.setStatus(HttpStatus.NOT_FOUND.value());
        model.addAttribute("error",
                this.messageSource.getMessage(e.getMessage(),
                        new Object[0],
                        e.getMessage(),
                        locale));
        return "errors/404";
    }
}