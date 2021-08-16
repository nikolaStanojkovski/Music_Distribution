package finki.ukim.mk.emtproject.albumpublishing.services.impl;

import finki.ukim.mk.emtproject.albumpublishing.domain.exceptions.PublishedAlbumNotFoundException;
import finki.ukim.mk.emtproject.albumpublishing.domain.models.PublishedAlbum;
import finki.ukim.mk.emtproject.albumpublishing.domain.models.PublishedAlbumId;
import finki.ukim.mk.emtproject.albumpublishing.domain.models.dto.PublishedAlbumDto;
import finki.ukim.mk.emtproject.albumpublishing.domain.repository.PublishedAlbumRepository;
import finki.ukim.mk.emtproject.albumpublishing.services.PublishedAlbumService;
import finki.ukim.mk.emtproject.albumpublishing.services.form.PublishedAlbumForm;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class PublishedAlbumServiceImpl implements PublishedAlbumService {

    private PublishedAlbumRepository publishedAlbumRepository;

    @Override
    public List<PublishedAlbumDto> findAll() {
        return publishedAlbumRepository.findAll().stream().map(i ->
                new PublishedAlbumDto(i.getAlbum().getId().getId(), i.getArtist().getId().getId(),
                        i.getPublishedOn(), i.getAlbumTier(), i.getSubscriptionFee().add(i.getTransactionFee()),
                        i.getPublisher().getId().getId(), i.getPublisher().getDistributorInfo().getDistributorInformation()))
                .collect(Collectors.toList());
    }

    @Override
    public PublishedAlbum findById(PublishedAlbumId id) {
        return publishedAlbumRepository.findById(id)
                .orElseThrow(() -> new PublishedAlbumNotFoundException(id));
    }
}
