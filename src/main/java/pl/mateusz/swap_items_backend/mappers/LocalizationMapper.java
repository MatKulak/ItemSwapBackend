package pl.mateusz.swap_items_backend.mappers;

import org.springframework.cglib.core.Local;
import pl.mateusz.swap_items_backend.dto.advertisement.CreateAdvertisementRequest;
import pl.mateusz.swap_items_backend.entities.Localization;

public class LocalizationMapper {

    public static Localization toEntity(final CreateAdvertisementRequest createAdvertisementRequest) {
        if (createAdvertisementRequest == null) return null;

        return Localization.builder()
                .country(createAdvertisementRequest.getCountry() == null ? "PL" : createAdvertisementRequest.getCountry())
                .city(createAdvertisementRequest.getCity())
                .postalCode(createAdvertisementRequest.getPostalCode())
                .street(createAdvertisementRequest.getStreet())
                .build();
    }
}
