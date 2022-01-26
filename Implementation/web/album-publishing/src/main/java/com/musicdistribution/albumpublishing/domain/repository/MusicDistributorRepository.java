package com.musicdistribution.albumpublishing.domain.repository;

import com.musicdistribution.albumpublishing.domain.models.entity.MusicDistributor;
import com.musicdistribution.albumpublishing.domain.models.entity.MusicDistributorId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * JPA Repository for a music distributor.
 */
@Repository
public interface MusicDistributorRepository extends JpaRepository<MusicDistributor, MusicDistributorId> {
}
