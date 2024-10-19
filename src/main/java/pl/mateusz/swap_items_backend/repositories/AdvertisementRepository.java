package pl.mateusz.swap_items_backend.repositories;

import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import pl.mateusz.swap_items_backend.entities.Advertisement;

import java.util.UUID;

public interface AdvertisementRepository extends JpaRepository<Advertisement, UUID>, QuerydslPredicateExecutor<Advertisement> {

//    @Query("SELECT a FROM Advertisement a " +
//            "LEFT JOIN FETCH a.systemFiles " +
//            "LEFT JOIN FETCH a.localization")
//    Page<Advertisement> getAdvertisementWithSystemFilesAndLocalization(final Pageable pageable, final Predicate predicate);
}
