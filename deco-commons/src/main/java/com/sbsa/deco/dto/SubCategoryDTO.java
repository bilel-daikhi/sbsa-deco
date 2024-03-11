package com.sbsa.deco.dto;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Relation(collectionRelation = "subCategories", itemRelation = "subCategory")
public class SubCategoryDTO extends RepresentationModel<SubCategoryDTO> {

    private long id;
    private String name;
    private String created;
    private CategoryDTO category;
    private int itemsCounter ;

    @Builder
    public SubCategoryDTO(String name,String created,int itemsCounter) {
        this.name = name;
        this.created=created;
        this.itemsCounter=itemsCounter;

    }

    @Override
    public String toString() {
        return name;
    }
}
