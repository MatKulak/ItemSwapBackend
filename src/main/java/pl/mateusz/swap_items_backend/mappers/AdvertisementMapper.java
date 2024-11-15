package pl.mateusz.swap_items_backend.mappers;

import pl.mateusz.swap_items_backend.dto.advertisement.AdvertisementWithFileResponse;
import pl.mateusz.swap_items_backend.dto.advertisement.CreateAdvertisementRequest;
import pl.mateusz.swap_items_backend.dto.advertisement.DetailedAdvertisementResponse;
import pl.mateusz.swap_items_backend.dto.advertisement.DetailedAdvertisementWithFilesResponse;
import pl.mateusz.swap_items_backend.dto.advertisement.SimpleAdvertisementResponse;
import pl.mateusz.swap_items_backend.entities.Advertisement;
import pl.mateusz.swap_items_backend.entities.Localization;
import pl.mateusz.swap_items_backend.entities.MainCategory;
import pl.mateusz.swap_items_backend.entities.SystemFile;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static pl.mateusz.swap_items_backend.utils.Utils.getLoggedUser;
import static pl.mateusz.swap_items_backend.utils.Utils.isNullOrEmpty;

public class AdvertisementMapper {

    public static SimpleAdvertisementResponse toSimpleAdvertisementResponse(final Advertisement advertisement) {
        if (advertisement == null) return null;

        return SimpleAdvertisementResponse.builder()
                .id(advertisement.getId())
                .title(advertisement.getTitle())
                .city(advertisement.getLocalization().getCity())
                .street(advertisement.getLocalization().getStreet())
                .postalCode(advertisement.getLocalization().getPostalCode())
                .condition(advertisement.getCondition())
                .addDate(advertisement.getAddDate())
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
                                         final Set<SystemFile> systemFiles,
                                         final Localization localization,
                                         final String phoneNumber) {
        if (createAdvertisementRequest == null) return null;

        return Advertisement.builder()
                .title(createAdvertisementRequest.getTitle())
                .description(createAdvertisementRequest.getDescription())
                .phoneNumber(isNullOrEmpty(createAdvertisementRequest.getPhoneNumber()) ? phoneNumber :
                        createAdvertisementRequest.getPhoneNumber())
                .addDate(LocalDateTime.now())
                .mainCategory(mainCategory)
                .localization(localization)
                .user(getLoggedUser())
                .followers(new HashSet<>())
                .systemFiles(systemFiles)
                .condition(createAdvertisementRequest.getCondition())
                .build();
    }

    public static DetailedAdvertisementResponse toDetailedAdvertisementResponse(final Advertisement advertisement) {
        if (advertisement == null) return null;

        return DetailedAdvertisementResponse.builder()
                .isFollowed(advertisement.getFollowers().contains(getLoggedUser()))
                .id(advertisement.getId())
                .title(advertisement.getTitle())
                .condition(advertisement.getCondition())
                .city(advertisement.getLocalization().getCity())
                .addDate(advertisement.getAddDate())
                .description(advertisement.getDescription())
                .localizationResponse(LocalizationMapper.toResponse(advertisement.getLocalization()))
                .userResponse(UserMapper.toResponse(advertisement.getUser()))
                .category(advertisement.getMainCategory().getName())
                .phoneNumber(advertisement.getPhoneNumber())
                .build();

    }

    public static DetailedAdvertisementWithFilesResponse toDetailedAdvertisementWithFilesResponse(final Advertisement advertisement, final List<UUID> fileIds) {
        if (advertisement == null) return null;

        return DetailedAdvertisementWithFilesResponse.builder()
                .detailedAdvertisement(toDetailedAdvertisementResponse(advertisement))
                .fileIds(fileIds)
                .build();
    }
}
