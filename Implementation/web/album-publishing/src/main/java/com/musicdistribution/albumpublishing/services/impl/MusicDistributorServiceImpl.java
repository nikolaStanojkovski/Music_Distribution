package com.musicdistribution.albumpublishing.services.impl;

import com.musicdistribution.albumpublishing.domain.exceptions.MusicDistributorNotFoundException;
import com.musicdistribution.albumpublishing.domain.exceptions.PublishedAlbumNotFoundException;
import com.musicdistribution.albumpublishing.domain.models.entity.MusicDistributor;
import com.musicdistribution.albumpublishing.domain.models.entity.MusicDistributorId;
import com.musicdistribution.albumpublishing.domain.models.entity.PublishedAlbum;
import com.musicdistribution.albumpublishing.domain.models.entity.PublishedAlbumId;
import com.musicdistribution.albumpublishing.domain.models.request.MusicDistributorRequest;
import com.musicdistribution.albumpublishing.domain.models.request.PublishedAlbumRequest;
import com.musicdistribution.albumpublishing.domain.repository.MusicDistributorRepository;
import com.musicdistribution.albumpublishing.domain.repository.PublishedAlbumRepository;
import com.musicdistribution.albumpublishing.domain.valueobjects.AlbumId;
import com.musicdistribution.albumpublishing.domain.valueobjects.ArtistId;
import com.musicdistribution.albumpublishing.domain.valueobjects.DistributorInfo;
import com.musicdistribution.albumpublishing.services.MusicDistributorService;
import com.musicdistribution.sharedkernel.domain.events.publishedAlbums.AlbumPublishedEvent;
import com.musicdistribution.sharedkernel.domain.events.publishedAlbums.AlbumUnpublishedEvent;
import com.musicdistribution.sharedkernel.domain.events.publishedAlbums.RaisedAlbumTierEvent;
import com.musicdistribution.sharedkernel.domain.valueobjects.Money;
import com.musicdistribution.sharedkernel.domain.valueobjects.auxiliary.Currency;
import com.musicdistribution.sharedkernel.infra.DomainEventPublisher;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * MusicDistributorServiceImpl - Service for the implementation of the main specific business logic for the music distributors
 */
@Service
@Transactional
@AllArgsConstructor
public class MusicDistributorServiceImpl implements MusicDistributorService {

    private final MusicDistributorRepository musicDistributorRepository;
    private final PublishedAlbumRepository publishedAlbumRepository;
    private final DomainEventPublisher domainEventPublisher;

    private static PublishedAlbum subscribeAlbum(PublishedAlbumRequest publishedAlbumRequest, PublishedAlbumRepository publishedAlbumRepository, MusicDistributorRepository musicDistributorRepository) {
        MusicDistributor musicDistributor = musicDistributorRepository.findById(MusicDistributorId.of(publishedAlbumRequest.getMusicPublisherId()))
                .orElseThrow(() -> new MusicDistributorNotFoundException(MusicDistributorId.of(publishedAlbumRequest.getMusicPublisherId())));
        PublishedAlbum newPublishedAlbum = PublishedAlbum.build(AlbumId.of(publishedAlbumRequest.getAlbumId()), publishedAlbumRequest.getAlbumName(), ArtistId.of(publishedAlbumRequest.getArtistId()),
                publishedAlbumRequest.getArtistInformation(), musicDistributor, Money.valueOf(Currency.EUR, publishedAlbumRequest.getSubscriptionFee()), publishedAlbumRequest.getAlbumTier(),
                Money.valueOf(Currency.EUR, publishedAlbumRequest.getTransactionFee()));
        publishedAlbumRepository.save(newPublishedAlbum);

        musicDistributor.subscribeAlbum(newPublishedAlbum);
        musicDistributorRepository.save(musicDistributor);

        return newPublishedAlbum;
    }

    private static PublishedAlbum unsubscribeAlbum(PublishedAlbumId publishedAlbumId, PublishedAlbumRepository publishedAlbumRepository, MusicDistributorRepository musicDistributorRepository) {
        PublishedAlbum publishedAlbum = publishedAlbumRepository.findById(publishedAlbumId)
                .orElseThrow(() -> new PublishedAlbumNotFoundException(publishedAlbumId));
        MusicDistributor musicDistributor = musicDistributorRepository.findById(MusicDistributorId.of(publishedAlbum.getPublisher().getId().getId()))
                .orElseThrow(() -> new MusicDistributorNotFoundException(MusicDistributorId.of(publishedAlbum.getPublisher().getId().getId())));
        publishedAlbumRepository.delete(publishedAlbum);

        musicDistributor.unsubscribeAlbum(publishedAlbum);
        musicDistributorRepository.save(musicDistributor);

        return publishedAlbum;
    }

    @Override
    public List<MusicDistributor> findAll() {
        return musicDistributorRepository.findAll();
    }

    @Override
    public void createDistributor(MusicDistributorRequest form) {
        musicDistributorRepository.save(MusicDistributor.build(DistributorInfo.build(form.getCompanyName(),
                form.getDistributorName())));
    }

    @Override
    public Optional<PublishedAlbum> publishAlbum(PublishedAlbumRequest publishedAlbumRequest) {

        PublishedAlbum publishedAlbum = subscribeAlbum(publishedAlbumRequest, publishedAlbumRepository, musicDistributorRepository);

        domainEventPublisher.publish(new AlbumPublishedEvent(publishedAlbum.getAlbumId().getId(),
                publishedAlbum.getArtistId().getId(), publishedAlbum.getPublisher().getId().getId(),
                publishedAlbum.getAlbumTier().toString(), publishedAlbum.getSubscriptionFee().getAmount(),
                publishedAlbum.getTransactionFee().getAmount()));
        return Optional.of(publishedAlbum);
    }

    @Override
    public Optional<PublishedAlbum> unPublishAlbum(PublishedAlbumId publishedAlbumId) {
        PublishedAlbum removedPublishedAlbum = unsubscribeAlbum(publishedAlbumId, publishedAlbumRepository, musicDistributorRepository);

        domainEventPublisher.publish(new AlbumUnpublishedEvent(removedPublishedAlbum.getAlbumId().getId()));

        return Optional.of(removedPublishedAlbum);
    }

    @Override
    public Optional<PublishedAlbum> unPublishAlbumByAlbumId(AlbumId albumId) {
        Optional<PublishedAlbum> publishedAlbum = publishedAlbumRepository.findByAlbumId(albumId);
        publishedAlbum.ifPresent(album -> unPublishAlbum(album.getId()));
        return publishedAlbum;
    }

    @Override
    public Optional<PublishedAlbum> raiseAlbumTier(PublishedAlbumRequest publishedAlbumRequest) {
        PublishedAlbum publishedAlbum = publishedAlbumRepository.findById(PublishedAlbumId.of(publishedAlbumRequest.getPublishedAlbumId()))
                .orElseThrow(() -> new PublishedAlbumNotFoundException(PublishedAlbumId.of(publishedAlbumRequest.getPublishedAlbumId())));
        publishedAlbum.raiseAlbumTier(publishedAlbumRequest.getAlbumTier(), publishedAlbumRequest.getSubscriptionFee(), publishedAlbumRequest.getTransactionFee());

        domainEventPublisher.publish(new RaisedAlbumTierEvent(publishedAlbum.getId().getId(), publishedAlbumRequest.getAlbumTier().toString()));

        return Optional.of(publishedAlbumRepository.save(publishedAlbum));
    }
}
