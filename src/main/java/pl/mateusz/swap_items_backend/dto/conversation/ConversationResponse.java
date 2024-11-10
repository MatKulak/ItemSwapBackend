package pl.mateusz.swap_items_backend.dto.conversation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.mateusz.swap_items_backend.dto.message.MessageResponse;
import pl.mateusz.swap_items_backend.dto.user.UserResponse;

import java.util.Set;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConversationResponse {

    private UUID id;
    private UserResponse seller;
    private UserResponse buyer;
    private Set<MessageResponse> messages;
}
