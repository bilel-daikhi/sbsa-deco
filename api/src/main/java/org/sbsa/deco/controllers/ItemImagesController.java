package org.sbsa.deco.controllers;

import lombok.extern.slf4j.Slf4j;
import org.sbsa.deco.exceptions.domaine.ItemNotFoundException;
import org.sbsa.deco.services.ImageService;
import org.sbsa.deco.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/images")
@Slf4j
public class ItemImagesController {
    @Autowired
    private ImageService imageService;
    @Autowired
    private ItemService itemService;

    @GetMapping("/item/{id}")
    public ResponseEntity findItemImagesByItemId(@PathVariable long id)  throws ItemNotFoundException  {
    if(!itemService.existsById(id)) throw new ItemNotFoundException("Item with id "+id+" doesn't exist!");

    return new ResponseEntity<>(imageService.findItemImagesByItem_Id(id),HttpStatus.OK);

    }
}
