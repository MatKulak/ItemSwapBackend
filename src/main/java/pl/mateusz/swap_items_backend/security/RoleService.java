package pl.mateusz.swap_items_backend.security;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.mateusz.swap_items_backend.entities.Role;
import pl.mateusz.swap_items_backend.repositories.RoleRepository;

import static pl.mateusz.swap_items_backend.utils.Utils.getOrThrow;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public Role getRoleByRoleName(final String name) {
        return getOrThrow(roleRepository.findRoleByName(name));
    }
}
