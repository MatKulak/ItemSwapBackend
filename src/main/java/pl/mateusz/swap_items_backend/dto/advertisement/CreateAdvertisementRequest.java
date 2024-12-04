package pl.mateusz.swap_items_backend.dto.advertisement;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.mateusz.swap_items_backend.enums.Condition;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateAdvertisementRequest {

    @NotNull
    @NotEmpty
    private String category;

    @NotNull
    @NotEmpty
    private Condition condition;

    @NotNull
    @Size(min = 3, max = 255)
    private String title;

    @NotNull
    @Size(min = 3, max = 255)
    private String description;

    @NotNull
    @Size(min = 3, max = 255)
    private String city;

    @NotNull
    @Size(min = 3, max = 255)
    private String street;

    @NotNull
    @Size(min = 3, max = 255)
    private String postalCode;

    private String phoneNumber;

    @NotNull
    @Size(min = 3, max = 255)
    private String trade;
}
