package org.sbsa.deco.service;

import com.sbsa.deco.dto.CategoryDTO;
import com.sbsa.deco.dto.SubCategoryDTO;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SubCategoriesService {
    boolean saveSubCategory(SubCategoryDTO owner, CategoryDTO categoryDTO);

    boolean updateSubCategory(SubCategoryDTO owner, CategoryDTO categoryDTO);

    boolean deleteSubCategory(TableView<SubCategoryDTO> tbl, ObservableList<SubCategoryDTO> listOwners);

    Page<SubCategoryDTO> findAll();

    Page<SubCategoryDTO> searchSubCategory(String query, Pageable pageable);

    SubCategoryDTO findSubCategoryById(long id);

    Long count();

}
