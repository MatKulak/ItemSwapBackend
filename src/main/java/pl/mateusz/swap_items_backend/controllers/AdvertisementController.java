package pl.mateusz.swap_items_backend.controllers;

import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.PageableDefault;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
//import pl.mateusz.swap_items_backend.dto.advertisement.AdvertisementResponse;
import pl.mateusz.swap_items_backend.dto.advertisement.AdvertisementWithFilesResponse;
import pl.mateusz.swap_items_backend.dto.advertisement.CreateAdvertisementRequest;
import pl.mateusz.swap_items_backend.entities.Advertisement;
import pl.mateusz.swap_items_backend.services.AdvertisementService;

import java.util.List;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class AdvertisementController {

    private static final String API_ADVERTISEMENTS = "/api/advertisements";
    private static final String API_ADVERTISEMENTS_ADD = API_ADVERTISEMENTS + "/add";

    private final AdvertisementService advertisementService;

    @PostMapping(API_ADVERTISEMENTS_ADD)
    public void addAdvertisement(final @RequestPart("data") CreateAdvertisementRequest createAdvertisementRequest,
                                 final @RequestPart("files") List<MultipartFile> files) {
        advertisementService.addAdvertisement(createAdvertisementRequest, files);
    }

    @GetMapping(API_ADVERTISEMENTS)
    public Page<AdvertisementWithFilesResponse> getAll(@QuerydslPredicate(root = Advertisement.class) final Predicate predicate,
                                                       @PageableDefault final Pageable pageable,
                                                       final @RequestParam MultiValueMap<String, String> parameters) {
        return advertisementService.getAll(predicate, pageable, parameters);
    }
}
