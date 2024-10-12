package pl.mateusz.swap_items_backend.services;

import ch.qos.logback.core.util.Loader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.mateusz.swap_items_backend.entities.Localization;
import pl.mateusz.swap_items_backend.repositories.LocalizationRepository;

@Service
@RequiredArgsConstructor
public class LocalizationService {

    private final LocalizationRepository localizationRepository;

    public Localization save(final Localization localization) {
        return localizationRepository.save(localization);
    }
}
