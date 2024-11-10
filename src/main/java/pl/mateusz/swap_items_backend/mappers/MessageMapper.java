package pl.mateusz.swap_items_backend.mappers;

import pl.mateusz.swap_items_backend.dto.message.MessageResponse;
import pl.mateusz.swap_items_backend.entities.Message;

public class MessageMapper {

    public static MessageResponse toMessageResponse(final Message message) {
        if (message == null) return null;

        return MessageResponse.builder()
                .content(message.getContent())
                .sendDate(message.getSendDate())
                .senderId(message.getSender().getId())
                .senderUsername(message.getSender().getUsername())
                .build();
    }
}
