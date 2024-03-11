package org.sbsa.deco.service;

import com.sbsa.deco.dto.CategoryDTO;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryService {
    boolean saveCategory(CategoryDTO customer, ObservableList<CategoryDTO> listCustomers);

    boolean updateCategory(CategoryDTO customer);

    boolean deleteCategory(TableView<CategoryDTO> tbl, ObservableList<CategoryDTO> listCustomers);

    Page<CategoryDTO> findAllCategory();

    Page<CategoryDTO> searchCategory(String query, Pageable pageable);

    CategoryDTO findCategory(long customerId);


    Long count();

}
