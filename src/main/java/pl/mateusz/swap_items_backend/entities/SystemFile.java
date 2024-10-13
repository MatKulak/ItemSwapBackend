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
public class SystemFile extends BaseEntity {

    private String originalName;

    private String type;

    @Column(nullable = false)
    private String path;

    private String mimeType;

    private long size;

    private boolean saved;

    private int fileOrder;
}
