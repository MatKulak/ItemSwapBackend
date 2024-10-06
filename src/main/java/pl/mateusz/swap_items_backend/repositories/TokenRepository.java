package pl.mateusz.swap_items_backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.mateusz.swap_items_backend.entities.Token;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TokenRepository extends JpaRepository<Token, UUID> {

    @Query("""
            SELECT token FROM Token token
            LEFT JOIN FETCH token.user user
            WHERE token.user.id = :userId
            AND token.loggedOut = false
            """)
    List<Token> findAllTokensByUserId(final UUID userId);

    Optional<Token> findByToken(final String token);
}
