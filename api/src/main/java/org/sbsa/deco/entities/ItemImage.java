package org.sbsa.deco.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@ToString
public class ItemImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @CreationTimestamp
    private LocalDate created;
    private String link;
    @Column(length = 3000)
    private String description;

    @ManyToOne
//    @Column(nullable = false)
    @JsonIgnore
    private Item item;

    @Builder
    public ItemImage(String link, String description) {
        this.link = link;
        this.description = description;

    }
}
