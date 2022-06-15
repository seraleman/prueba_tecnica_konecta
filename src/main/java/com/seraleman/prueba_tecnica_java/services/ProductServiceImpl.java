package com.seraleman.prueba_tecnica_java.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.seraleman.prueba_tecnica_java.models.Product;
import com.seraleman.prueba_tecnica_java.repositories.IProductDao;

public class ProductServiceImpl implements IProductService {

    @Autowired
    private IProductDao productDao;

    @Override
    public List<Product> getProducts() {
        return (List<Product>) productDao.findAll();
    }

    @Override
    public Product getProductById(Long id) {
        return productDao.findById(id).orElse(null);
    }

    @Override
    public Product saveProduct(Product product) {
        return productDao.save(product);
    }

    @Override
    public void deleteProductById(Long id) {
        productDao.deleteById(id);
    }

}
