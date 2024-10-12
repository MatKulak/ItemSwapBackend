package pl.mateusz.swap_items_backend.dto.advertisement;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
    public class CreateAdvertisementRequest {
    private String mainCategory;
    private String description;
    private String localization;
    private String phoneNumber;
    private String title;
    private String country;
    private String city;
    private String postalCode;
    private String street;
}
