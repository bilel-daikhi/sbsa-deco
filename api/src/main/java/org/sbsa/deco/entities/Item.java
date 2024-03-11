package org.sbsa.deco.entities;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    @Column(length = 3000)
    private String description;
    @Column(unique = true)
    private String reference;
    @Column(nullable = false)
    private String landingImage;
    @CreationTimestamp
    private LocalDate created;
    @Column(columnDefinition="Decimal(10,2) default '1.00'")
    private double price;
    private boolean discount;
    @Column(columnDefinition="Decimal(10,2) default '1.00'")
    private double discountAmount;
    private float rating;
    @OneToMany(mappedBy = "item")
    private List<ItemImage> itemImages = new ArrayList<>();
    @ManyToOne
    private SubCategory subCategory;
    @ElementCollection
    private List<String> materials=new ArrayList<>();

    @Builder
    public Item(String name, float price, boolean discount,List<String> materials, double discountAmount, float rating, String description,String reference,String landingImage) {
        this.name = name;
        this.materials=materials;
        this.reference=reference;
        this.landingImage=landingImage;
        this.price = price;
        this.discount = discount;
        this.discountAmount = discountAmount;
        this.rating = rating;
        this.description = description;
    }
}
