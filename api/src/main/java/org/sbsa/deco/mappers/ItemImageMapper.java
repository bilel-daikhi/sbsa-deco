package org.sbsa.deco.mappers;


import com.sbsa.deco.dto.ItemImageDTO;
import org.modelmapper.ModelMapper;
import org.sbsa.deco.entities.ItemImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

@Component
public class ItemImageMapper {
    @Autowired
    private ModelMapper modelMapper;


    public ItemImageDTO ItemImageToItemImageDTO(ItemImage image) {
        if (image == null)
            return null;

        final ItemImageDTO imageDTO = modelMapper.map(image, ItemImageDTO.class);
        imageDTO.add(
                Link.of("/api/v1/images/{id}").withRel("find-image").withType("GET")
                        .expand(imageDTO.getId()));

        return imageDTO;
    }

    public ItemImage itemImageDTOToItemImage(ItemImageDTO imageDTO) {
        if (imageDTO == null)
            return null;

        final ItemImage image = modelMapper.map(imageDTO, ItemImage.class);

        return image;
    }
}
