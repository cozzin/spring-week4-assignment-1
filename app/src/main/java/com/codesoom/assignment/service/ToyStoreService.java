package com.codesoom.assignment.service;

import com.codesoom.assignment.ProductNotFoundException;
import com.codesoom.assignment.model.Product;
import com.codesoom.assignment.repository.ToyStoreRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ToyStoreService {

    private ToyStoreRepository toyStoreRepository;

    public ToyStoreService(ToyStoreRepository toyStoreRepository) {
        this.toyStoreRepository = toyStoreRepository;
    }

    public Product save(Product product) {
        return toyStoreRepository.save(product);
    }

    public List<Product> getProducts() {
        return toyStoreRepository.findAll();
    }

    public Product getProduct(long productId) {
        return toyStoreRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException(productId));
    }
}
