package pl.mateusz.swap_items_backend.mappers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.mateusz.swap_items_backend.dto.user.UserResponse;
import pl.mateusz.swap_items_backend.entities.User;

@Component
@AllArgsConstructor
public class UserMapper {

    public static UserResponse toResponse(final User user) {
        if (user == null) {
            return null;
        }

        return UserResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .phoneNumber(user.getPhoneNumber())
                .username(user.getUsername())
                .email(user.getEmail())
                .build();
    }
}
