package pl.mateusz.swap_items_backend.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.mateusz.swap_items_backend.entities.MainCategory;
import pl.mateusz.swap_items_backend.repositories.MainCategoryRepository;

import static pl.mateusz.swap_items_backend.utils.Utils.getOrThrow;

@Service
@RequiredArgsConstructor
public class MainCategoryService {

    private final MainCategoryRepository mainCategoryRepository;

    public MainCategory getMainCategoryByName(final String name) {
        return getOrThrow(mainCategoryRepository.findByName(name));
    }
}
