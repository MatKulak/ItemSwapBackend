package pl.mateusz.swap_items_backend.dto.conversation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SimpleConversationResponse {

    private String advertisementTitle;
    private UUID advertisementId;
    private String username;
    private UUID participantId;
    private UUID conversationId;
}

