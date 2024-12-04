package pl.mateusz.swap_items_backend.services;

import com.querydsl.core.types.Predicate;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import pl.mateusz.swap_items_backend.dto.advertisement.AdvertisementTradeDetails;
import pl.mateusz.swap_items_backend.dto.advertisement.AdvertisementWithFileResponse;
import pl.mateusz.swap_items_backend.dto.advertisement.CreateAdvertisementRequest;
import pl.mateusz.swap_items_backend.dto.advertisement.DetailedAdvertisementResponse;
import pl.mateusz.swap_items_backend.entities.Advertisement;
import pl.mateusz.swap_items_backend.entities.BaseEntity;
import pl.mateusz.swap_items_backend.entities.Localization;
import pl.mateusz.swap_items_backend.entities.MainCategory;
import pl.mateusz.swap_items_backend.entities.SystemFile;
import pl.mateusz.swap_items_backend.entities.User;
import pl.mateusz.swap_items_backend.mappers.AdvertisementMapper;
import pl.mateusz.swap_items_backend.mappers.LocalizationMapper;
import pl.mateusz.swap_items_backend.repositories.AdvertisementRepository;
import pl.mateusz.swap_items_backend.utils.Utils;


import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static pl.mateusz.swap_items_backend.criteria.AdvertisementCriteria.addAdvertisementsIdsToCriteria;
import static pl.mateusz.swap_items_backend.criteria.AdvertisementCriteria.updateCriteria;
import static pl.mateusz.swap_items_backend.utils.Utils.*;

@Service
@RequiredArgsConstructor
public class AdvertisementService {

    private final AdvertisementRepository advertisementRepository;
    private final SystemFileService systemFileService;
    private final MainCategoryService mainCategoryService;
    private final LocalizationService localizationService;
    private final AIService aiService;

    @Transactional
    public void addAdvertisement(final CreateAdvertisementRequest request,
                                 final List<MultipartFile> files) {
        final MainCategory mainCategory = mainCategoryService.getMainCategoryByName(request.getCategory());
        final Localization localization = localizationService.save(LocalizationMapper.toEntity(request));
        final String phoneNumber = getLoggedUser().getPhoneNumber();
        advertisementRepository.save(AdvertisementMapper.toEntity(request, mainCategory, systemFileService.prepareSystemFiles(files), localization, phoneNumber));
    }

    @Transactional
    public void updateAdvertisement(final UUID id,
                                    final CreateAdvertisementRequest createAdvertisementRequest,
                                    final List<MultipartFile> files) {
        final Advertisement advertisement = getOrThrow(advertisementRepository.findById(id));
        advertisementRepository.save(update(advertisement, createAdvertisementRequest, files));
    }

    public Page<AdvertisementWithFileResponse> getAll(Predicate predicate, final Pageable pageable, final MultiValueMap<String, String> parameters) {
        final String filter = parameters.getFirst("filter");

        if (filter != null && !"matchmaking".equals(filter)) {
            predicate = updateCriteria(predicate, parameters);
            return getAllAdvertisements(predicate, pageable);
        }

        final User loggedUser = getLoggedUser();
        final Set<Advertisement> userAdvertisements = advertisementRepository.findAdvertisementsByUserId(loggedUser.getId());
        final Set<UUID> userAdvertisementsIds = toStream(userAdvertisements)
                .map(BaseEntity::getId)
                .collect(Collectors.toSet());;

        if (userAdvertisements.isEmpty()) {
            return new PageImpl<>(Collections.emptyList(), pageable, 0);
        }

        final List<UUID> matchingAdvertisementsIds = getMatchingAdvertisementIds(loggedUser);

        if (matchingAdvertisementsIds == null || matchingAdvertisementsIds.isEmpty()) {
            return new PageImpl<>(Collections.emptyList(), pageable, 0);
        }

        predicate = addAdvertisementsIdsToCriteria(predicate, matchingAdvertisementsIds, userAdvertisementsIds);
        return getAllAdvertisements(predicate, pageable);
    }

    private List<UUID> getMatchingAdvertisementIds(final User loggedUser) {
        final List<Advertisement> allAdvertisements = advertisementRepository.findAll();

        final List<AdvertisementTradeDetails> myAdvertisements = toStream(allAdvertisements).
                filter(advertisement -> advertisement.getUser().getId().equals(loggedUser.getId()))
                .map(AdvertisementMapper::toAdvertisementTradeDetails)
                .toList();

        final List<AdvertisementTradeDetails> otherAdvertisements = toStream(allAdvertisements).
                filter(advertisement -> !advertisement.getUser().getId().equals(loggedUser.getId()))
                .map(AdvertisementMapper::toAdvertisementTradeDetails)
                .toList();

        final String matchingIds = aiService.findMatchingAdvertisementsIds(mapObjectToJosnString(myAdvertisements), mapObjectToJosnString(otherAdvertisements));
        if (matchingIds == null) return null;

        return extractUUIDs(matchingIds);
    }

    private Page<AdvertisementWithFileResponse> getAllAdvertisements(final Predicate predicate, final Pageable pageable) {
        return advertisementRepository.findAll(predicate, pageable)
                .map(advertisement -> AdvertisementMapper
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

    public DetailedAdvertisementResponse getOneById(final UUID id) {
        return AdvertisementMapper.toDetailedAdvertisementResponse(getAdvertisementById(id));
    }

    public List<byte[]> getAdvertisementFiles(final UUID id) {
        return toStream(getAdvertisementById(id).getSystemFiles())
                .sorted(Comparator.comparingInt(SystemFile::getFileOrder))
                .map(systemFile -> systemFileService.getFileBySystemFileId(systemFile.getId()))
                .toList();
    }

    @Transactional
    public boolean updateAdvertisementFollowers(final UUID id) {
        final Advertisement advertisement = getAdvertisementById(id);
        final User user = getLoggedUser();
        final Set<User> advertisementFollowers = advertisement.getFollowers();
        final boolean result;
        if (!advertisementFollowers.contains(user)) {
            advertisementFollowers.add(user);
            result = true;
        }
        else {
            advertisementFollowers.remove(user);
            result = false;
        }
        advertisement.setFollowers(advertisementFollowers);
        advertisementRepository.save(advertisement);
        return result;
    }

    private static List<UUID> prepareSortedFileIds(final Advertisement advertisement) {
        return advertisement.getSystemFiles().stream()
                .sorted(Comparator.comparingInt(SystemFile::getFileOrder))
                .map(BaseEntity::getId)
                .toList();
    }

    public Advertisement getAdvertisementById(final UUID id) {
        return getOrThrow(advertisementRepository.findById(id));
    }

    public Advertisement getAdvertisementByUserId(final UUID userId) {
        return getOrThrow(advertisementRepository.findAdvertisementByUserId(userId));
    }

    private Advertisement update(final Advertisement advertisement,
                                 final CreateAdvertisementRequest request,
                                 final List<MultipartFile> files) {
        advertisement.setMainCategory(mainCategoryService.getMainCategoryByName(request.getCategory()));
        advertisement.setCondition(request.getCondition());
        advertisement.setTitle(request.getTitle());
        advertisement.setDescription(request.getDescription());
        advertisement.setLocalization(localizationService.save(LocalizationMapper.toEntity(request)));
        advertisement.setPhoneNumber(isNullOrEmpty(request.getPhoneNumber()) ? getLoggedUser().getPhoneNumber() : request.getPhoneNumber());
        advertisement.setSystemFiles(systemFileService.prepareSystemFiles(files));
        advertisement.setTrade(request.getTrade());
        return advertisement;
    }
}
