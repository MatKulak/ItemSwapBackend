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
import pl.mateusz.swap_items_backend.dto.advertisement.DetailedAdvertisementResponse;
import pl.mateusz.swap_items_backend.dto.advertisement.DetailedAdvertisementWithFilesResponse;
import pl.mateusz.swap_items_backend.entities.Advertisement;
import pl.mateusz.swap_items_backend.entities.BaseEntity;
import pl.mateusz.swap_items_backend.entities.Localization;
import pl.mateusz.swap_items_backend.entities.MainCategory;
import pl.mateusz.swap_items_backend.entities.SystemFile;
import pl.mateusz.swap_items_backend.entities.User;
import pl.mateusz.swap_items_backend.mappers.AdvertisementMapper;
import pl.mateusz.swap_items_backend.mappers.LocalizationMapper;
import pl.mateusz.swap_items_backend.repositories.AdvertisementRepository;


import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static pl.mateusz.swap_items_backend.criteria.UserCriteria.updateCriteria;
import static pl.mateusz.swap_items_backend.utils.Utils.*;

@Service
@RequiredArgsConstructor
public class AdvertisementService {

    private final AdvertisementRepository advertisementRepository;
    private final SystemFileService systemFileService;
    private final MainCategoryService mainCategoryService;
    private final LocalizationService localizationService;

    @Transactional
    public void addAdvertisement(final CreateAdvertisementRequest request,
                                 final List<MultipartFile> files) {
        final MainCategory mainCategory = mainCategoryService.getMainCategoryByName(request.getMainCategory());
        final Localization localization = localizationService.save(LocalizationMapper.toEntity(request));
        advertisementRepository.save(AdvertisementMapper.toEntity(request, mainCategory, systemFileService.prepareSystemFiles(files), localization));
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

//    public DetailedAdvertisementWithFilesResponse getOneById(final UUID id) {
//        final Advertisement advertisement = getAdvertisementById(id);
//        final List<UUID> sortedFiles = toStream(advertisement.getSystemFiles())
//                .sorted(Comparator.comparingInt(SystemFile::getFileOrder))
//                .map(BaseEntity::getId)
//                .toList();
//
//        return AdvertisementMapper.toDetailedAdvertisementWithFilesResponse(advertisement, sortedFiles);
//    }

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

    private Advertisement getAdvertisementById(final UUID id) {
        return getOrThrow(advertisementRepository.findById(id));
    }



}
