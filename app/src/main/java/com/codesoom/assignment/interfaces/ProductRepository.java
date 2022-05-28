package com.codesoom.assignment.interfaces;

import com.codesoom.assignment.domain.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {

    List<Product> findAll();

    Optional<Product> findById(Long id);

    Product save(Product product);

    Product update(Product product);

    void delete(Long id);
}
