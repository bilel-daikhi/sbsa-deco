package org.sbsa.deco.mapper;

import com.sbsa.deco.dto.ItemDTO;
import org.sbsa.deco.controllers.tv.ItemTV;
import org.springframework.stereotype.Component;

@Component
public class ItemMapper {
    public ItemTV itemDTOToItemTV(ItemDTO itemDTO) {
        if (itemDTO == null)
            throw new RuntimeException();
        ItemTV itemTV = ItemTV.builder()
                .id(itemDTO.getId())
                .name(itemDTO.getName()).price(itemDTO.getPrice())
                .reference(itemDTO.getReference())
                .created(itemDTO.getCreated()).description(itemDTO.getDescription())
                .discount(itemDTO.isDiscount()).discountAmount(itemDTO.getDiscountAmount())
                .build();
        if (itemDTO.getSubCategoryDTO() != null) {
            itemTV.setSubCategoryId(itemDTO.getSubCategoryDTO().getId());
            itemTV.setSubCategory(itemDTO.getSubCategoryDTO().getName());
        }
        return itemTV;
    }

    public ItemDTO itemTVToItemDTO(ItemTV itemTV) {
        if (itemTV == null)
            throw new RuntimeException();
        ItemDTO itemDTO = ItemDTO.builder()
                .id(itemTV.getId())
                .price(itemTV.getPrice())
                .discount(itemTV.isDiscount())
                .name(itemTV.getName())
                .description(itemTV.getDescription())
                .created(itemTV.getCreated())
                .build();

        return itemDTO;
    }
}
