package pl.mateusz.swap_items_backend.mappers;

import pl.mateusz.swap_items_backend.dto.conversation.ConversationResponse;
import pl.mateusz.swap_items_backend.dto.conversation.SimpleConversationResponse;
import pl.mateusz.swap_items_backend.entities.Conversation;

import java.util.stream.Collectors;

import static pl.mateusz.swap_items_backend.utils.Utils.toStream;

public class ConversationMapper {

    public static ConversationResponse toConversationResponse(final Conversation conversation) {
        if (conversation == null) return null;

        return ConversationResponse.builder()
                .id(conversation.getId())
                .seller(UserMapper.toResponse(conversation.getAdvertisement().getUser()))
                .buyer(UserMapper.toResponse(conversation.getParticipant()))
                .messages(toStream(conversation.getMessages()).map(MessageMapper::toMessageResponse).collect(Collectors.toSet()))
                .build();
    }

    public static SimpleConversationResponse toSimpleConversationResponse(final Conversation conversation) {
        if (conversation == null) return null;

        return SimpleConversationResponse.builder()
                .advertisementTitle(conversation.getAdvertisement().getTitle())
                .advertisementId(conversation.getAdvertisement().getId())
                .username(conversation.getParticipant().getUsername())
                .build();
    }
}
