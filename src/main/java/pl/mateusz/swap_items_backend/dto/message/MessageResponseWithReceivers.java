package pl.mateusz.swap_items_backend.dto.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageResponseWithReceivers {

    private UUID conversationId;
    private MessageResponse messageResponse;
    private Set<UUID> receiverIds;
}
