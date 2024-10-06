package pl.mateusz.swap_items_backend.mappers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@AllArgsConstructor
public class UserMapper {

//    public static SimpleUserResponse toSimpleUserResponse(final User user) {
//        if (user == null) {
//            return null;
//        }
//
//        return SimpleUserResponse.builder()
//                .id(user.getId())
//                .name(user.getName())
//                .surname(user.getSurname())
//                .phoneNumber(user.getPhoneNumber())
//                .username(user.getUsername())
//                .email(user.getEmail())
//                .build();
//    }

//    public static User toEntity(final RegisterUserRequest registerUserRequest) {
//        if (registerUserRequest == null) {
//            return null;
//        }
//
//        return User.builder()
//                .name(registerUserRequest.getName())
//                .surname(registerUserRequest.getSurname())
//                .username(registerUserRequest.getUsername())
//                .email(registerUserRequest.getEmail())
//                .phoneNumber(registerUserRequest.getPhoneNumber())
//                .password(registerUserRequest.getPassword().toCharArray())
//                .build();
//    }
}
