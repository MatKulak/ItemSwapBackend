package pl.mateusz.swap_items_backend.dto.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageRequest {

    private UUID conversationId;
    private UUID advertisementId;
    private UUID senderId;
    private String content;
}
