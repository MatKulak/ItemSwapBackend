package pl.mateusz.swap_items_backend.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import pl.mateusz.swap_items_backend.entities.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import static pl.mateusz.swap_items_backend.others.Messages.ENTITY_NOT_FOUND;

public class Utils {

    public static Object getPrincipal() {
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public static boolean isUserLoggedIn() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken);
    }

    public static UUID getLoggedUserId() {
        return ((User) getPrincipal()).getId();
    }

    public static User getLoggedUser() {
        return (User) getPrincipal();
    }

    public static <T> T getOrThrow(final Optional<T> entity) {
        return entity.orElseThrow(() -> new RuntimeException(ENTITY_NOT_FOUND));
    }

    public static String getFileExtension(final String fileName) {
        int lastIndexOfDot = fileName.lastIndexOf(".");
        if (lastIndexOfDot == -1) {
            return "";
        }
        return fileName.substring(lastIndexOfDot);
    }

    public static <T> Stream<T> toStream(final Collection<T> collection) {
        return Stream.ofNullable(collection).flatMap(Collection::stream);
    }

    public static boolean isNullOrEmpty(final String string) {
        return string == null || string.isEmpty();
    }

    public static String mapObjectToJosnString(final Object object) {
        try {
            final ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            return null;
        }
    }

    public static List<UUID> extractUUIDs(final String input) {
        final List<UUID> uuidList = new ArrayList<>();
        final String[] uuidStrings = input.split(",");

        for (String uuidStr : uuidStrings) {
            try {
                uuidList.add(UUID.fromString(uuidStr.trim()));
            } catch (final IllegalArgumentException e) {
                return null;
            }
        }

        return uuidList;
    }
}
