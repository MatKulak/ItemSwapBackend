package pl.mateusz.swap_items_backend.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    @NotBlank
    @Size(min = 3, max = 255)
    private String firstName;

    @NotBlank
    @Size(min = 3, max = 255)
    private String lastName;

    @NotBlank
    @Size(min = 3, max = 255)
    private String username;

    @NotBlank
    @Email
    @Size(min = 3, max = 255)
    private String email;

    @NotBlank
    @Size(min = 9, max = 9)
    private String phoneNumber;

    @NotBlank
    @Size(min = 6)
    @Size(min = 3, max = 255)
    private String password;
}
