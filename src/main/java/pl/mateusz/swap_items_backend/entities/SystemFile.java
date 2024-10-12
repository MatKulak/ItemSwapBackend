package pl.mateusz.swap_items_backend.entities;

import jakarta.persistence.Entity;
import lombok.*;


@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SystemFile extends BaseEntity {

    private String name;
    private String type;
    private String filePath;
    private boolean deleted;
}
