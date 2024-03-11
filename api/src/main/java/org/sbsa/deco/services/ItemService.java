package org.sbsa.deco.services;


import com.sbsa.deco.dto.ItemDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemService extends CrudService<ItemDTO, Long> {
    Page<ItemDTO> findAllBySubCategoryId(long id, Pageable pageable);

    Page<ItemDTO> findAllByName(String name, Pageable pageable);

    Page<ItemDTO> findAllBySubCategory_Name(String name, Pageable pageable);

    Page<ItemDTO> findAllByDiscount(Boolean isDescount, Pageable pageable);
}
