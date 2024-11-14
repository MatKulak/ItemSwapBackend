package pl.mateusz.swap_items_backend.services;

import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import pl.mateusz.swap_items_backend.criteria.ConversationCriteria;
import pl.mateusz.swap_items_backend.dto.conversation.SimpleConversationResponse;
import pl.mateusz.swap_items_backend.entities.Advertisement;
import pl.mateusz.swap_items_backend.entities.Conversation;
import pl.mateusz.swap_items_backend.mappers.ConversationMapper;
import pl.mateusz.swap_items_backend.repositories.ConversationRepository;
import pl.mateusz.swap_items_backend.utils.Utils;

import java.util.UUID;

import static pl.mateusz.swap_items_backend.utils.Utils.getOrThrow;

@Service
@RequiredArgsConstructor
public class ConversationService {

    private final ConversationRepository conversationRepository;
    private final AdvertisementService advertisementService;

    public Conversation getConversationByAdvertisementId(final UUID advertisementId, final UUID participantId) {
        final UUID loggedUserId = Utils.getLoggedUserId();
        final Advertisement advertisement = advertisementService.getAdvertisementById(advertisementId);
        final boolean isOwnerOfAdvertisement = advertisement.getUser().getId().equals(loggedUserId);

        if (isOwnerOfAdvertisement && participantId != null) {
            return conversationRepository.findAllConversationsByAdvertisementIdAndParticipantId(advertisementId, participantId).get(0);
        } else {
            return getConversationByParticipantIdAndAdvertisementId(loggedUserId, advertisementId);
        }
    }

    public Conversation save(final Conversation conversation) {
        return conversationRepository.save(conversation);
    }

    private Conversation getConversationByParticipantIdAndAdvertisementId(final UUID participantId, final UUID advertisementId) {
        return getOrThrow(conversationRepository.findConversationByParticipantIdAndAdvertisementId(participantId, advertisementId));
    }

    public Conversation getConversationById(final UUID conversationId) {
        return getOrThrow(conversationRepository.findById(conversationId));
    }

    public Page<SimpleConversationResponse> getAll(Predicate predicate, final Pageable pageable, final MultiValueMap<String, String> params) {
        predicate = ConversationCriteria.updateCriteria(predicate, params);
        final UUID loggedUserId = Utils.getLoggedUserId();
        return conversationRepository.findAll(predicate, pageable).map(conversation -> ConversationMapper.toSimpleConversationResponse(conversation, loggedUserId));
    }
}
