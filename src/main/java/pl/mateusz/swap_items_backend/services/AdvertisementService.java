package pl.mateusz.swap_items_backend.services;

import com.querydsl.core.types.Predicate;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import pl.mateusz.swap_items_backend.dto.advertisement.AdvertisementWithFileResponse;
import pl.mateusz.swap_items_backend.dto.advertisement.CreateAdvertisementRequest;
import pl.mateusz.swap_items_backend.entities.*;
import pl.mateusz.swap_items_backend.mappers.AdvertisementMapper;
import pl.mateusz.swap_items_backend.repositories.AdvertisementRepository;

import java.time.LocalDateTime;
import java.util.*;

import static pl.mateusz.swap_items_backend.criteria.UserCriteria.updateCriteria;
import static pl.mateusz.swap_items_backend.utils.Utils.*;

@Service
@RequiredArgsConstructor
public class AdvertisementService {

    private final AdvertisementRepository advertisementRepository;
    private final SystemFileService systemFileService;
    private final MainCategoryService mainCategoryService;
    private final LocalizationService localizationService;

    public Advertisement getAdvertisementById(final UUID id) {
        return getOrThrow(advertisementRepository.findById(id));
    }

//    @Transactional
//    public void addAdvertisement(final CreateAdvertisementRequest request,
//                                 final List<MultipartFile> files) {
//
//        final Localization localization = Localization.builder()
//                .country(request.getCountry())
//                .city(request.getCity())
//                .postalCode(request.getPostalCode())
//                .street(request.getStreet())
//                .build();
//
//        final Localization savedLocalization = localizationService.save(localization);
//
//        final MainCategory mainCategory =
//                mainCategoryService.getMainCategoryByName(request.getMainCategory());
//
//        final Advertisement advertisement = Advertisement.builder()
//                .title(request.getTitle())
//                .description(request.getDescription())
//                .phoneNumber(request.getPhoneNumber())
//                .localization(savedLocalization)
//                .addDate(LocalDateTime.now())
//                .mainCategory(mainCategory)
//                .user(getLoggedUser())
//                .followers(new HashSet<>())
//                .systemFiles(systemFileService.prepareSystemFiles(files))
//                .build();
//
//        advertisementRepository.save(advertisement);
//    }

    @Transactional
    public void addAdvertisement(final CreateAdvertisementRequest request,
                                 final List<MultipartFile> files) {
        final MainCategory mainCategory = mainCategoryService.getMainCategoryByName(request.getMainCategory());
        advertisementRepository.save(AdvertisementMapper.toEntity(request, mainCategory, systemFileService.prepareSystemFiles(files)));
    }

    public Page<AdvertisementWithFileResponse> getAll(Predicate predicate, final Pageable pageable, final MultiValueMap<String, String> parameters) {
        predicate = updateCriteria(predicate, parameters);
        return advertisementRepository.findAll(predicate, pageable)
                .map(advertisement ->  AdvertisementMapper
                        .toAdvertisementWithFileResponse(
                                advertisement,
                                systemFileService.getFileBySystemFileId(
                                        toStream(advertisement.getSystemFiles())
                                                .filter(systemFile -> systemFile.getFileOrder() == 0)
                                                .map(SystemFile::getId)
                                                .toList()
                                                .get(0)
                                )
                        )
                );
    }


//    public Page<BasicAdvertisementWithImageResponse> getAll(Predicate predicate, final Pageable pageable, final MultiValueMap<String, String> parameters) {
//        predicate = updateCriteria(predicate, parameters);
//        final Page<Advertisement> advertisementPage = advertisementRepository.findAll(predicate, pageable);
//
//        if (advertisementPage.isEmpty()) {
//            return new PageImpl<>(Collections.emptyList(), pageable, 0);
//        }
//
//        final List<BasicAdvertisementWithImageResponse> responseList = toStream(advertisementPage.getContent())
//                .map(advertisement -> {
//                    final LocalizationResponse localizationResponse = LocalizationResponse.builder()
//                            .city(advertisement.getLocalization().getCity())
//                            .postalCode(advertisement.getLocalization().getPostalCode())
//                            .street(advertisement.getLocalization().getStreet())
//                            .build();
//
//                    final MainCategoryResponse mainCategoryResponse = MainCategoryResponse.builder()
//                            .name(advertisement.getMainCategory().getName())
//                            .build();
//
//                    final UserResponse userResponse = UserResponse.builder()
//                            .id(advertisement.getUser().getId())
//                            .name(advertisement.getUser().getFirstName())
//                            .surname(advertisement.getUser().getLastName())
//                            .username(advertisement.getUser().getUsername())
//                            .email(advertisement.getUser().getEmail())
//                            .phoneNumber(advertisement.getUser().getPhoneNumber())
//                            .build();
//
//                    final AdvertisementResponse advertisementResponse = AdvertisementResponse.builder()
//                            .id(advertisement.getId())
//                            .title(advertisement.getTitle())
//                            .description(advertisement.getDescription())
//                            .phoneNumber(advertisement.getPhoneNumber())
//                            .addDate(advertisement.getAddDate())
//                            .localizationResponse(localizationResponse)
//                            .mainCategoryResponse(mainCategoryResponse)
//                            .userResponse(userResponse)
//                            .build();
//
//                    final List<byte[]> files = toStream(prepareSortedFileIds(advertisement))
//                            .map(systemFileService::getFileBySystemFileId)
//                            .collect(Collectors.toList());
//
//                    return BasicAdvertisementWithImageResponse.builder()
//                            .advertisementResponse(advertisementResponse)
//                            .file(files)
//                            .build();
//
//                }).toList();
//
//        return new PageImpl<>(responseList, pageable, advertisementPage.getTotalElements());
//    }


    private static List<UUID> prepareSortedFileIds(final Advertisement advertisement) {
        return advertisement.getSystemFiles().stream()
                .sorted(Comparator.comparingInt(SystemFile::getFileOrder))
                .map(BaseEntity::getId)
                .toList();
    }

}
