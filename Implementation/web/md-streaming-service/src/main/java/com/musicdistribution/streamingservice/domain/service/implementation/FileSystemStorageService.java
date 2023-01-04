package com.musicdistribution.streamingservice.domain.service.implementation;

import com.musicdistribution.streamingservice.config.FileProperties;
import com.musicdistribution.streamingservice.constant.ExceptionConstants;
import com.musicdistribution.streamingservice.domain.exception.FileStorageException;
import com.musicdistribution.streamingservice.domain.model.enums.FileLocationType;
import com.musicdistribution.streamingservice.domain.service.IFileSystemStorage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

/**
 * The implementation of the domain service used for file storage manipulation.
 */
@Slf4j
@Service
@AllArgsConstructor
public class FileSystemStorageService implements IFileSystemStorage {

    private final FileProperties fileProperties;

    /**
     * Method used to save a multipart file.
     *
     * @param file         - the file to be saved.
     * @param fileName     - the name of the file which is to be saved.
     * @param locationType - the type of the location in which the file is to be stored.
     */
    @Override
    public void saveFile(MultipartFile file, String fileName, FileLocationType locationType) {
        try {
            Path location = resolveLocation(locationType);
            Path resolvedFile = location.resolve(fileName);
            if (Files.exists(resolvedFile)) {
                throw new FileStorageException(String.format(ExceptionConstants.DUPLICATE_FILE_FAILURE, fileName));
            }

            Files.copy(file.getInputStream(), resolvedFile, StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new FileStorageException(ExceptionConstants.MULTIPART_FILE_FAILURE);
        }
    }

    /**
     * Method used for reading the file size in bytes.
     *
     * @param fileName     - the name of the file whose size is to be read.
     * @param locationType - the type of the location in which the file is stored.
     * @return the size of the specified file in bytes.
     */
    @Override
    public Long loadFileSize(String fileName, FileLocationType locationType) {
        try {
            return Files.size(this.loadFilePath(fileName, locationType));
        } catch (IOException ex) {
            log.error(ex.getMessage());
            ex.printStackTrace();
            return 0L;
        }
    }

    /**
     * Method used to fetch the bytes from the file in the specified byte range.
     *
     * @param filename     - the name of the file whose bytes are to be read from.
     * @param start        - the starting index of the range of the required bytes.
     * @param end          - the ending index of the range of the required bytes.
     * @param locationType - the type of the location in which the file is stored.
     * @return the bytes of the file in the specified range.
     */
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
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new FileStorageException(String.format(ExceptionConstants.FILE_BYTES_LOADING_FAILURE,
                    filename, start, end));
        }
    }

    /**
     * Method used to load a file path given its name and location type.
     *
     * @param fileName     - the name of the file.
     * @param locationType - the type of the location where the file is stored.
     * @return the path of the located file.
     */
    private Path loadFilePath(String fileName, FileLocationType locationType) {
        try {
            Path location = resolveLocation(locationType);
            Path path = location.resolve(fileName).normalize();

            if (Files.exists(path) && Files.isReadable(path)) {
                return path;
            } else {
                throw new FileStorageException(ExceptionConstants.FILE_NOT_FOUND);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new FileStorageException(ExceptionConstants.FILE_LOADING_FAILURE);
        }
    }

    /**
     * Method used to resolve the location path of a file.
     *
     * @param locationType - the type of the location where the file is stored.
     * @return the resolved location of the specified file.
     */
    private Path resolveLocation(FileLocationType locationType) {
        return Optional.ofNullable(getLocation(locationType)).map(location -> {
            try {
                URL resource = FileSystemStorageService.class.getResource("/");
                String directoryPath = String.format("%s/%s", resource, location);
                Path path = Path.of(URI.create(directoryPath));
                Files.createDirectories(path);
                FileSystemStorageService.class.getResource(location);
                return path;
            } catch (Exception e) {
                log.error(e.getMessage());
                throw new FileStorageException(ExceptionConstants.FILES_FOLDER_FAILURE);
            }
        }).orElse(null);
    }

    /**
     * Method used to read the location of the file type.
     *
     * @param locationType - the type of the location where the file is stored.
     * @return the location of the file with the specified type.
     */
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
