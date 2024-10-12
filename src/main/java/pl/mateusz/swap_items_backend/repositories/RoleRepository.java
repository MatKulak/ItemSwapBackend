package pl.mateusz.swap_items_backend.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import pl.mateusz.swap_items_backend.entities.Role;

import java.util.Optional;
import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, UUID> {

    Optional<Role> findRoleByName(final String name);
}
