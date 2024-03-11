package org.sbsa.deco.mappers;


import com.sbsa.deco.dto.ItemDTO;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.sbsa.deco.entities.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ItemMapper {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private SubCategoryMapper subCategoryMapper;


    public ItemDTO itemToItemDTO(Item item) {
        if (item == null)
            return null;

        final ItemDTO itemDTO = modelMapper.map(item, ItemDTO.class);
        if(item.getSubCategory()!=null)
            itemDTO.setSubCategoryDTO(subCategoryMapper.subCategoryToSubCategoryDTO(item.getSubCategory()));
        itemDTO.add(
                Link.of("/api/v1/items/{id}").withRel("find-item").withType("GET")
                        .expand(itemDTO.getId()));
        log.info("item mapper: "+ itemDTO);
        return itemDTO;
    }

    public Item itemDTOToitem(ItemDTO itemDTO) {
        if (itemDTO == null)
            return null;

        final Item item = modelMapper.map(itemDTO, Item.class);

        return item;
    }
}
