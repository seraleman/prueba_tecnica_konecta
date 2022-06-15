package com.seraleman.prueba_tecnica_java.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.seraleman.prueba_tecnica_java.helpers.IResponse;
import com.seraleman.prueba_tecnica_java.models.Product;
import com.seraleman.prueba_tecnica_java.services.IProductService;

@RestController
@RequestMapping("/product")
public class ProductRestController {

    @Autowired
    private IProductService productService;

    @Autowired
    private IResponse response;

    @GetMapping("/")
    public ResponseEntity<?> getCategories() {
        try {
            List<Product> products = productService.getProducts();
            if (products.isEmpty()) {
                return response.empty();
            }
            return response.list(products);
        } catch (DataAccessException e) {
            return response.errorDataAccess(e);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id) {
        try {
            Product product = productService.getProductById(id);
            if (product == null) {
                return response.notFound(id);
            }
            return response.found(product);
        } catch (DataAccessException e) {
            return response.errorDataAccess(e);
        }
    }

    @PostMapping("/")
    public ResponseEntity<?> createProduct(@RequestBody Product product) {
        try {
            return response.created(productService.saveProduct(product));
        } catch (DataAccessException e) {
            return response.errorDataAccess(e);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProductById(@RequestBody Product product, @PathVariable Long id) {
        try {
            Product currentProduct = productService.getProductById(id);
            if (currentProduct == null) {
                response.notFound(id);
            }
            currentProduct.setCategory(product.getCategory());
            currentProduct.setCreated(product.getCreated());
            currentProduct.setName(product.getName());
            currentProduct.setPrice(product.getPrice());
            currentProduct.setReference(product.getReference());
            currentProduct.setStock(product.getStock());
            currentProduct.setWeight(product.getWeight());

            return response.created(productService.saveProduct(currentProduct));
        } catch (DataAccessException e) {
            return response.errorDataAccess(e);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategoryById(@PathVariable Long id) {
        try {
            Product product = productService.getProductById(id);
            if (product == null) {
                response.notFound(id);
            }
            productService.deleteProductById(id);
            return response.deleted();
        } catch (DataAccessException e) {
            return response.errorDataAccess(e);
        }
    }

}
