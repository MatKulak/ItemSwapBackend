package pl.mateusz.swap_items_backend.dto.user;

import lombok.Getter;
import pl.mateusz.swap_items_backend.dto.auth.RegisterRequest;

@Getter
public class UpdateUserRequest extends RegisterRequest {

    private boolean updatePassword;
}
