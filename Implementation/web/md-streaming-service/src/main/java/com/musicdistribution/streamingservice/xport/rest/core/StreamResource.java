package com.musicdistribution.streamingservice.xport.rest.core;

import com.musicdistribution.sharedkernel.util.ApiController;
import com.musicdistribution.streamingservice.constant.*;
import com.musicdistribution.streamingservice.domain.model.enums.FileLocationType;
import com.musicdistribution.streamingservice.domain.service.IEncryptionSystem;
import com.musicdistribution.streamingservice.domain.service.IFileSystemStorage;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Mono;

import java.util.Optional;

/**
 * Stream Rest Controller.
 */
@ApiController
@AllArgsConstructor
@RequestMapping(PathConstants.API_STREAM)
public class StreamResource {

    private final IFileSystemStorage fileSystemStorage;
    private final IEncryptionSystem encryptionSystem;

    /**
     * Method used to stream an audio file.
     *
     * @param httpRangeList - the range list of the audio to be fetched.
     * @param id            - the ID of the audio to be fetched.
     * @return a response entity with the requested bytes of the audio file.
     */
    @GetMapping(PathConstants.API_AUDIO_STREAM)
    public Mono<ResponseEntity<byte[]>> streamAudio(@RequestHeader(value = ServletConstants.RANGE, required = false) String httpRangeList,
                                                    @PathVariable(EntityConstants.ID) String id) {
        String fileName = String.format("%s.%s", encryptionSystem.decrypt(id), FileConstants.MPEG_EXTENSION);
        return Mono.just(getAudioContent(fileName, httpRangeList));
    }

    /**
     * Method used to fetch the cover picture of a song.
     *
     * @param id - the id of the song cover picture to be fetched.
     * @return a response entity with the requested bytes of the picture.
     */
    @GetMapping(value = PathConstants.API_SONG_COVER,
            produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getSongCover(@PathVariable(EntityConstants.ID) String id) {
        String fileName = String.format("%s.%s", encryptionSystem.decrypt(id), FileConstants.PNG_EXTENSION);
        return getPictureContent(fileName, FileLocationType.SONG_COVERS);
    }

    /**
     * Method used to fetch the cover picture of an album.
     *
     * @param id - the id of the album cover picture to be fetched.
     * @return a response entity with the requested bytes of the picture.
     */
    @GetMapping(value = PathConstants.API_ALBUM_COVER,
            produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getAlbumCover(@PathVariable(EntityConstants.ID) String id) {
        String fileName = String.format("%s.%s", encryptionSystem.decrypt(id), FileConstants.PNG_EXTENSION);
        return getPictureContent(fileName, FileLocationType.ALBUM_COVERS);
    }

    /**
     * Method used to fetch the cover picture of an artist.
     *
     * @param id - the id of the artist cover picture to be fetched.
     * @return a response entity with the requested bytes of the picture.
     */
    @GetMapping(value = PathConstants.API_ARTIST_COVER,
            produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getArtistPicture(@PathVariable(EntityConstants.ID) String id) {
        String fileName = String.format("%s.%s", encryptionSystem.decrypt(id), FileConstants.PNG_EXTENSION);
        return getPictureContent(fileName, FileLocationType.ARTIST_PROFILE_PICTURE);
    }

    /**
     * Method used to fetch the adequate byte array from the audio file.
     *
     * @param fileName      - the name of the audio file to fetched.
     * @param httpRangeList - the range list of the audio to be fetched.
     * @return the requested byte array from the audio file.
     */
    private ResponseEntity<byte[]> getAudioContent(String fileName, String httpRangeList) {
        Long fileSize = fileSystemStorage.loadFileSize(fileName, FileLocationType.SONGS);

        return Optional.ofNullable(httpRangeList).map(range -> {
            String[] ranges = range.split(AlphabetConstants.SCORE);
            long rangeStart = Long.parseLong(ranges[0].substring(6));
            long rangeEnd = ((ranges.length > 1)) ? Long.parseLong(ranges[1]) : fileSize - 1;
            rangeEnd = ((fileSize < rangeEnd)) ? fileSize - 1 : rangeEnd;
            String contentLength = String.valueOf((rangeEnd - rangeStart) + 1);

            return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
                    .header(ServletConstants.CONTENT_TYPE, ServletConstants.AUDIO_CONTENT_TYPE)
                    .header(ServletConstants.ACCEPT_RANGES, ServletConstants.BYTES)
                    .header(ServletConstants.CONTENT_LENGTH, contentLength)
                    .header(ServletConstants.CONTENT_RANGE, String.format("%s %s-%s/%s",
                            ServletConstants.BYTES, rangeStart, rangeEnd, fileSize))
                    .body(fileSystemStorage.loadFileByteRange(fileName, rangeStart, rangeEnd, FileLocationType.SONGS));
        }).orElse(ResponseEntity.status(HttpStatus.OK)
                .header(ServletConstants.CONTENT_TYPE, ServletConstants.AUDIO_CONTENT_TYPE)
                .header(ServletConstants.CONTENT_LENGTH, String.valueOf(fileSize))
                .body(fileSystemStorage.loadFileByteRange(fileName, 0, fileSize - 1, FileLocationType.SONGS)));
    }

    /**
     * Method used to read the content of the requested picture file.
     *
     * @param fileName     - the name of the image file to fetched.
     * @param locationType - the type of the image file to be loaded.
     * @return the requested byte array from the image.
     */
    private ResponseEntity<byte[]> getPictureContent(String fileName, FileLocationType locationType) {
        return Optional.ofNullable(fileSystemStorage.loadFileSize(fileName, locationType))
                .filter(size -> size != 0).map(size -> ResponseEntity.status(HttpStatus.OK)
                        .header(ServletConstants.CONTENT_TYPE, MediaType.IMAGE_PNG_VALUE)
                        .header(ServletConstants.CONTENT_LENGTH, String.valueOf(size))
                        .body(fileSystemStorage.loadFileByteRange(fileName, 0, size - 1,
                                locationType))).orElse(ResponseEntity.badRequest().build());
    }
}
