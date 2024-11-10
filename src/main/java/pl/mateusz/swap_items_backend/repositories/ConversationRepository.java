package pl.mateusz.swap_items_backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import pl.mateusz.swap_items_backend.entities.Conversation;

import java.util.Optional;
import java.util.UUID;

public interface ConversationRepository extends JpaRepository<Conversation, UUID>, QuerydslPredicateExecutor<Conversation> {

    Optional<Conversation> findConversationByAdvertisementId(final UUID advertisementId);
}
