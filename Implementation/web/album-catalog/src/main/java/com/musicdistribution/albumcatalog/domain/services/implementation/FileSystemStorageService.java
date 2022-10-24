package com.musicdistribution.albumcatalog.domain.services.implementation;

import com.musicdistribution.albumcatalog.config.FileProperties;
import com.musicdistribution.albumcatalog.domain.exceptions.FileStorageException;
import com.musicdistribution.albumcatalog.domain.services.IFileSystemStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@Service
public class FileSystemStorageService implements IFileSystemStorage {
    private final Path dirLocation;

    @Autowired
    public FileSystemStorageService(FileProperties fileProperties) {
        try {
            URL resource = FileSystemStorageService.class.getResource("/");
            String directoryPath = String.format("%s/%s", resource, fileProperties.getLocation());
            Path path = Path.of(URI.create(directoryPath));
            Files.createDirectories(path);
            FileSystemStorageService.class.getResource(fileProperties.getLocation());

            this.dirLocation = path;
        } catch (Exception ex) {
            throw new FileStorageException("Could not create the tenant files folder.");
        }
    }

    @Override
    public String saveFile(MultipartFile file, String fileName) {
        try {
            Path resolvedFile = this.dirLocation.resolve(fileName);
            if (Files.exists(resolvedFile)) {
                throw new FileStorageException("File already exists with a name " + fileName);
            }

            Files.copy(file.getInputStream(), resolvedFile, StandardCopyOption.REPLACE_EXISTING);
            return fileName;
        } catch (Exception e) {
            throw new FileStorageException("Could not save a new multipart file.");
        }
    }

    @Override
    public byte[] loadFile(String fileName) {
        try {
            Path path = this.dirLocation.resolve(fileName).normalize();

            if (Files.exists(path) && Files.isReadable(path)) {
                return Files.readAllBytes(path);
            } else {
                throw new FileStorageException("Could not find the file with the specified file name.");
            }
        } catch (Exception e) {
            throw new FileStorageException("Could not load the file with the specified file name.");
        }
    }
}
