package pl.mateusz.swap_items_backend.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class Message extends BaseEntity {

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private LocalDateTime sendDate;

    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    private User sender;
}
