package pl.mateusz.swap_items_backend.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.mateusz.swap_items_backend.entities.SystemFile;
import pl.mateusz.swap_items_backend.repositories.AdvertisementRepository;
import pl.mateusz.swap_items_backend.repositories.SystemFileRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Stream;

import static pl.mateusz.swap_items_backend.others.Constants.allowedImageExtensions;
import static pl.mateusz.swap_items_backend.others.Messages.NOT_ALLOWED_EXTENSION;
import static pl.mateusz.swap_items_backend.utils.Utils.getFileExtension;
import static pl.mateusz.swap_items_backend.utils.Utils.getOrThrow;

@Service
@RequiredArgsConstructor
public class SystemFileService {

    private final SystemFileRepository systemFileRepository;

    private final AdvertisementRepository advertisementRepository;

    @Value("${file.storage.directory}")
    private String storageDirectory;

    public Set<SystemFile> prepareSystemFiles(final List<MultipartFile> files) {
        final Set<SystemFile> systemFileSet = new HashSet<>();

        files.forEach(file -> {
            try {
                final UUID fileId = UUID.randomUUID();
                final String filePath = saveFile(file, fileId);

                final SystemFile systemFileEntity = SystemFile.builder()
                        .id(fileId)
                        .originalName(file.getOriginalFilename())
                        .type(file.getContentType())
                        .path(filePath)
                        .mimeType(file.getContentType())
                        .size(file.getSize())
                        .saved(true)
                        .build();

                systemFileSet.add(systemFileEntity);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        return new HashSet<>(systemFileRepository.saveAll(systemFileSet));
    }

    private String saveFile(final MultipartFile file, final UUID fileId) throws IOException {
        final String fileExtension = getFileExtension(file.getOriginalFilename());
        if (!allowedImageExtensions.contains(fileExtension))
            throw new RuntimeException(NOT_ALLOWED_EXTENSION);

        final String fileName = fileId + fileExtension;
        final Path filePath = Paths.get(storageDirectory).resolve(fileName);
        Files.copy(file.getInputStream(), filePath);
        return filePath.toString();
    }

    public byte[] getFileBySystemFileId(final UUID id) {
        final String idString = id.toString();
        final File directory = new File(storageDirectory);

        if (directory.exists() && directory.isDirectory()) {
            final File[] listOfFiles = directory.listFiles();

            if (listOfFiles != null) {
                File fileToReturn = java.util.Arrays.stream(listOfFiles)
                        .filter(file -> file.getName().contains(idString))
                        .findFirst()
                        .orElse(null);

                if (fileToReturn != null) {
                    try {
                        Path filePath = Paths.get(fileToReturn.getAbsolutePath());
                        return Files.readAllBytes(filePath);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        return null;
    }


    public SystemFile saveFile1(final MultipartFile multipartFile) throws IOException {
        final String originalName = multipartFile.getOriginalFilename();
        final String extension =  originalName.substring(originalName.lastIndexOf('.') + 1);
        final Long size = multipartFile.getSize();
        final LocalDateTime uploadDateTime = LocalDateTime.now();
        final byte[] data = multipartFile.getBytes();

        final SystemFile systemFile = SystemFile.builder()
                .originalName(originalName)
//                .extension(extension)
                .size(size)
//                .uploadDateTime(uploadDateTime)
//                .data(data)
                .saved(true)
                .build();

        return systemFileRepository.save(systemFile);
    }

    public SystemFile getFileById(final UUID fileId) {
        return getOrThrow(systemFileRepository.findFileById(fileId));
    }
}
