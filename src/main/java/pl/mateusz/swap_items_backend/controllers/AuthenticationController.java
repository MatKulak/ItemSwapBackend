package pl.mateusz.swap_items_backend.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.mateusz.swap_items_backend.dto.auth.LoginRequest;
import pl.mateusz.swap_items_backend.dto.auth.AuthenticationResponse;
import pl.mateusz.swap_items_backend.dto.auth.RegisterRequest;
import pl.mateusz.swap_items_backend.services.AuthenticationService;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class AuthenticationController {
    private static final String AUTH = "/auth";
    private static final String AUTH_REGISTER = AUTH + "/register";
    private static final String AUTH_LOGIN = AUTH + "/login";

    private final AuthenticationService authenticationService;

    @PostMapping(AUTH_REGISTER)
    public ResponseEntity<AuthenticationResponse> register(@RequestBody final RegisterRequest registerRequest) {
        return ResponseEntity.ok(authenticationService.register(registerRequest));
    }

    @PostMapping(AUTH_LOGIN)
    public ResponseEntity<AuthenticationResponse> login(@RequestBody final LoginRequest loginRequest) {
        return ResponseEntity.ok(authenticationService.login(loginRequest));
    }
}
