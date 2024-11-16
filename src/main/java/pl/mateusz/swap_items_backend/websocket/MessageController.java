package pl.mateusz.swap_items_backend.websocket;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import pl.mateusz.swap_items_backend.dto.message.MessageRequest;
import pl.mateusz.swap_items_backend.dto.message.MessageResponseWithReceivers;
import pl.mateusz.swap_items_backend.services.MessageService;

@Controller
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @MessageMapping("/message")
    @SendTo("/topic/message")
    public MessageResponseWithReceivers sendMessage(final MessageRequest request) {
        return messageService.sendMessage(request);
    }
}
