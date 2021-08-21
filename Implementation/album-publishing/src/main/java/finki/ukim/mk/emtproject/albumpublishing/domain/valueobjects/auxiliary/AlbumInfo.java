package finki.ukim.mk.emtproject.albumpublishing.domain.valueobjects.auxiliary;

import finki.ukim.mk.emtproject.sharedkernel.domain.base.ValueObject;
import lombok.Getter;

import javax.persistence.Embeddable;

/**
 * AlbumInfo - value object that keeps the main information for the album value object
 */
@Embeddable
@Getter
public class AlbumInfo implements ValueObject {

    private String artistName;

    private String producerName;

    private String composerName;

    public static AlbumInfo build(String artistName, String producerName, String composerName) {
        return new AlbumInfo(artistName, producerName, composerName);
    }

    protected AlbumInfo(String artistName, String producerName, String composerName) {
        this.artistName = artistName;
        this.producerName = producerName;
        this.composerName = composerName;
    }

    protected AlbumInfo() {
    }
}
