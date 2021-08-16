package finki.ukim.mk.emtproject.albumpublishing.domain.repository;

import finki.ukim.mk.emtproject.albumpublishing.domain.models.MusicDistributor;
import finki.ukim.mk.emtproject.albumpublishing.domain.models.MusicDistributorId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MusicDistributorRepository extends JpaRepository<MusicDistributor, MusicDistributorId> {
}
