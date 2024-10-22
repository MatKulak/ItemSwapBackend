package pl.mateusz.swap_items_backend.dto.advertisement;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import pl.mateusz.swap_items_backend.dto.localization.LocalizationResponse;
import pl.mateusz.swap_items_backend.dto.user.UserResponse;

@Getter
@Setter
@SuperBuilder(toBuilder = true)
@ToString(callSuper = true)
@NoArgsConstructor
public class DetailedAdvertisementResponse extends SimpleAdvertisementResponse {

    private String description;
    private LocalizationResponse localizationResponse;
    private UserResponse userResponse;
    @JsonProperty("isFollowed")
    private boolean isFollowed;
}
