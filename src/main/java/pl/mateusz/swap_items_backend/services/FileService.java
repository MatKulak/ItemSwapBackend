package pl.mateusz.swap_items_backend.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.mateusz.swap_items_backend.entities.Advertisement;
import pl.mateusz.swap_items_backend.entities.File;
import pl.mateusz.swap_items_backend.repositories.AdvertisementRepository;
import pl.mateusz.swap_items_backend.repositories.FileRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static pl.mateusz.swap_items_backend.others.Constants.allowedImageExtensions;
import static pl.mateusz.swap_items_backend.others.Messages.NOT_ALLOWED_EXTENSION;
import static pl.mateusz.swap_items_backend.utils.Utils.getFileExtension;
import static pl.mateusz.swap_items_backend.utils.Utils.getOrThrow;

@Service
@RequiredArgsConstructor
public class FileService {

    private final FileRepository fileRepository;

    private final AdvertisementRepository advertisementRepository;

    @Value("${file.storage.directory}")
    private String storageDirectory;

    public Set<File> prepareFiles(final List<MultipartFile> files) {
        final Set<File> fileSet = new HashSet<>();

        files.forEach(file -> {
            try {
                final UUID fileId = UUID.randomUUID();
                final String filePath = saveFile(file, fileId);

                final File fileEntity = File.builder()
                        .id(fileId)
                        .originalName(file.getOriginalFilename())
                        .type(file.getContentType())
                        .path(filePath)
                        .mimeType(file.getContentType())
                        .size(file.getSize())
                        .deleted(false)
                        .build();

                fileSet.add(fileEntity);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        return new HashSet<>(fileRepository.saveAll(fileSet));
    }

    private String saveFile(final MultipartFile file, final UUID fileId) throws IOException {
        final String fileExtension = getFileExtension(file.getOriginalFilename());
        if (allowedImageExtensions.contains(fileExtension))
            throw new RuntimeException(NOT_ALLOWED_EXTENSION);

        final String fileName = fileId + fileExtension;
        final Path filePath = Paths.get(storageDirectory).resolve(fileName);
        Files.copy(file.getInputStream(), filePath);
        return filePath.toString();
    }


    public File saveFile1(final MultipartFile multipartFile) throws IOException {
        final String originalName = multipartFile.getOriginalFilename();
        final String extension =  originalName.substring(originalName.lastIndexOf('.') + 1);
        final Long size = multipartFile.getSize();
        final LocalDateTime uploadDateTime = LocalDateTime.now();
        final byte[] data = multipartFile.getBytes();

        final File file = File.builder()
                .originalName(originalName)
//                .extension(extension)
                .size(size)
//                .uploadDateTime(uploadDateTime)
//                .data(data)
                .deleted(false)
                .build();

        return fileRepository.save(file);
    }

    public File getFileById(final UUID fileId) {
        return getOrThrow(fileRepository.findFileById(fileId));
    }
}
