package com.seraleman.prueba_tecnica_java.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.seraleman.prueba_tecnica_java.models.Category;
import com.seraleman.prueba_tecnica_java.repositories.ICategoryDao;

@Service
public class CategoryServiceImpl implements ICategoryService {

    @Autowired
    private ICategoryDao categoryDao;

    @Override
    public List<Category> getCategories() {
        return (List<Category>) categoryDao.findAll();
    }

    @Override
    public Category getCategoryById(Long id) {
        return categoryDao.findById(id).orElse(null);
    }

    @Override
    public Category saveCategory(Category category) {
        return categoryDao.save(category);
    }

    @Override
    public void deleteCategoryById(Long id) {
        categoryDao.deleteById(id);
    }

}
