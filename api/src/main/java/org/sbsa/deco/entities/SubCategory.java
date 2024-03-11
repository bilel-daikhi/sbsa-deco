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
public class SubCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    @CreationTimestamp
    private LocalDate created;
    @ManyToOne
    private Category category;
    @OneToMany(mappedBy = "subCategory",cascade = CascadeType.ALL)
    private List<Item> items = new ArrayList<>();

    @Builder
    public SubCategory(String name, List<Item> items) {
        this.name = name;
        this.items = items;
    }
}
