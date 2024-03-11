package org.sbsa.deco.controllers.tv;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ItemTV {
    private long id;
    private String name;
    private String reference;
    private String created;
    private double price;
    private long subCategoryId;
    private String subCategory;
    private long categoryId;
    private String category;
    private String description;
    private boolean discount;
    private double discountAmount;

    @Builder
    public ItemTV(long id, String name, String created, double price, long subCategoryId, String subCategory, long categoryId, String category, String description,double discountAmount, boolean discount,String reference) {
        this.id = id;

        this.name = name;
        this.reference=reference;
        this.created = created;
        this.price = price;
        this.subCategoryId = subCategoryId;
        this.category = category;
        this.categoryId = categoryId;
        this.subCategory = subCategory;
        this.description = description;
        this.discount = discount;
        this.discountAmount=discountAmount;
    }
}
