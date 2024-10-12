package pl.mateusz.swap_items_backend.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "sub_category")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class SubCategory extends BaseEntity {

    private String name;
}
