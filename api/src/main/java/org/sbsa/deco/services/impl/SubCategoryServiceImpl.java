package org.sbsa.deco.services.impl;


import com.sbsa.deco.dto.SubCategoryDTO;
import lombok.extern.slf4j.Slf4j;
import org.sbsa.deco.entities.SubCategory;
import org.sbsa.deco.mappers.SubCategoryMapper;
import org.sbsa.deco.repositories.SubCategoryRepository;
import org.sbsa.deco.services.SubCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
@Slf4j
public class SubCategoryServiceImpl implements SubCategoryService {
    @Autowired
    private SubCategoryMapper categoryMapper;

    @Autowired
    private SubCategoryRepository subCategoryRepository;

    @Override
    public Page<SubCategoryDTO> findAll(Pageable pageable) {
        log.info("Find all called!");
        Page<SubCategory> categories = subCategoryRepository.findAll(pageable);
        List<SubCategoryDTO> categoryDTOS = categories.getContent().stream().map(category -> categoryMapper.subCategoryToSubCategoryDTO(category)).collect(Collectors.toList());

        return new PageImpl<>(categoryDTOS, pageable, categories.getTotalElements());

    }

    @Override
    public Page<SubCategoryDTO> findAllByCategory(long id, Pageable pageable) {
        log.info("Find all called!");
        Page<SubCategory> categories = subCategoryRepository.findAllByCategory_Id(id, pageable);
        List<SubCategoryDTO> categoryDTOS = categories.getContent().stream().map(category -> categoryMapper.subCategoryToSubCategoryDTO(category)).collect(Collectors.toList());

        return new PageImpl<>(categoryDTOS, pageable, categories.getTotalElements());

    }

    @Override
    public Page<SubCategoryDTO> findAllByCategory_Name(String name, Pageable pageable) {
        Page<SubCategory> categories = subCategoryRepository.findAllByCategory_Name(name, pageable);
        List<SubCategoryDTO> categoryDTOS = categories.getContent().stream().map(category -> categoryMapper.subCategoryToSubCategoryDTO(category)).collect(Collectors.toList());

        return new PageImpl<>(categoryDTOS, pageable, categories.getTotalElements());
    }

    @Override
    public Page<SubCategoryDTO> findAllByName(String name, Pageable pageable) {
        Page<SubCategory> categories = subCategoryRepository.findAllByName(name, pageable);
        List<SubCategoryDTO> categoryDTOS = categories.getContent().stream().map(category -> categoryMapper.subCategoryToSubCategoryDTO(category)).collect(Collectors.toList());

        return new PageImpl<>(categoryDTOS, pageable, categories.getTotalElements());
    }

    @Override
    public SubCategoryDTO findById(Long id) {
        SubCategory subCategory = subCategoryRepository.getOne(id);
        return categoryMapper.subCategoryToSubCategoryDTO(subCategory);
    }


    @Override
    public boolean existsById(Long id) {
        return subCategoryRepository.existsById(id);
    }

    @Override
    public SubCategoryDTO save(SubCategoryDTO object) {
        SubCategory subCategory = categoryMapper.subCategoryDTOToSubCategory(object);
        return categoryMapper.subCategoryToSubCategoryDTO(subCategory);
    }

    @Override
    public long count() {
        return subCategoryRepository.count();
    }

    @Override
    public void delete(SubCategoryDTO object) {
        subCategoryRepository.delete(categoryMapper.subCategoryDTOToSubCategory(object));

    }

    @Override
    public void deleteById(Long id) {
        subCategoryRepository.deleteById(id);

    }
}
