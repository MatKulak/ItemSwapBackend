package pl.mateusz.swap_items_backend.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.mateusz.swap_items_backend.entities.MainCategory;
import pl.mateusz.swap_items_backend.repositories.MainCategoryRepository;

import java.util.List;

import static pl.mateusz.swap_items_backend.utils.Utils.getOrThrow;
import static pl.mateusz.swap_items_backend.utils.Utils.toStream;

@Service
@RequiredArgsConstructor
public class MainCategoryService {

    private final MainCategoryRepository mainCategoryRepository;

    public MainCategory getMainCategoryByName(final String name) {
        return getOrThrow(mainCategoryRepository.findByName(name));
    }

    public List<String> getAll() {
        return toStream(mainCategoryRepository.findAll()).map(MainCategory::getName).toList();
    }
}
