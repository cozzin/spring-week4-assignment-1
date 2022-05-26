package com.codesoom.assignment.service;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.dto.ProductResponse;
import com.codesoom.assignment.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class ProductServiceTest {

    private ProductRepository productRepository;
    private ProductService productService;

    private Product product1;
    private Product product2;

    private List<Product> products;

    @BeforeEach
    void setUp() {
        productRepository = mock(ProductRepository.class);
        productService = new ProductService(productRepository);

        product1 = Product.builder()
                .id(1L)
                .name("name1")
                .price(1)
                .imageUrl("url1")
                .maker("maker1")
                .build();

        product2 = Product.builder()
                .id(2L)
                .name("name2")
                .price(2)
                .imageUrl("url2")
                .maker("maker2")
                .build();

        products = new ArrayList<>();
    }

    @Test
    @DisplayName("상품을 생성한다.")
    void createProduct() {
        given(productRepository.save(any(Product.class)))
                .will(invocation -> invocation.<Product>getArgument(0));

        productService.createProduct(product1);

        verify(productRepository).save(any(Product.class));
    }

    @Test
    @DisplayName("모든 상품을 조회한다.")
    void getProducts() {
        products.add(product1);
        products.add(product2);

        given(productRepository.findAll()).willReturn(products);

        List<ProductResponse> products = productService.getProducts();

        assertThat(products).hasSize(2);

        verify(productRepository).findAll();
    }
}
