package pl.mateusz.swap_items_backend.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.mateusz.swap_items_backend.dto.validation.SimpleValidationRequest;
import pl.mateusz.swap_items_backend.services.UserService;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class UserController {

    private static final String API_USERS = "/api/users";
    private static final String API_USERS_VALIDATE = API_USERS + "/validate";

    private final UserService userService;

    @GetMapping(API_USERS_VALIDATE)
    public Boolean validate(@RequestBody final SimpleValidationRequest simpleValidationRequest) {
        return userService.validate(simpleValidationRequest);
    }

}
