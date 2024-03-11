package org.sbsa.deco.controllers;


import com.sbsa.deco.dto.ItemDTO;
import com.sbsa.deco.dto.SubCategoryDTO;
import org.sbsa.deco.exceptions.domaine.CategoryNotFoundException;
import org.sbsa.deco.exceptions.domaine.ItemNotFoundException;
import org.sbsa.deco.exceptions.domaine.SubCategoryNotFoundException;
import org.sbsa.deco.services.ItemService;
import org.sbsa.deco.services.SubCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/items")
public class ItemController {
    @Autowired
    private ItemService itemService;
    @Autowired
    private SubCategoryService subCategoryService;


    @PostMapping("/{subCategoryId}")
    public ResponseEntity saveItem(@Valid @RequestBody ItemDTO itemDTO, @PathVariable long subCategoryId) throws CategoryNotFoundException {
        if (!subCategoryService.existsById(subCategoryId))
            throw new CategoryNotFoundException("SubCategory with id: " + subCategoryId + " not found!");

        SubCategoryDTO categoryDTO = subCategoryService.findById(subCategoryId);
        itemDTO.setSubCategoryDTO(categoryDTO);

        ItemDTO savedItemDTO = itemService.save(itemDTO);
        return new ResponseEntity(itemDTO, HttpStatus.CREATED);
    }

    @PatchMapping
    public ResponseEntity updateItem(@Valid @RequestBody ItemDTO itemDTO) throws SubCategoryNotFoundException {
        if (!itemService.existsById(itemDTO.getId()))
            throw new SubCategoryNotFoundException("Item with id: " + itemDTO.getId() + " not found!");

        ItemDTO savedItemDTO = itemService.save(itemDTO);
        return new ResponseEntity(savedItemDTO, HttpStatus.OK);
    }


    @GetMapping("/sub-categories/{categoryId}")
    public ResponseEntity getItemsByCategory(@PathVariable long categoryId, Pageable pageable) throws SubCategoryNotFoundException {
        if (!subCategoryService.existsById(categoryId))
            throw new SubCategoryNotFoundException("SubCategory with id: " + categoryId + " not found!");

        return new ResponseEntity(itemService.findAllBySubCategoryId(categoryId, pageable), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity findAllItems(Pageable pageable) {
        return new ResponseEntity(itemService.findAll(pageable), HttpStatus.OK);
    }

    @GetMapping("/search/{query}")
    public ResponseEntity findItemsByName(@PathVariable String query, Pageable pageable) {

        return new ResponseEntity(itemService.findAllByName(query, pageable), HttpStatus.OK);
    }

    @GetMapping("/searchByCategoryName/{query}")
    public ResponseEntity findItemsByCategoryName(@PathVariable String query, Pageable pageable) {

        return new ResponseEntity(itemService.findAllBySubCategory_Name(query, pageable), HttpStatus.OK);
    }

    @GetMapping("/discount/{isDescount}")
    public ResponseEntity findItemsByDiscount(@PathVariable int isDescount, Pageable pageable) {

        return new ResponseEntity(itemService.findAllByDiscount(isDescount == 1, pageable), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteItem(@PathVariable long id) throws ItemNotFoundException {
        if (!itemService.existsById(id))
            throw new ItemNotFoundException("Item with id: " + id + " not found!");
        itemService.deleteById(id);
        return new ResponseEntity(HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity findItemById(@PathVariable long id) throws ItemNotFoundException {
        if (!itemService.existsById(id))
            throw new ItemNotFoundException("Item with id: " + id + " not found!");
        ItemDTO itemDTO=itemService.findById(id);
        return new ResponseEntity(itemDTO,HttpStatus.OK);
    }

    @GetMapping("/count")
    public ResponseEntity count() {

        return new ResponseEntity(itemService.count(), HttpStatus.OK);
    }
}
