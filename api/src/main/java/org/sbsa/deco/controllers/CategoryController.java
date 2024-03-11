package org.sbsa.deco.controllers;


import com.sbsa.deco.dto.CategoryDTO;
import org.sbsa.deco.exceptions.domaine.CategoryNotFoundException;
import org.sbsa.deco.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public ResponseEntity saveCategory(@Valid @RequestBody CategoryDTO subCategoryDTO) {

        CategoryDTO savedSubCategoryDTO = categoryService.save(subCategoryDTO);
        return new ResponseEntity(savedSubCategoryDTO, HttpStatus.CREATED);
    }

    @PatchMapping
    public ResponseEntity updateCategory(@Valid @RequestBody CategoryDTO categoryDTO) throws CategoryNotFoundException {
        if (!categoryService.existsById(categoryDTO.getId()))
            throw new CategoryNotFoundException("Category with id: " + categoryDTO.getId() + " not found!");

        CategoryDTO savedCategoryDTO = categoryService.save(categoryDTO);
        return new ResponseEntity(savedCategoryDTO, HttpStatus.OK);
    }


    @GetMapping
    public ResponseEntity getCategories(Pageable pageable) {
        return new ResponseEntity(categoryService.findAll(pageable), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity findCategoryById(@PathVariable long id) throws CategoryNotFoundException {
        if (!categoryService.existsById(id))
            throw new CategoryNotFoundException("Category with id: " + id + " not found!");
        return new ResponseEntity(categoryService.findById(id), HttpStatus.OK);
    }

    @GetMapping("/search/{query}")
    public ResponseEntity findCategoriesByName(@PathVariable String query, Pageable pageable) {

        return new ResponseEntity(categoryService.findAllByName(query, pageable), HttpStatus.OK);
    }

    @GetMapping("/count")
    public ResponseEntity count() {

        return new ResponseEntity(categoryService.count(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteSubCategory(@PathVariable long id) throws CategoryNotFoundException {
        if (!categoryService.existsById(id))
            throw new CategoryNotFoundException("Category with id: " + id + " not found!");
        categoryService.deleteById(id);
        return new ResponseEntity(HttpStatus.OK);
    }

}
