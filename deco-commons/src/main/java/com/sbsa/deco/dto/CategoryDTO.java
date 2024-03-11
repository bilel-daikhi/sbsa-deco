package com.sbsa.deco.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor

@Relation(collectionRelation = "categories", itemRelation = "category")
public class CategoryDTO extends RepresentationModel<SubCategoryDTO> {

    private long id;
    @NotBlank(message = "Name should not be empty!")
    @Size(min = 2, message = "Size should be more than 2 characters!")
    private String name;
    private String created;
    private int subCategoriesCount ;

    @Builder
    public CategoryDTO(String name,int subCategoriesCount,String created) {
        this.name = name;
        this.created=created;
        this.subCategoriesCount = subCategoriesCount;
    }

    @Override
    public String toString() {
        return name;
    }
}
