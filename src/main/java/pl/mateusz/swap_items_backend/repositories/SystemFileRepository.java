package pl.mateusz.swap_items_backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.mateusz.swap_items_backend.entities.SystemFile;

import java.util.Optional;
import java.util.UUID;

public interface SystemFileRepository extends JpaRepository<SystemFile, UUID> {
    Optional<SystemFile> findFileById(final UUID id);
}
