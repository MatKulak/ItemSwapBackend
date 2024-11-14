package pl.mateusz.swap_items_backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import pl.mateusz.swap_items_backend.entities.Conversation;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ConversationRepository extends JpaRepository<Conversation, UUID>, QuerydslPredicateExecutor<Conversation> {

    Optional<Conversation> findConversationByAdvertisementIdAndParticipantId(final UUID advertisementId, final UUID participantId);

    @Query("""
            SELECT conversation FROM Conversation conversation
            LEFT JOIN FETCH conversation.advertisement advertisement
            LEFT JOIN FETCH conversation.participant participant
            WHERE advertisement.id = :advertisementId
            AND (participant.id = :participantId
            OR advertisement.user.id = :participantId)
            """)
    List<Conversation> findAllConversationsByAdvertisementIdAndParticipantId(final UUID advertisementId, final UUID participantId);

    Optional<Conversation> findConversationByParticipantIdAndAdvertisementId(final UUID participantId, final UUID advertisementId);
}
