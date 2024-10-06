package pl.mateusz.swap_items_backend.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.mateusz.swap_items_backend.dto.auth.LoginRequest;
import pl.mateusz.swap_items_backend.dto.auth.AuthenticationResponse;
import pl.mateusz.swap_items_backend.dto.auth.RegisterRequest;
import pl.mateusz.swap_items_backend.dto.user.UserResponse;
import pl.mateusz.swap_items_backend.entities.Token;
import pl.mateusz.swap_items_backend.entities.User;
import pl.mateusz.swap_items_backend.repositories.TokenRepository;
import pl.mateusz.swap_items_backend.repositories.UserRepository;
import pl.mateusz.swap_items_backend.security.JwtService;

import java.util.List;

import static pl.mateusz.swap_items_backend.others.Messages.*;
import static pl.mateusz.swap_items_backend.others.Messages.PHONE_NUMBER_ALREADY_EXISTS;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final TokenRepository tokenRepository;

    public AuthenticationResponse login(final LoginRequest loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );

        final User user = userRepository.findByUsername(loginRequest.getUsername()).orElseThrow();
        final String jwt = jwtService.generateToken(user);
        final List<Token> revokedTokens = revokeAllTokensByUser(user);
        final Token savedToken = saveUserToken(jwt, user);
        final UserResponse userResponse = toUserResponse(user);

        return AuthenticationResponse.builder()
                .token(savedToken.getToken())
                .userResponse(userResponse)
                .build();
    }

    @Transactional
    public AuthenticationResponse register(final RegisterRequest registerRequest) {
        if (!registerRequest.getPassword().equals(registerRequest.getConfirmPassword()))
            throw new RuntimeException(NON_MATCHING_PASSWORDS);

        if (userRepository.existsByUsername(registerRequest.getUsername()))
            throw new RuntimeException(USERNAME_ALREADY_EXISTS);

        if (userRepository.existsByEmail(registerRequest.getEmail()))
            throw new RuntimeException(EMAIL_ALREADY_EXISTS);

        if (userRepository.existsByPhoneNumber(registerRequest.getPhoneNumber()))
            throw new RuntimeException(PHONE_NUMBER_ALREADY_EXISTS);

        final User user = User.builder()
                .name(registerRequest.getName())
                .surname(registerRequest.getSurname())
                .username(registerRequest.getUsername())
                .email(registerRequest.getEmail())
                .phoneNumber(registerRequest.getPhoneNumber())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .build();

        final User savedUser = userRepository.save(user);
        final UserResponse userResponse = toUserResponse(savedUser);
        final String token = jwtService.generateToken(savedUser);
        saveUserToken(token, savedUser);

        return AuthenticationResponse.builder()
                .token(token)
                .userResponse(userResponse)
                .build();
    }

    private static UserResponse toUserResponse(final User user) {
        if (user == null) return null;

        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .surname(user.getSurname())
                .username(user.getUsername())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .build();
    }

    private List<Token> revokeAllTokensByUser(final User user) {
        final List<Token> validTokenListByUser = tokenRepository.findAllTokensByUserId(user.getId());

        if (!validTokenListByUser.isEmpty()) {
            validTokenListByUser.forEach(token -> token.setLoggedOut(true));
        }

        return tokenRepository.saveAll(validTokenListByUser);
    }



    private Token saveUserToken(final String jwt, final User user) {
        final Token token = Token.builder()
                .token(jwt)
                .loggedOut(false)
                .user(user)
                .build();

        return tokenRepository.save(token);
    }
}
