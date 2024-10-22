package pl.mateusz.swap_items_backend.mappers;

import pl.mateusz.swap_items_backend.dto.file.SimpleSystemFileResponse;
import pl.mateusz.swap_items_backend.entities.SystemFile;

public class SystemFileMapper {

    public static SimpleSystemFileResponse toSimpleSystemFileResponse(final SystemFile systemFile) {
        if (systemFile == null) return null;

        return SimpleSystemFileResponse.builder()
                .id(systemFile.getId())
                .fileOrder(systemFile.getFileOrder())
                .build();
    }
}
