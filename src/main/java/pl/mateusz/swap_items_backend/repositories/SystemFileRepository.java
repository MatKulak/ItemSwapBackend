package pl.mateusz.swap_items_backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.mateusz.swap_items_backend.entities.SystemFile;

import java.util.UUID;

@Repository
public interface SystemFileRepository extends JpaRepository<SystemFile, UUID> {
}
