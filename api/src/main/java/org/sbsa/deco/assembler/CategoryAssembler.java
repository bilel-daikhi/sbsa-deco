package org.sbsa.deco.assembler;

//@Component
public class CategoryAssembler {
    //extends RepresentationModelAssemblerSupport<Category, CategoryDTO> {
//    @Autowired
//    private CategoryMapper categoryMapper;
//    public CategoryAssembler() {
//        super(CategoryController.class, CategoryDTO.class);
//    }
//
//    @SneakyThrows
//    @Override
//    public CategoryDTO toModel(Category entity) {
//        CategoryDTO model = instantiateModel(entity);
//        model.add(linkTo(
//                methodOn(CategoryController.class)
//                        .findCategoryById(entity.getId()))
//                .withSelfRel());
//        model = categoryMapper.categoryToCategoryDTO(entity);
//        return model;
//    }
//    @SneakyThrows
//    public Category toEntity(CategoryDTO entity) {
//        Category category = new Category();
//        category = categoryMapper.categoryDTOToCategory(entity);
//        return category;
//    }
}
