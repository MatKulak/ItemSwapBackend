package pl.mateusz.swap_items_backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.mateusz.swap_items_backend.entities.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    boolean existsByUsername(final String username);
    boolean existsByPhoneNumber(final String phoneNumber);
    boolean existsByEmail(final String email);

    Optional<User> findByUsername(final String username);

}
