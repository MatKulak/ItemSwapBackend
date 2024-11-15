package pl.mateusz.swap_items_backend.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.mateusz.swap_items_backend.services.MainCategoryService;

import java.util.List;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class CategoryController {

    private static final String API_CATEGORIES = "/api/categories";

    private final MainCategoryService mainCategoryService;

    @GetMapping(API_CATEGORIES)
    public List<String> getAll() {
        return mainCategoryService.getAll();
    }
}
