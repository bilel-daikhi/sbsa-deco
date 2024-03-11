package org.sbsa.deco.data;

import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;
import org.sbsa.deco.entities.Category;
import org.sbsa.deco.entities.Item;
import org.sbsa.deco.entities.ItemImage;
import org.sbsa.deco.entities.SubCategory;
import org.sbsa.deco.repositories.CategoryRepository;
import org.sbsa.deco.repositories.ItemImageRepository;
import org.sbsa.deco.repositories.ItemRepository;
import org.sbsa.deco.repositories.SubCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Component
@Slf4j
@Profile({"dev","prod"})
public class InitData implements CommandLineRunner {
    @Autowired
    private Faker faker;

    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private ItemImageRepository itemImageRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private SubCategoryRepository subCategoryRepository;


    @Override
    public void run(String... args) throws Exception {
        log.info("Init fake data to database!");
        createRecords();

        setRelations();
    }

    private void createRecords() {
        IntStream.range(0, 100).forEach(value ->
                saveItem(Item.builder().
                        name(faker.commerce().productName())
                        .reference(faker.regexify("\\d{3}[0-9A-Z]{8}"))
                        .price(Float.parseFloat(faker.commerce().price()))
                        .discount(faker.random().nextBoolean())
                        .discountAmount(faker.random().nextInt(10, 30))
                        .landingImage("https://picsum.photos/" + faker.random().nextInt(1, 200))
                        .description(faker.lorem().paragraph(15))
                        .rating(faker.random().nextInt(1, 5))
                        .materials(constructMaterials())
                        .build()));
        IntStream.range(0, 300).forEach(value -> saveImage(ItemImage.builder()
                .description(faker.lorem().paragraph(15))
                .link("https://picsum.photos/" + faker.random().nextInt(1, 200))


                .build()));


        IntStream.range(0, 5).forEach(value -> saveCategory(Category.builder()
                .name(faker.cat().name())
                .build()));
        IntStream.range(0, 10).forEach(value -> saveSubCategory(SubCategory.builder()
                .name(faker.cat().name())
                .build()));
    }

    public void setRelations() {
        int indexSubCategory = 0;
        int indexItems = 0;
        int indexImages = 0;
        List<Item> listItems = itemRepository.findAll();
        List<Category> categoryList = categoryRepository.findAll();
        List<SubCategory> subCategories = subCategoryRepository.findAll();
        List<ItemImage> images = itemImageRepository.findAll();

        for (Category category : categoryList) {

            List<SubCategory> firstTen = subCategories.subList(indexSubCategory, indexSubCategory + 2);
            firstTen.stream().forEach(subCategory -> {
                subCategory.setCategory(category);
                subCategoryRepository.save(subCategory);
            });
            indexSubCategory = indexSubCategory + 2;

        }
        for (SubCategory subCategory : subCategories) {
            List<Item> firstTen = listItems.subList(indexItems, indexItems + 10);
            firstTen.stream().forEach(item -> {
                item.setSubCategory(subCategory);
                itemRepository.save(item);
            });
            indexItems = indexItems + 10;

        }
        for (Item item : listItems) {
            List<ItemImage> threeFirstimages = images.subList(indexImages, indexImages + 3);
            threeFirstimages.stream().forEach(itemImage -> {
                itemImage.setItem(item);
                itemImageRepository.save(itemImage);
            });
            indexImages = indexImages + 3;
        }
    }
public List<String> constructMaterials(){
    List<String> list=new ArrayList<>();
        IntStream.range(0,3).forEach(value -> list.add(faker.commerce().material()));

        return list;
}

    public ItemImage saveImage(ItemImage image) {
        return itemImageRepository.save(image);
    }

    public Item saveItem(Item item) {

        return itemRepository.save(item);
    }

    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    public SubCategory saveSubCategory(SubCategory subCategory) {
        return subCategoryRepository.save(subCategory);
    }


}
