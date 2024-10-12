package pl.mateusz.swap_items_backend.services;

import com.querydsl.core.types.Predicate;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import pl.mateusz.swap_items_backend.dto.advertisement.AdvertisementWithFilesResponse;
import pl.mateusz.swap_items_backend.dto.advertisement.CreateAdvertisementRequest;
import pl.mateusz.swap_items_backend.entities.Advertisement;
import pl.mateusz.swap_items_backend.entities.Localization;
import pl.mateusz.swap_items_backend.entities.MainCategory;
import pl.mateusz.swap_items_backend.repositories.AdvertisementRepository;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import static pl.mateusz.swap_items_backend.utils.Utils.getLoggedUser;
import static pl.mateusz.swap_items_backend.utils.Utils.getOrThrow;

@Service
@RequiredArgsConstructor
public class AdvertisementService {

    private final FileService fileService;
    private final AdvertisementRepository advertisementRepository;
    private final MainCategoryService mainCategoryService;
    private final LocalizationService localizationService;

    public Advertisement getAdvertisementById(final UUID id) {
        return getOrThrow(advertisementRepository.findById(id));
    }

    @Transactional
    public Advertisement addAdvertisement(final CreateAdvertisementRequest request,
                                          final List<MultipartFile> files) {

        final Localization localization = Localization.builder()
                .country(request.getCountry())
                .city(request.getCity())
                .postalCode(request.getPostalCode())
                .street(request.getStreet())
                .build();

        final Localization savedLocalization = localizationService.save(localization);

        final MainCategory mainCategory =
                mainCategoryService.getMainCategoryByName(request.getMainCategory());

        final Advertisement advertisement = Advertisement.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .phoneNumber(request.getPhoneNumber())
                .localization(savedLocalization)
                .addDate(LocalDateTime.now())
                .mainCategory(mainCategory)
                .user(getLoggedUser())
                .followers(new HashSet<>())
                .files(fileService.prepareFiles(files))
                .build();

        return advertisementRepository.save(advertisement);
    }

    public Page<AdvertisementWithFilesResponse> getAll(Predicate predicate,
                                                       final Pageable pageable,
                                                       final MultiValueMap<String, String> parameters) {

    }
}
