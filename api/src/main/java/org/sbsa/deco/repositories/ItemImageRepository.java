package org.sbsa.deco.repositories;

import org.sbsa.deco.entities.ItemImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemImageRepository extends JpaRepository<ItemImage, Long> {
    List<ItemImage> findItemImagesByItem_Id(long id);

}
