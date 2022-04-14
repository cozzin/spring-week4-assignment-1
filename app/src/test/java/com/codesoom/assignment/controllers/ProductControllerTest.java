package com.codesoom.assignment.controllers;


import com.codesoom.assignment.application.ProductService;
import com.codesoom.assignment.dto.ProductDto;
import com.codesoom.assignment.models.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DisplayName("ProductController 에서")
class ProductControllerTest {
    private static final String PRODUCT_NAME = "상품1";
    private static final String PRODUCT_MAKER = "메이커1";
    private static final Integer PRODUCT_PRICE = 100000;
    private static final String PRODUCT_IMAGE_URL = "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Ft1.daumcdn.net%2Fcfile%2Ftistory%2F9941A1385B99240D2E";

    @Autowired
    private ProductService productService;
    private ProductController productController;

    @BeforeEach
    void setUp() {
        productController = new ProductController(productService);
        productService.deleteAll();
    }

    /**
     * 여러개의 Product 를 생성해 등록합니다.
     * @param createProuctSize 생성할 Product의 갯수
     */
    void createProducts(int createProuctSize) {
        for (int i = 0; i < createProuctSize; i++) {
            ProductDto productDto = new ProductDto
                    .Builder(PRODUCT_PRICE, PRODUCT_NAME)
                    .maker(PRODUCT_MAKER)
                    .imageUrl(PRODUCT_IMAGE_URL)
                    .build();
            productService.createProduct(productDto);
        }
    }

    @Nested
    @DisplayName("list 메소드 는")
    class Describe_of_list {
        final int createProductsize = 3;

        @BeforeEach
        void setUp() {
            createProducts(createProductsize);
        }

        @Nested
        @DisplayName("Product 객체가 없을 경우")
        class Context_with_empty_list {

            @BeforeEach
            void setUp() {
                productService.deleteAll();
            }

            @Test
            @DisplayName("빈 리스트를 반환한다")
            void it_return_empty_list() {
                List<Product> products = productController.list();

                assertThat(products).isEmpty();
            }

        }

    }

}