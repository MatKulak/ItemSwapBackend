package pl.mateusz.swap_items_backend.dto.advertisement;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.mateusz.swap_items_backend.enums.Condition;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SimpleAdvertisementResponse {

    private String title;
    private Condition condition;
    private String city;
    private String addDate;
}
