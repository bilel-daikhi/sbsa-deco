package org.sbsa.deco.controllers;

import com.sbsa.deco.dto.SubCategoryDTO;
import lombok.extern.slf4j.Slf4j;
import org.sbsa.deco.exceptions.domaine.CategoryNotFoundException;
import org.sbsa.deco.exceptions.domaine.SubCategoryNotFoundException;
import org.sbsa.deco.services.CategoryService;
import org.sbsa.deco.services.SubCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Slf4j
@RequestMapping("/api/v1/sub-categories")
public class SubCategoryController {
    @Autowired
    private SubCategoryService subCategoryService;
    @Autowired
    private CategoryService categoryService;

    @PostMapping("/{categoryId}")
    public ResponseEntity saveSubCategory(@Valid @RequestBody SubCategoryDTO subCategoryDTO, @PathVariable long categoryId) throws CategoryNotFoundException {
        if (!categoryService.existsById(categoryId))
            throw new CategoryNotFoundException("Category with id: " + categoryId + " not found!");

//        CategoryDTO categoryDTO = categoryService.findById(categoryId);
//        subCategoryDTO.setCategory(categoryDTO);

        SubCategoryDTO savedSubCategoryDTO = subCategoryService.save(subCategoryDTO);
        return new ResponseEntity(savedSubCategoryDTO, HttpStatus.CREATED);
    }

    @PatchMapping
    public ResponseEntity updateSubCategory(@Valid @RequestBody SubCategoryDTO subCategoryDTO) throws SubCategoryNotFoundException {
        if (!subCategoryService.existsById(subCategoryDTO.getId()))
            throw new SubCategoryNotFoundException("SubCategory with id: " + subCategoryDTO.getId() + " not found!");

        SubCategoryDTO savedSubCategoryDTO = subCategoryService.save(subCategoryDTO);
        return new ResponseEntity(savedSubCategoryDTO, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getAllSubCategories(Pageable pageable) {
        return new ResponseEntity(subCategoryService.findAll(pageable), HttpStatus.OK);
    }

    @GetMapping("/searchByName/{query}")
    public ResponseEntity getAllSubCategoriesByName(String query, Pageable pageable) {
        return new ResponseEntity(subCategoryService.findAllByName(query, pageable), HttpStatus.OK);
    }

    @GetMapping("/searchByCategoryName/{query}")
    public ResponseEntity getAllSubCategoriesByCategoryName(String query, Pageable pageable) {
        return new ResponseEntity(subCategoryService.findAllByCategory_Name(query, pageable), HttpStatus.OK);
    }

    @GetMapping("/categories/{categoryId}")
    public ResponseEntity getSubCategoriesByCategory(@PathVariable long categoryId, Pageable pageable) throws CategoryNotFoundException {
        if (!categoryService.existsById(categoryId))
            throw new CategoryNotFoundException("Category with id: " + categoryId + " not found!");

        return new ResponseEntity(subCategoryService.findAllByCategory(categoryId, pageable), HttpStatus.OK);
    }
    @GetMapping("/{categoryId}")
    public ResponseEntity findSubCategoriesById(@PathVariable long categoryId) throws CategoryNotFoundException {
        if (!subCategoryService.existsById(categoryId))
            throw new CategoryNotFoundException("Sub Category with id: " + categoryId + " not found!");

        return new ResponseEntity(subCategoryService.findById(categoryId), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteSubCategory(@PathVariable long id) throws SubCategoryNotFoundException {
        if (!subCategoryService.existsById(id))
            throw new SubCategoryNotFoundException("SubCategory with id: " + id + " not found!");
        subCategoryService.deleteById(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/count")
    public ResponseEntity count() {

        return new ResponseEntity(subCategoryService.count(), HttpStatus.OK);
    }
}
