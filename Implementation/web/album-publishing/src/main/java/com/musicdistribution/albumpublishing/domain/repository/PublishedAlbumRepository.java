package com.musicdistribution.albumpublishing.domain.repository;

import com.musicdistribution.albumpublishing.domain.models.entity.PublishedAlbum;
import com.musicdistribution.albumpublishing.domain.models.entity.PublishedAlbumId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * JPA Repository for a published album.
 */
@Repository
public interface PublishedAlbumRepository extends JpaRepository<PublishedAlbum, PublishedAlbumId> {
}
