package pl.mateusz.swap_items_backend.entities;

import jakarta.persistence.*;
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
public class Conversation extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "advertisement_id", nullable = false)
    private Advertisement advertisement;

    @ManyToOne
    @JoinColumn(name = "participant_id", nullable = false)
    private User participant;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "conversation_messages",
            joinColumns = @JoinColumn(name = "conversation_id"),
            inverseJoinColumns = @JoinColumn(name = "message_id")
    )
    private Set<Message> messages = new HashSet<>();
}