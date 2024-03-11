package org.sbsa.deco.config;

import com.sbsa.deco.dto.CategoryDTO;
import com.sbsa.deco.dto.ItemDTO;
import com.sbsa.deco.dto.SubCategoryDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.sbsa.deco.entities.Category;
import org.sbsa.deco.entities.Item;
import org.sbsa.deco.entities.SubCategory;
import org.sbsa.deco.mappers.converters.ObjectsListToObjectsCountConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

//        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.typeMap(Category.class, CategoryDTO.class)
                .addMappings(new PropertyMap<Category, CategoryDTO>() {
                    @Override
                    protected void configure() {
                        using(new ObjectsListToObjectsCountConverter()).map(source.getSubCategories(), destination.getSubCategoriesCount());
                    }
                });
        modelMapper.typeMap(SubCategory.class, SubCategoryDTO.class)
                .addMappings(new PropertyMap<SubCategory, SubCategoryDTO>() {
                    @Override
                    protected void configure() {
                        using(new ObjectsListToObjectsCountConverter()).map(source.getItems(), destination.getItemsCounter());
                    }
                });
        modelMapper.typeMap(Item.class, ItemDTO.class)
                .addMappings(new PropertyMap<Item, ItemDTO>() {
                    @Override
                    protected void configure() {
                        using(new ObjectsListToObjectsCountConverter()).map(source.getItemImages(), destination.getItemImagesCounter());
                    }
                });

        return modelMapper;
    }
}
