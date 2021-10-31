package finki.ukim.mk.emtproject.albumcatalog.domain.models.request;

import finki.ukim.mk.emtproject.albumcatalog.domain.models.Album;
import finki.ukim.mk.emtproject.sharedkernel.domain.valueobjects.auxiliary.Genre;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * An album object used to transfer data from the front-end user form to the backend.
 */
@Data
@NoArgsConstructor
public class AlbumRequest {

    @NotBlank
    private String albumName;
    @NotBlank
    private Integer totalLength;
    @NotBlank
    private Boolean isPublished;
    @NotNull
    private Genre genre;

    @NotBlank
    private String artistName;
    @NotBlank
    private String producerName;
    @NotBlank
    private String composerName;

    private String creatorId;

    /**
     * Static method used for creation of a new album form object.
     *
     * @return the new album form object.
     */
    public static AlbumRequest build(Album album) {
        AlbumRequest albumRequest = new AlbumRequest();
        albumRequest.setAlbumName(album.getAlbumName());
        albumRequest.setTotalLength(album.getTotalLength().getLengthInSeconds());
        albumRequest.setIsPublished(Boolean.FALSE);
        albumRequest.setArtistName(album.getAlbumInfo().getArtistName());
        albumRequest.setProducerName(album.getAlbumInfo().getProducerName());
        albumRequest.setComposerName(album.getAlbumInfo().getComposerName());
        albumRequest.setGenre(album.getGenre());
        albumRequest.setCreatorId(Objects.isNull(album.getCreator()) ? null : album.getCreator().getId().getId());

        return albumRequest;
    }
}
