package pl.mateusz.swap_items_backend.dto.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageResponse {

    private String content;
    private LocalDateTime sendDate;
    private UUID senderId;
    private String senderUsername;
}
