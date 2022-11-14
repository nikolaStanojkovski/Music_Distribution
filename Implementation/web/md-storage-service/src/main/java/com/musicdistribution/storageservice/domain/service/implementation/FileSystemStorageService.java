package com.musicdistribution.storageservice.domain.service.implementation;

import com.musicdistribution.storageservice.config.FileProperties;
import com.musicdistribution.storageservice.domain.exception.FileStorageException;
import com.musicdistribution.storageservice.domain.model.enums.FileLocationType;
import com.musicdistribution.storageservice.domain.service.IFileSystemStorage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

@Service
@AllArgsConstructor
public class FileSystemStorageService implements IFileSystemStorage {
    private final FileProperties fileProperties;

    @Override
    public String saveFile(MultipartFile file, String fileName, FileLocationType locationType) {
        try {
            Path location = resolveLocation(locationType);
            Path resolvedFile = location.resolve(fileName);
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
    public Long loadFileSize(String fileName, FileLocationType locationType) {
        try {
            return Files.size(this.loadFilePath(fileName, locationType));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return 0L;
    }

    @Override
    public byte[] loadFileByteRange(String filename, long start, long end, FileLocationType locationType) {
        Path path = this.loadFilePath(filename, locationType);
        try (InputStream inputStream = (Files.newInputStream(path));
             ByteArrayOutputStream bufferedOutputStream = new ByteArrayOutputStream()) {
            byte[] data = new byte[128];
            int nRead;
            while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
                bufferedOutputStream.write(data, 0, nRead);
            }
            bufferedOutputStream.flush();
            byte[] result = new byte[(int) (end - start) + 1];
            System.arraycopy(bufferedOutputStream.toByteArray(), (int) start, result, 0, result.length);
            return result;
        } catch (IOException exception) {
            exception.printStackTrace();
            throw new FileStorageException("The bytes from the file: " + filename +
                    " with start: " + start + " and end: " + end + "have not been loaded");
        }
    }

    private Path loadFilePath(String fileName, FileLocationType locationType) {
        try {
            Path location = resolveLocation(locationType);
            Path path = location.resolve(fileName).normalize();

            if (Files.exists(path) && Files.isReadable(path)) {
                return path;
            } else {
                throw new FileStorageException("Could not find the file with the specified file name.");
            }
        } catch (Exception e) {
            throw new FileStorageException("Could not load the file with the specified file name.");
        }
    }

    private Path resolveLocation(FileLocationType locationType) {
        return Optional.ofNullable(getLocation(locationType)).map(location -> {
            try {
                URL resource = FileSystemStorageService.class.getResource("/");
                String directoryPath = String.format("%s/%s", resource, location);
                Path path = Path.of(URI.create(directoryPath));
                Files.createDirectories(path);
                FileSystemStorageService.class.getResource(location);
                return path;
            } catch (Exception ex) {
                throw new FileStorageException("Could not create the files folder.");
            }
        }).orElse(null);
    }

    private String getLocation(FileLocationType locationType) {
        switch (locationType) {
            case SONGS:
                return this.fileProperties.getSongsLocation();
            case SONG_COVERS:
                return this.fileProperties.getSongCoversLocation();
            case ALBUM_COVERS:
                return this.fileProperties.getAlbumCoversLocation();
            case ARTIST_PROFILE_PICTURE:
                return this.fileProperties.getProfilePicturesLocation();
            default:
                return null;
        }
    }
}
