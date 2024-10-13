package pl.mateusz.swap_items_backend.utils;

import org.springframework.security.core.context.SecurityContextHolder;
import pl.mateusz.swap_items_backend.entities.User;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import static pl.mateusz.swap_items_backend.others.Messages.ENTITY_NOT_FOUND;

public class Utils {

    public static Object getPrincipal() {
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal();
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

}
