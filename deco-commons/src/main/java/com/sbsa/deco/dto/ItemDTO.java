package com.sbsa.deco.dto;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@ToString
@Relation(collectionRelation = "items", itemRelation = "item")
public class ItemDTO extends RepresentationModel<ItemDTO> {

    private long id;
    @NotBlank(message = "Name should not be empty!")
    @Size(min = 2, message = "Size should be more than 2 characters!")
    private String name;
    private String created;
    private String reference;
    private String description;
    @Min(value = 1, message = "Price should be more than 1!")
    private double price;
    private String landingImage;
    private boolean discount;
    private double discountAmount;
    private float rating;
    private List<String> materials=new ArrayList<>();
    //    @NotEmpty
    private int itemImagesCounter;
    private SubCategoryDTO subCategoryDTO;

    @Builder
    public ItemDTO(long id, String name, double price, boolean discount, double discountAmount, float rating, String description, String created,String reference,String landingImage) {
        this.id = id;
        this.reference=reference;
        this.landingImage=landingImage;
        this.name = name;
        this.price = price;
        this.discount = discount;
        this.discountAmount = discountAmount;
        this.rating = rating;
        this.description = description;
        this.created = created;
    }
}
