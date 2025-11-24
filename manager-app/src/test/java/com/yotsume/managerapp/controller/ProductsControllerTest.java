package com.yotsume.managerapp.controller;

import com.yotsume.managerapp.client.BadRequestException;
import com.yotsume.managerapp.client.ProductRestClient;
import com.yotsume.managerapp.controller.payload.NewProductPayload;
import com.yotsume.managerapp.entity.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.ConcurrentModel;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductsControllerTest {

    @Mock
    ProductRestClient productRestClient;
    @InjectMocks
    ProductsController productsController;

    //@BeforeEach - перед каждым тестовым методом,
    // чтобы получить новый экземпляр тестируемого объекта

    @Test
    // @DisplayName("createProduct create new product and redirect to product page")
    void createProduct_RequestIsValid_ReturnsRedirectionProductPage(){
        // given
        var payload = new NewProductPayload("new product", "disr");
        var model = new ConcurrentModel();

        Mockito.doReturn(new Product(1,"new product", "disr"))
                .when(productRestClient)
                .createProduct("new product", "disr");

        // when
        var result = this.productsController.CreateProduct(payload, model);

        // then
        assertEquals("redirect:/catalogue/products/1", result);
        Mockito.verify(this.productRestClient)
                .createProduct("new product", "disr");
        Mockito.verifyNoMoreInteractions(this.productRestClient);
    }

    @Test
    void createProduct_RequestIsInvalid_ReturnsRedirectionErrorPage(){
        var payload = new NewProductPayload("  ", null);
        var model = new ConcurrentModel();

        Mockito.doThrow(new BadRequestException(List.of("error 1", "error 2")))
        .when(productRestClient)
        .createProduct("  ", null);

        var result = this.productsController.CreateProduct(payload, model);

        assertEquals("catalogue/products/new_product", result);
        assertEquals(payload, model.getAttribute("payload"));
        assertEquals(List.of("error 1", "error 2"), model.getAttribute("errors"));

        Mockito.verify(this.productRestClient).createProduct("  ", null);
        Mockito.verifyNoMoreInteractions(this.productRestClient);
    }
}