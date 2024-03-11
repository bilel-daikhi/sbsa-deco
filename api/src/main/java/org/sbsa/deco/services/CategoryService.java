package org.sbsa.deco.services;


import com.sbsa.deco.dto.CategoryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryService extends CrudService<CategoryDTO, Long> {
    Page<CategoryDTO> findAllByName(String name, Pageable pageable);
}
