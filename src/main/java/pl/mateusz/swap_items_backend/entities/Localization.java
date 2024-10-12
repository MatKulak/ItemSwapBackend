package pl.mateusz.swap_items_backend.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class Localization extends BaseEntity {

    private String country;

    private String city;

    private String postalCode;

    private String street;
}
