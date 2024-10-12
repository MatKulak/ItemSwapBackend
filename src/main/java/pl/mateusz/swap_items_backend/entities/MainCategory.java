package pl.mateusz.swap_items_backend.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class MainCategory extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @ManyToMany
    @JoinTable(
            name = "main_category_sub_categories",
            joinColumns = @JoinColumn(name = "main_category_id"),
            inverseJoinColumns = @JoinColumn(name = "sub_category_id")
    )
    private Set<SubCategory> subCategories = new HashSet<>();
}
