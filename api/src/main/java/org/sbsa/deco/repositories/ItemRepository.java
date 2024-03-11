package org.sbsa.deco.repositories;

import org.sbsa.deco.entities.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    Page<Item> findAllBySubCategory_Id(long id, Pageable pageable);

    Page<Item> findAllByName(String name, Pageable pageable);

    Page<Item> findAllBySubCategory_Name(String name, Pageable pageable);

    Page<Item> findAllByDiscount(Boolean isDescount, Pageable pageable);

}
