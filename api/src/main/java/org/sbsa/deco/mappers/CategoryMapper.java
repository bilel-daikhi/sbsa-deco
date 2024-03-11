package org.sbsa.deco.mappers;

import com.sbsa.deco.dto.CategoryDTO;
import org.modelmapper.ModelMapper;
import org.sbsa.deco.entities.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {
    @Autowired
    private ModelMapper modelMapper;


    public CategoryDTO categoryToCategoryDTO(Category category) {
        if (category == null)
            return null;

        final CategoryDTO categoryDTO = modelMapper.map(category, CategoryDTO.class);
        categoryDTO.add(
                Link.of("/api/v1/categories/{id}").withRel("find-category").withType("GET")
                        .expand(categoryDTO.getId()));

        return categoryDTO;
    }

    public Category categoryDTOToCategory(CategoryDTO categoryDTO) {
        if (categoryDTO == null)
            return null;

        final Category category = modelMapper.map(categoryDTO, Category.class);

        return category;
    }
}
