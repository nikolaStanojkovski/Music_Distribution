package finki.ukim.mk.emtproject.albumpublishing.services.impl;

import finki.ukim.mk.emtproject.albumpublishing.domain.exceptions.MusicDistributorNotFoundException;
import finki.ukim.mk.emtproject.albumpublishing.domain.exceptions.PublishedAlbumNotFoundException;
import finki.ukim.mk.emtproject.albumpublishing.domain.models.MusicDistributor;
import finki.ukim.mk.emtproject.albumpublishing.domain.models.MusicDistributorId;
import finki.ukim.mk.emtproject.albumpublishing.domain.models.PublishedAlbum;
import finki.ukim.mk.emtproject.albumpublishing.domain.models.PublishedAlbumId;
import finki.ukim.mk.emtproject.albumpublishing.domain.models.dto.MusicDistributorDto;
import finki.ukim.mk.emtproject.albumpublishing.domain.repository.MusicDistributorRepository;
import finki.ukim.mk.emtproject.albumpublishing.domain.repository.PublishedAlbumRepository;
import finki.ukim.mk.emtproject.albumpublishing.domain.valueobjects.DistributorInfo;
import finki.ukim.mk.emtproject.albumpublishing.services.MusicDistributorService;
import finki.ukim.mk.emtproject.albumpublishing.services.form.AlbumPublishForm;
import finki.ukim.mk.emtproject.albumpublishing.services.form.MusicDistributorForm;
import finki.ukim.mk.emtproject.albumpublishing.services.form.PublishedAlbumForm;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class MusicDistributorServiceImpl implements MusicDistributorService {

    private MusicDistributorRepository musicDistributorRepository;
    private PublishedAlbumRepository publishedAlbumRepository;

    @Override
    public List<MusicDistributorDto> findAll() {
        return musicDistributorRepository.findAll().stream().map(i ->
                new MusicDistributorDto(i.getId().getId(), i.getDistributorInfo().getDistributorInformation(),
                        i.getDistributorInfo(), i.totalEarnings(), i.getPublishedAlbums().size()))
                .collect(Collectors.toList());
    }

    @Override
    public MusicDistributor findById(MusicDistributorId id) {
        return musicDistributorRepository.findById(id)
                .orElseThrow(() -> new MusicDistributorNotFoundException(id));
    }

    @Override
    public MusicDistributor createDistributor(MusicDistributorForm form) {
        MusicDistributor newDistributor = MusicDistributor.build(DistributorInfo.build(form.getCompanyName(),
                form.getDistributorName()));
        musicDistributorRepository.save(newDistributor);
        return newDistributor;
    }

    @Override
    public Optional<PublishedAlbum> publishAlbum(AlbumPublishForm albumPublishForm) {
        return Optional.empty();
    }

    @Override
    public Optional<PublishedAlbum> unpublishAlbum(PublishedAlbumId publishedAlbumId) {
        return Optional.empty();
    }

    @Override
    public PublishedAlbum subscribeAlbum(PublishedAlbumForm form) {
        MusicDistributor musicDistributor = findById(form.getMusicDistributorId());
        PublishedAlbum newPublishedAlbum = PublishedAlbum.build(form.getAlbum(), form.getArtist(),
                musicDistributor, form.getSubscriptionFee(), form.getAlbumTier(), form.getTransactionFee());
        publishedAlbumRepository.save(newPublishedAlbum);

        musicDistributor.subscribeAlbum(newPublishedAlbum);
        musicDistributorRepository.save(musicDistributor);

        return newPublishedAlbum;
    }

    @Override
    public PublishedAlbum unsubscribeAlbum(PublishedAlbumId publishedAlbumId) {
        PublishedAlbum publishedAlbum = publishedAlbumRepository.findById(publishedAlbumId)
                .orElseThrow(() -> new PublishedAlbumNotFoundException(publishedAlbumId));
        publishedAlbumRepository.delete(publishedAlbum);

        MusicDistributor musicDistributor = findById(publishedAlbum.getPublisher().getId());
        musicDistributor.unsubscribeAlbum(publishedAlbum);
        musicDistributorRepository.save(musicDistributor);

        return publishedAlbum;
    }
}
