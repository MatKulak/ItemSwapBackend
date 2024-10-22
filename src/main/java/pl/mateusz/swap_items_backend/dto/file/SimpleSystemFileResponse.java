package pl.mateusz.swap_items_backend.dto.file;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SimpleSystemFileResponse {

    private UUID id;
    private int fileOrder;
}
