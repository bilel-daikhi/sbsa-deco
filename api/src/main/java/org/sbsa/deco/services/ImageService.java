package org.sbsa.deco.services;

import com.sbsa.deco.dto.ItemImageDTO;
import org.sbsa.deco.entities.ItemImage;

import java.util.List;

public interface ImageService extends CrudService<ItemImageDTO,Long> {
    List<ItemImage> findItemImagesByItem_Id(long id);
}
