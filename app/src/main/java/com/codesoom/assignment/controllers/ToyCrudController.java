package com.codesoom.assignment.controllers;

import com.codesoom.assignment.domain.Toy;
import com.codesoom.assignment.interfaces.ProductCrudController;
import com.codesoom.assignment.interfaces.ProductCrudService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tasks")
@CrossOrigin(origins = "http://localhost:3000")
public class ToyCrudController implements ProductCrudController {
    private final ProductCrudService service;

    public ToyCrudController(ProductCrudService service) {
        this.service = service;
    }

    @GetMapping
    @Override
    public List<Toy> list() {
        return service.showAll();
    }

    @Override
    public Optional<Toy> detail(Long id) {
        return service.showById(id);
    }
}
