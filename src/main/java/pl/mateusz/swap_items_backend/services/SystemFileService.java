package pl.mateusz.swap_items_backend.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.mateusz.swap_items_backend.entities.SystemFile;
import pl.mateusz.swap_items_backend.repositories.SystemFileRepository;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SystemFileService {
    private final String FOLDER_PATH = "C:\\Files\\";

    private final SystemFileRepository systemFileRepository;

    public SystemFile save(final MultipartFile file) throws IOException {
        final UUID systemFileId = UUID.randomUUID();
        final String systemFilePath = FOLDER_PATH + systemFileId;

        final SystemFile systemFile = SystemFile.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .filePath(systemFilePath)
                .deleted(false)
                .build();

        systemFile.setId(systemFileId);
        file.transferTo(new File(systemFilePath));
        return systemFileRepository.save(systemFile);
    }

//    public byte[] getSystemFile()
}
