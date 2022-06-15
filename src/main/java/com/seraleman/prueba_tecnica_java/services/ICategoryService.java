package com.seraleman.prueba_tecnica_java.services;

import java.util.List;

import com.seraleman.prueba_tecnica_java.models.Category;

public interface ICategoryService {

    List<Category> getCategories();

    Category getCategoryById(Long id);

    Category saveCategory(Category category);

    void deleteCategoryById(Long id);

}
