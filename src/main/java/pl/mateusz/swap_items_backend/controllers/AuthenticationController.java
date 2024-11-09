package pl.mateusz.swap_items_backend.controllers;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
    private static final String AUTH_LOGGED_IN = AUTH + "/logged-in";

    private final AuthenticationService authenticationService;

    @PostMapping(AUTH_REGISTER)
    public ResponseEntity<AuthenticationResponse> register(@RequestBody final RegisterRequest registerRequest) {
        return ResponseEntity.ok(authenticationService.register(registerRequest));
    }

    @PostMapping(AUTH_LOGIN)
    public ResponseEntity<AuthenticationResponse> login(@RequestBody final LoginRequest loginRequest) {
        return ResponseEntity.ok(authenticationService.login(loginRequest));
    }

    @GetMapping(AUTH_LOGGED_IN)
    public ResponseEntity<Boolean> isLoggedIn(final HttpServletRequest request) {
        boolean isLoggedIn = authenticationService.isLoggedIn(request);
        return isLoggedIn ? ResponseEntity.ok(true) : ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
    }
}
