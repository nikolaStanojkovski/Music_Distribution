package finki.ukim.mk.emtproject.albumpublishing.services.impl;

import finki.ukim.mk.emtproject.albumpublishing.domain.exceptions.MusicDistributorNotFoundException;
import finki.ukim.mk.emtproject.albumpublishing.domain.exceptions.PublishedAlbumNotFoundException;
import finki.ukim.mk.emtproject.albumpublishing.domain.models.MusicDistributor;
import finki.ukim.mk.emtproject.albumpublishing.domain.models.MusicDistributorId;
import finki.ukim.mk.emtproject.albumpublishing.domain.models.PublishedAlbum;
import finki.ukim.mk.emtproject.albumpublishing.domain.models.PublishedAlbumId;
import finki.ukim.mk.emtproject.albumpublishing.domain.models.dto.MusicDistributorDto;
import finki.ukim.mk.emtproject.albumpublishing.domain.models.dto.PublishedAlbumDto;
import finki.ukim.mk.emtproject.albumpublishing.domain.repository.MusicDistributorRepository;
import finki.ukim.mk.emtproject.albumpublishing.domain.repository.PublishedAlbumRepository;
import finki.ukim.mk.emtproject.albumpublishing.domain.valueobjects.AlbumId;
import finki.ukim.mk.emtproject.albumpublishing.domain.valueobjects.ArtistId;
import finki.ukim.mk.emtproject.albumpublishing.domain.valueobjects.DistributorInfo;
import finki.ukim.mk.emtproject.albumpublishing.services.MusicDistributorService;
import finki.ukim.mk.emtproject.albumpublishing.services.form.MusicDistributorForm;
import finki.ukim.mk.emtproject.sharedkernel.domain.events.publishedAlbums.AlbumPublishedEvent;
import finki.ukim.mk.emtproject.sharedkernel.domain.events.publishedAlbums.AlbumUnpublishedEvent;
import finki.ukim.mk.emtproject.sharedkernel.domain.events.publishedAlbums.RaisedAlbumTierEvent;
import finki.ukim.mk.emtproject.sharedkernel.domain.valueobjects.Money;
import finki.ukim.mk.emtproject.sharedkernel.domain.valueobjects.auxiliary.Currency;
import finki.ukim.mk.emtproject.sharedkernel.infra.DomainEventPublisher;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

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
    private final Validator validator;

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
    public Optional<PublishedAlbum> publishAlbum(PublishedAlbumDto publishedAlbumDto) {
        Objects.requireNonNull(publishedAlbumDto, "album to be published must not be null.");
        var constraintViolations = validator.validate(publishedAlbumDto);
        if (constraintViolations.size() > 0) {
            throw new ConstraintViolationException("The oublished album form is not valid", constraintViolations);
        }

        PublishedAlbum newPublishedAlbum = subscribeAlbum(publishedAlbumDto);
        domainEventPublisher.publish(new AlbumPublishedEvent(newPublishedAlbum.getAlbumId().getId(),
                newPublishedAlbum.getArtistId().getId(), newPublishedAlbum.getPublisher().getId().getId(),
                newPublishedAlbum.getAlbumTier().toString(), newPublishedAlbum.getSubscriptionFee().getAmount(),
                newPublishedAlbum.getTransactionFee().getAmount()));
        return Optional.of(newPublishedAlbum);
    }

    @Override
    public Optional<PublishedAlbum> unpublishAlbum(PublishedAlbumId publishedAlbumId) {
        PublishedAlbum removedPublishedAlbum = unsubscribeAlbum(publishedAlbumId);
        domainEventPublisher.publish(new AlbumUnpublishedEvent(removedPublishedAlbum.getAlbumId().getId()));
        return Optional.of(removedPublishedAlbum);
    }

    @Override
    public Optional<PublishedAlbum> raiseAlbumTier(PublishedAlbumDto form) {
        PublishedAlbum publishedAlbum = publishedAlbumRepository.findById(PublishedAlbumId.of(form.getPublishedAlbumId()))
                .orElseThrow(() -> new PublishedAlbumNotFoundException(PublishedAlbumId.of(form.getPublishedAlbumId())));
        publishedAlbum.raiseAlbumTier(form.getAlbumTier(), form.getSubscriptionFee(), form.getTransactionFee());
        publishedAlbumRepository.save(publishedAlbum);

        domainEventPublisher.publish(new RaisedAlbumTierEvent(publishedAlbum.getId().getId(), form.getAlbumTier().toString()));

        return Optional.of(publishedAlbum);
    }


    @Override
    public PublishedAlbum subscribeAlbum(PublishedAlbumDto form) {
        MusicDistributor musicDistributor = findById(MusicDistributorId.of(form.getMusicPublisherId()));
        PublishedAlbum newPublishedAlbum = PublishedAlbum.build(AlbumId.of(form.getAlbumId()), form.getAlbumName(), ArtistId.of(form.getArtistId()),
                form.getArtistInformation(), musicDistributor, Money.valueOf(Currency.EUR, form.getSubscriptionFee()), form.getAlbumTier(),
                Money.valueOf(Currency.EUR, form.getTransactionFee()));
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
