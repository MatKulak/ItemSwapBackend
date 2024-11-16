package pl.mateusz.swap_items_backend.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.mateusz.swap_items_backend.dto.validation.SimpleValidationRequest;
import pl.mateusz.swap_items_backend.entities.User;
import pl.mateusz.swap_items_backend.repositories.UserRepository;

import java.util.UUID;

import static pl.mateusz.swap_items_backend.utils.Utils.*;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;


    public Boolean validate(final SimpleValidationRequest simpleValidationRequest) {
        final String value = simpleValidationRequest.getValue();

        return switch (simpleValidationRequest.getProperty()) {
            case "email" -> userRepository.existsByEmail(value);
            case "username" -> userRepository.existsByUsername(value);
            case "phoneNumber" -> userRepository.existsByPhoneNumber(value);
            default -> true;
        };
    }

    @Override
    public UserDetails loadUserByUsername(final String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Entity not found"));
    }

    public User getUserById(final UUID userId) {
        return getOrThrow(userRepository.findById(userId));
    }
}
