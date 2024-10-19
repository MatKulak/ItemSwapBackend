package pl.mateusz.swap_items_backend.mappers;

import pl.mateusz.swap_items_backend.dto.advertisement.AdvertisementWithFileResponse;
import pl.mateusz.swap_items_backend.dto.advertisement.CreateAdvertisementRequest;
import pl.mateusz.swap_items_backend.dto.advertisement.SimpleAdvertisementResponse;
import pl.mateusz.swap_items_backend.entities.Advertisement;
import pl.mateusz.swap_items_backend.entities.MainCategory;
import pl.mateusz.swap_items_backend.entities.SystemFile;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static pl.mateusz.swap_items_backend.utils.Utils.getLoggedUser;

public class AdvertisementMapper {

    public static SimpleAdvertisementResponse toSimpleAdvertisementResponse(final Advertisement advertisement) {
        if (advertisement == null) return null;

        return SimpleAdvertisementResponse.builder()
                .title(advertisement.getTitle())
                .city(advertisement.getLocalization().getCity())
                .condition(advertisement.getCondition())
                .addDate(advertisement.getAddDate().toString())
                .build();
    }

    public static AdvertisementWithFileResponse toAdvertisementWithFileResponse(final Advertisement advertisement, final byte[] file) {
        if (advertisement == null) return null;

        return AdvertisementWithFileResponse.builder()
                .file(file)
                .simpleAdvertisementResponse(toSimpleAdvertisementResponse(advertisement))
                .build();
    }

    public static Advertisement toEntity(final CreateAdvertisementRequest createAdvertisementRequest,
                                         final MainCategory mainCategory,
                                         final Set<SystemFile> systemFiles) {
        if (createAdvertisementRequest == null) return null;

        return Advertisement.builder()
                .title(createAdvertisementRequest.getTitle())
                .description(createAdvertisementRequest.getDescription())
                .phoneNumber(createAdvertisementRequest.getPhoneNumber())
                .addDate(LocalDateTime.now())
                .mainCategory(mainCategory)
                .localization(LocalizationMapper.toEntity(createAdvertisementRequest))
                .user(getLoggedUser())
                .followers(new HashSet<>())
                .systemFiles(systemFiles)
                .build();
    }
}
