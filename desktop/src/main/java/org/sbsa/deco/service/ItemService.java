package org.sbsa.deco.service;

import com.sbsa.deco.dto.ItemDTO;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import org.sbsa.deco.controllers.tv.ItemTV;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ItemService {
    boolean saveItem(ItemTV item, ObservableList<ItemTV> list);

    boolean updateItem(ItemTV item);

    boolean deleteItem(TableView<ItemTV> tbl, ObservableList<ItemTV> list);

    Page<ItemTV> findAll();

    Page<ItemTV> searchItems(String query, Pageable pageable);

    ItemDTO findItemById(long id);

    List<ItemDTO> findItemsByName(String name);


    List<ItemTV> getAllDiscountItems(Boolean isDescount);

    Long count();
    String getLandingImageByItem(long id);
    Image getImageItem(long id);


}
