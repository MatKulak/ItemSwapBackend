package pl.mateusz.swap_items_backend.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.mateusz.swap_items_backend.dto.message.MessageRequest;
import pl.mateusz.swap_items_backend.dto.message.MessageResponse;
import pl.mateusz.swap_items_backend.dto.message.MessageResponseWithReceivers;
import pl.mateusz.swap_items_backend.entities.Advertisement;
import pl.mateusz.swap_items_backend.entities.Conversation;
import pl.mateusz.swap_items_backend.entities.Message;
import pl.mateusz.swap_items_backend.entities.User;
import pl.mateusz.swap_items_backend.utils.Utils;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final AdvertisementService advertisementService;
    private final ConversationService conversationService;
    private final UserService userService;

    @Transactional
    public MessageResponseWithReceivers sendMessage(final MessageRequest messageRequest) {

        final UUID conversationId = messageRequest.getConversationId();
        final UUID senderId = messageRequest.getSenderId();
        final LocalDateTime currentTime = LocalDateTime.now();
        final Advertisement advertisement = advertisementService.getAdvertisementById(messageRequest.getAdvertisementId());
        final User user = userService.getUserById(senderId);
        final Conversation conversation;

        if (conversationId == null) {
            conversation = createNewConversation(messageRequest, currentTime, user, advertisement);
        } else {
            conversation = updateConversation(conversationId, messageRequest, user, currentTime);
        }

        final MessageResponse messageResponse = MessageResponse.builder()
                .content(messageRequest.getContent())
                .sendDate(currentTime)
                .senderId(senderId)
                .senderUsername(user.getUsername())
                .build();

        return MessageResponseWithReceivers.builder()
                .messageResponse(messageResponse)
                .receiverIds(Set.of(conversation.getAdvertisement().getUser().getId(), conversation.getParticipant().getId()))
                .build();
    }

    private Conversation createNewConversation(final MessageRequest messageRequest, final LocalDateTime currentTime,
                                       final User user, final Advertisement advertisement) {

        final Message message = Message.builder()
                .content(messageRequest.getContent())
                .sendDate(currentTime)
                .sender(user)
                .build();

        final Conversation conversation = Conversation.builder()
                .advertisement(advertisement)
                .participant(user)
                .messages(Set.of(message))
                .build();

        return conversationService.save(conversation);
    }

    private Conversation updateConversation(final UUID conversationId, final MessageRequest messageRequest, final User user, final LocalDateTime currentTime) {

        final Conversation conversation = conversationService.getConversationById(conversationId);
        final Set<Message> messages = conversation.getMessages();

        final Message message = Message.builder()
                .content(messageRequest.getContent())
                .sendDate(currentTime)
                .sender(user)
                .build();

        messages.add(message);
        conversation.setMessages(messages);
        return conversationService.save(conversation);
    }
}
