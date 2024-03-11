package org.sbsa.deco.services;


import com.sbsa.deco.dto.SubCategoryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SubCategoryService extends CrudService<SubCategoryDTO, Long> {
    Page<SubCategoryDTO> findAllByCategory(long id, Pageable pageable);

    Page<SubCategoryDTO> findAllByCategory_Name(String name, Pageable pageable);

    Page<SubCategoryDTO> findAllByName(String name, Pageable pageable);
}
