package com.sbsa.deco.dto;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;


@Getter
@Setter
@NoArgsConstructor
@ToString
@Relation(collectionRelation = "itemImages", itemRelation = "itemImage")
public class ItemImageDTO extends RepresentationModel<ItemImageDTO> {

    private long id;
    private String link;
    private String created;
    private String description;

    private ItemDTO item;

    @Builder
    public ItemImageDTO(String link, String description) {
        this.link = link;
        this.description = description;
    }
}
