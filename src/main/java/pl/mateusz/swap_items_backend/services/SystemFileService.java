package pl.mateusz.swap_items_backend.services;

import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.mateusz.swap_items_backend.entities.SystemFile;
import pl.mateusz.swap_items_backend.repositories.SystemFileRepository;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static pl.mateusz.swap_items_backend.others.Constants.allowedImageExtensions;
import static pl.mateusz.swap_items_backend.others.Messages.NOT_ALLOWED_EXTENSION;
import static pl.mateusz.swap_items_backend.utils.Utils.getFileExtension;
import static pl.mateusz.swap_items_backend.utils.Utils.getOrThrow;

@Service
@RequiredArgsConstructor
public class SystemFileService {

    private final SystemFileRepository systemFileRepository;

    @Value("${file.storage.directory}")
    private String storageDirectory;

    public Set<SystemFile> prepareSystemFiles(final List<MultipartFile> files) {
        return IntStream.range(0, files.size())
                .mapToObj(index -> {
                    final MultipartFile file = files.get(index);
                    final UUID fileId = UUID.randomUUID();
                    final String filePath = saveFile(file, fileId);

                    return SystemFile.builder()
                            .id(fileId)
                            .originalName(file.getOriginalFilename())
                            .type(file.getContentType())
                            .path(filePath)
                            .mimeType(file.getContentType())
                            .size(file.getSize())
                            .saved(true)
                            .fileOrder(index)
                            .build();
                })
                .collect(Collectors.collectingAndThen(
                        Collectors.toSet(),
                        systemFileSet -> new HashSet<>(systemFileRepository.saveAll(systemFileSet))
                ));
    }

    private String saveFile(final MultipartFile file, final UUID fileId) {
        final String fileExtension = getFileExtension(file.getOriginalFilename());
        if (!allowedImageExtensions.contains(fileExtension))
            throw new RuntimeException(NOT_ALLOWED_EXTENSION);

        final String fileName = fileId + fileExtension;
        final Path filePath = Paths.get(storageDirectory).resolve(fileName);

        Try.run(() -> Files.copy(file.getInputStream(), filePath)).onFailure(RuntimeException::new);
        return filePath.toString();
    }

    public byte[] getFileBySystemFileId(final UUID id) {
        final String idString = id.toString();
        final File directory = new File(storageDirectory);

        final File fileToReturn = Arrays.stream(directory.listFiles())
                .filter(file -> file.getName().contains(idString))
                .findFirst()
                .orElse(null);

        return Try.of(() -> Files.readAllBytes(Paths.get(fileToReturn.getAbsolutePath())))
                .getOrElseThrow((ex) -> new RuntimeException(ex));
    }

    public SystemFile getFileById(final UUID fileId) {
        return getOrThrow(systemFileRepository.findFileById(fileId));
    }
}
