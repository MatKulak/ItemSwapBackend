package pl.mateusz.swap_items_backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import pl.mateusz.swap_items_backend.entities.Advertisement;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface AdvertisementRepository extends JpaRepository<Advertisement, UUID>, QuerydslPredicateExecutor<Advertisement> {

    Optional<Advertisement> findAdvertisementByUserId(final UUID userId);

    Set<Advertisement> findAdvertisementsByUserId(final UUID userId);
}
