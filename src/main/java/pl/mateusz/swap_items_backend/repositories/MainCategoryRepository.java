package pl.mateusz.swap_items_backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.mateusz.swap_items_backend.entities.MainCategory;

import java.util.Optional;
import java.util.UUID;

public interface MainCategoryRepository extends JpaRepository<MainCategory, UUID> {

    Optional<MainCategory> findByName(final String name);
}
