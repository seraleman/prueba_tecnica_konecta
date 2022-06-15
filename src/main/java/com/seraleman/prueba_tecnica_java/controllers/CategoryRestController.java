package com.seraleman.prueba_tecnica_java.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.seraleman.prueba_tecnica_java.helpers.IResponse;
import com.seraleman.prueba_tecnica_java.models.Category;
import com.seraleman.prueba_tecnica_java.services.ICategoryService;

@RestController
@RequestMapping("/category")
public class CategoryRestController {

    @Autowired
    private ICategoryService categoryService;

    @Autowired
    private IResponse response;

    @GetMapping("/")
    public ResponseEntity<?> getCategories() {
        try {
            List<Category> categories = categoryService.getCategories();
            if (categories.isEmpty()) {
                return response.empty();
            }
            return response.list(categories);
        } catch (DataAccessException e) {
            return response.errorDataAccess(e);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable Long id) {
        try {
            Category category = categoryService.getCategoryById(id);
            if (category == null) {
                return response.notFound(id);
            }
            return response.found(category);
        } catch (DataAccessException e) {
            return response.errorDataAccess(e);
        }
    }

    @PostMapping("/")
    public ResponseEntity<?> createCategory(@Valid @RequestBody Category category,
            BindingResult result) {
        if (result.hasErrors()) {
            return response.invalidObject(result);
        }
        try {
            return response.created(categoryService.saveCategory(category).getId());
        } catch (DataAccessException e) {
            return response.errorDataAccess(e);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategoryById(@Valid @RequestBody Category category,
            BindingResult result,
            @PathVariable Long id) {

        if (result.hasErrors()) {
            return response.invalidObject(result);
        }
        try {
            Category currentCategory = categoryService.getCategoryById(id);
            if (currentCategory == null) {
                response.notFound(id);
            }
            currentCategory.setName(category.getName());
            categoryService.saveCategory(currentCategory);
            return response.updated();
        } catch (DataAccessException e) {
            return response.errorDataAccess(e);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategoryById(@PathVariable Long id) {
        try {
            Category category = categoryService.getCategoryById(id);
            if (category == null) {
                return response.notFound(id);
            }
            categoryService.deleteCategoryById(id);
            return response.deleted();
        } catch (DataAccessException e) {
            return response.errorDataAccess(e);
        }
    }
}
