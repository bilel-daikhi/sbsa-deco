package org.sbsa.deco.services.impl;


import com.sbsa.deco.dto.CategoryDTO;
import lombok.extern.slf4j.Slf4j;
import org.sbsa.deco.entities.Category;
import org.sbsa.deco.mappers.CategoryMapper;
import org.sbsa.deco.repositories.CategoryRepository;
import org.sbsa.deco.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Page<CategoryDTO> findAll(Pageable pageable) {

        log.info("Find all called!");
        Page<Category> categories = categoryRepository.findAll(pageable);
        List<CategoryDTO> categoryDTOS = categories.getContent().stream().map(category -> categoryMapper.categoryToCategoryDTO(category)).collect(Collectors.toList());
        return new PageImpl<>(categoryDTOS, pageable, categories.getTotalElements());
    }

    @Override
    public CategoryDTO findById(Long id) {

        return categoryMapper.categoryToCategoryDTO(categoryRepository.getOne(id));
    }


    @Override
    public boolean existsById(Long id) {
        return categoryRepository.existsById(id);
    }

    @Override
    public CategoryDTO save(CategoryDTO object) {
        Category category = categoryRepository.save(categoryMapper.categoryDTOToCategory(object));
        return categoryMapper.categoryToCategoryDTO(category);
    }

    @Override
    public long count() {
        return categoryRepository.count();
    }

    @Override
    public void delete(CategoryDTO object) {
        categoryRepository.delete(categoryMapper.categoryDTOToCategory(object));
    }

    @Override
    public void deleteById(Long id) {
        categoryRepository.deleteById(id);

    }

    @Override
    public Page<CategoryDTO> findAllByName(String name, Pageable pageable) {
        Page<Category> categories = categoryRepository.findAllByName(name, pageable);
        List<CategoryDTO> categoryDTOS = categories.getContent()
                .stream().map(category -> categoryMapper.categoryToCategoryDTO(category)).collect(Collectors.toList());

        return new PageImpl<>(categoryDTOS, pageable, categories.getTotalElements());
    }

}
