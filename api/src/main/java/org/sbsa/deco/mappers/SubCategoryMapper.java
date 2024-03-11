package org.sbsa.deco.mappers;


import com.sbsa.deco.dto.SubCategoryDTO;
import org.modelmapper.ModelMapper;
import org.sbsa.deco.entities.SubCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

@Component
public class SubCategoryMapper {
    @Autowired
    private ModelMapper modelMapper;


    public SubCategoryDTO subCategoryToSubCategoryDTO(SubCategory subCategory) {
        if (subCategory == null)
            return null;

        final SubCategoryDTO subCategoryDTO = modelMapper.map(subCategory, SubCategoryDTO.class);
        subCategoryDTO.add(
                Link.of("/api/v1/sub-categories/{id}").withRel("find-subCategory").withType("GET")
                        .expand(subCategoryDTO.getId()));
        return subCategoryDTO;
    }

    public SubCategory subCategoryDTOToSubCategory(SubCategoryDTO categoryDTO) {
        if (categoryDTO == null)
            return null;

        final SubCategory item = modelMapper.map(categoryDTO, SubCategory.class);

        return item;
    }
}