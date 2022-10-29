package com.musicdistribution.albumcatalog.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class FileProperties {

    @Value("${file.songs.upload.location}")
    private String songsLocation;

    @Value("${file.cover-songs.upload.location}")
    private String songCoversLocation;

    @Value("${file.cover-albums.upload.location}")
    private String albumCoversLocation;

    @Value("${file.profile-pictures.upload.location}")
    private String profilePicturesLocation;

}
