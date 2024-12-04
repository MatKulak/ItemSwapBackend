package pl.mateusz.swap_items_backend.dto.advertisement;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import pl.mateusz.swap_items_backend.enums.Condition;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@SuperBuilder(toBuilder = true)
@ToString(callSuper = true)
@NoArgsConstructor
public class SimpleAdvertisementResponse {

    private UUID id;
    private String title;
    private Condition condition;
    private String city;
    private String street;
    private String postalCode;
    private LocalDateTime addDate;
    private String trade;
}
