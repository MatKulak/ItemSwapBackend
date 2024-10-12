package pl.mateusz.swap_items_backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.mateusz.swap_items_backend.entities.Localization;

import java.util.UUID;

public interface LocalizationRepository extends JpaRepository<Localization, UUID> {
}
