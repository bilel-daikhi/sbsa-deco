package org.sbsa.deco.repositories;

import org.sbsa.deco.entities.SubCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubCategoryRepository extends JpaRepository<SubCategory, Long> {
    Page<SubCategory> findAllByCategory_Id(long id, Pageable pageable);

    Page<SubCategory> findAllByCategory_Name(String name, Pageable pageable);

    Page<SubCategory> findAllByName(String name, Pageable pageable);
}
