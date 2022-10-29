package com.musicdistribution.albumcatalog.xport.rest.core;

import com.musicdistribution.albumcatalog.domain.models.enums.FileLocationType;
import com.musicdistribution.albumcatalog.domain.services.IEncryptionSystem;
import com.musicdistribution.albumcatalog.domain.services.IFileSystemStorage;
import com.musicdistribution.sharedkernel.util.ApiController;
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
 * Song Rest Controller.
 */
@ApiController
@AllArgsConstructor
@RequestMapping("/api/resource/stream")
public class StreamResource {

    private final IFileSystemStorage fileSystemStorage;
    private final IEncryptionSystem encryptionSystem;

    @GetMapping("/{id}.mp3")
    public Mono<ResponseEntity<byte[]>> streamAudio(@RequestHeader(value = "Range", required = false) String httpRangeList,
                                                    @PathVariable("id") String id) {
        String fileName = String.format("%s.mp3", encryptionSystem.decrypt(id));
        return Mono.just(getAudioContent(fileName, httpRangeList));
    }

    @GetMapping(value = "/songs/{id}.png", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getSongCover(@PathVariable("id") String id) {
        String fileName = String.format("%s.png", encryptionSystem.decrypt(id));
        return getPictureContent(fileName, FileLocationType.SONG_COVERS);
    }

    @GetMapping(value = "/albums/{id}.png", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getAlbumCover(@PathVariable("id") String id) {
        String fileName = String.format("%s.png", encryptionSystem.decrypt(id));
        return getPictureContent(fileName, FileLocationType.ALBUM_COVERS);
    }

    @GetMapping(value = "/artists/{id}.png", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getArtistPicture(@PathVariable("id") String id) {
        String fileName = String.format("%s.png", encryptionSystem.decrypt(id));
        return getPictureContent(fileName, FileLocationType.ARTIST_PROFILE_PICTURE);
    }

    private ResponseEntity<byte[]> getAudioContent(String fileName, String httpRangeList) {
        Long fileSize = fileSystemStorage.loadFileSize(fileName, FileLocationType.SONGS);

        return Optional.ofNullable(httpRangeList).map(range -> {
            String[] ranges = range.split("-");
            long rangeStart = Long.parseLong(ranges[0].substring(6));
            long rangeEnd = ((ranges.length > 1)) ? Long.parseLong(ranges[1]) : fileSize - 1;
            rangeEnd = ((fileSize < rangeEnd)) ? fileSize - 1 : rangeEnd;
            String contentLength = String.valueOf((rangeEnd - rangeStart) + 1);

            return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
                    .header("Content-Type", "audio/mpeg")
                    .header("Accept-Ranges", "bytes")
                    .header("Content-Length", contentLength)
                    .header("Content-Range", "bytes" + " " + rangeStart + "-" + rangeEnd + "/" + fileSize)
                    .body(fileSystemStorage.loadFileByteRange(fileName, rangeStart, rangeEnd, FileLocationType.SONGS));
        }).orElse(ResponseEntity.status(HttpStatus.OK)
                .header("Content-Type", "audio/mpeg")
                .header("Content-Length", String.valueOf(fileSize))
                .body(fileSystemStorage.loadFileByteRange(fileName, 0, fileSize - 1, FileLocationType.SONGS)));
    }

    private ResponseEntity<byte[]> getPictureContent(String fileName, FileLocationType locationType) {
        return Optional.ofNullable(fileSystemStorage.loadFileSize(fileName, locationType))
                .filter(size -> size != 0).map(size -> ResponseEntity.status(HttpStatus.OK)
                        .header("Content-Type", "image/png")
                        .header("Content-Length", String.valueOf(size))
                        .body(fileSystemStorage.loadFileByteRange(fileName, 0, size - 1, locationType))).orElse(ResponseEntity.badRequest().build());
    }
}
