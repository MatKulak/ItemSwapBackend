package pl.mateusz.swap_items_backend.controllers;

import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
import pl.mateusz.swap_items_backend.dto.advertisement.AdvertisementWithFileResponse;
import pl.mateusz.swap_items_backend.dto.advertisement.CreateAdvertisementRequest;
import pl.mateusz.swap_items_backend.entities.Advertisement;
import pl.mateusz.swap_items_backend.services.AdvertisementService;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class AdvertisementController {

    private static final String API_ADVERTISEMENTS = "/api/advertisements";
    private static final String API_ADVERTISEMENTS_ADD = API_ADVERTISEMENTS + "/add";
    private static final String API_ADVERTISEMENTS_PAGE = API_ADVERTISEMENTS + "/page";

    private final AdvertisementService advertisementService;

    @PostMapping(API_ADVERTISEMENTS_ADD)
    public void addAdvertisement(final @RequestPart("data") CreateAdvertisementRequest createAdvertisementRequest,
                                 final @RequestPart("files") List<MultipartFile> files) {
        advertisementService.addAdvertisement(createAdvertisementRequest, files);
    }

//    @GetMapping(API_ADVERTISEMENTS)
//    public Page<BasicAdvertisementWithImageResponse> getAll(final @QuerydslPredicate(root = Advertisement.class) Predicate predicate,
//                                                            final @PageableDefault Pageable pageable,
//                                                            final @RequestParam MultiValueMap<String, String> parameters) {
//        return advertisementService.getAll(predicate, pageable, parameters);
//    }

    @GetMapping(API_ADVERTISEMENTS_PAGE)
    public Page<AdvertisementWithFileResponse> getAll(final @QuerydslPredicate(root = Advertisement.class) Predicate predicate,
                                                      final @PageableDefault Pageable pageable,
                                                      final @RequestParam MultiValueMap<String, String> parameters) {
        return advertisementService.getAll(predicate, pageable, parameters);
    }

    @GetMapping("/test")
    public String test() {
        return "api working";
    }

    private final List<String> dataList = IntStream.range(1, 100).mapToObj(Integer::toString).collect(Collectors.toList());

////    public void StringController() {
////        // Generate some dummy data
//        dataList = IntStream.range(1, 101)
//                .mapToObj(i -> "Item " + i)
//                .collect(Collectors.toList());
////    }

    @GetMapping("/strings")
    public Page<String> getStrings(@RequestParam int page, @RequestParam int size) {
        // Create a PageRequest object with the page and size parameters
        PageRequest pageRequest = PageRequest.of(page, size);

        // Calculate the start and end indices of the sublist
        int start = (int) pageRequest.getOffset();
        int end = Math.min((start + pageRequest.getPageSize()), dataList.size());

        // Return a Page object with the requested sublist of strings
        return new org.springframework.data.domain.PageImpl<>(dataList.subList(start, end), pageRequest, dataList.size());
    }
}
