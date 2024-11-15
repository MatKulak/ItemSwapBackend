package pl.mateusz.swap_items_backend.mappers;

import pl.mateusz.swap_items_backend.dto.advertisement.CreateAdvertisementRequest;
import pl.mateusz.swap_items_backend.dto.localization.LocalizationResponse;
import pl.mateusz.swap_items_backend.entities.Localization;

public class LocalizationMapper {

    public static Localization toEntity(final CreateAdvertisementRequest createAdvertisementRequest) {
        if (createAdvertisementRequest == null) return null;

        return Localization.builder()
                .country("PL")
                .city(createAdvertisementRequest.getCity())
                .postalCode(createAdvertisementRequest.getPostalCode())
                .street(createAdvertisementRequest.getStreet())
                .build();
    }

    public static LocalizationResponse toResponse(final Localization localization) {
        if (localization == null) return null;

        return LocalizationResponse.builder()
                .city(localization.getCity())
                .postalCode(localization.getPostalCode())
                .street(localization.getStreet())
                .build();
    }
}
