package pl.mateusz.swap_items_backend.controllers;

import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.PageableDefault;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.mateusz.swap_items_backend.dto.advertisement.AdvertisementWithFileResponse;
import pl.mateusz.swap_items_backend.dto.advertisement.CreateAdvertisementRequest;
import pl.mateusz.swap_items_backend.dto.advertisement.DetailedAdvertisementResponse;
import pl.mateusz.swap_items_backend.entities.Advertisement;
import pl.mateusz.swap_items_backend.services.AdvertisementService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class AdvertisementController {

    private static final String API_ADVERTISEMENTS = "/api/advertisements";
    private static final String API_ADVERTISEMENTS_ADD = API_ADVERTISEMENTS + "/add";
    private static final String API_ADVERTISEMENTS_PAGE = API_ADVERTISEMENTS + "/page";
    private static final String API_ADVERTISEMENT = API_ADVERTISEMENTS + "/{id}";
    private static final String API_ADVERTISEMENT_FILES = API_ADVERTISEMENT + "/files";
    private static final String API_ADVERTISEMENT_FOLLOWERS = API_ADVERTISEMENT + "/followers";
    private final AdvertisementService advertisementService;

    @PostMapping(API_ADVERTISEMENTS_ADD)
    public void addAdvertisement(final @RequestPart("data") CreateAdvertisementRequest createAdvertisementRequest,
                                 final @RequestPart("files") List<MultipartFile> files) {
        advertisementService.addAdvertisement(createAdvertisementRequest, files);
    }

    @GetMapping(API_ADVERTISEMENTS_PAGE)
    public Page<AdvertisementWithFileResponse> getAll(final @QuerydslPredicate(root = Advertisement.class) Predicate predicate,
                                                      final @PageableDefault Pageable pageable,
                                                      final @RequestParam MultiValueMap<String, String> params) {
        return advertisementService.getAll(predicate, pageable, params);
    }

    @GetMapping(API_ADVERTISEMENT)
    public DetailedAdvertisementResponse getOneById(@PathVariable("id") final UUID id) {
        return advertisementService.getOneById(id);
    }

    @GetMapping(API_ADVERTISEMENT_FILES)
    public List<byte[]> getAdvertisementFiles(@PathVariable("id") final UUID id) {
        return advertisementService.getAdvertisementFiles(id);
    }

    @PostMapping(API_ADVERTISEMENT_FOLLOWERS)
    public boolean updateAdvertisementFollowers(@PathVariable("id") final UUID id) {
        return advertisementService.updateAdvertisementFollowers(id);
    }
}
