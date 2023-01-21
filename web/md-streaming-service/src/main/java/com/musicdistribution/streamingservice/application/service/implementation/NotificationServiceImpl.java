package com.musicdistribution.streamingservice.application.service.implementation;

import com.musicdistribution.streamingservice.domain.model.entity.core.Notification;
import com.musicdistribution.streamingservice.domain.model.entity.id.ListenerId;
import com.musicdistribution.streamingservice.domain.model.entity.id.NotificationId;
import com.musicdistribution.sharedkernel.domain.response.SearchResultResponse;
import com.musicdistribution.streamingservice.domain.repository.core.NotificationRepository;
import com.musicdistribution.sharedkernel.domain.repository.SearchRepository;
import com.musicdistribution.streamingservice.application.service.NotificationService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of the notification service.
 */
@Service
@Transactional
@AllArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final SearchRepository<Notification> searchRepository;

    /**
     * Method used for fetching a page of notifications from the database.
     *
     * @param pageable - the wrapper object containing pagination data.
     * @return a page of the notifications.
     */
    @Override
    public Page<Notification> findAll(Pageable pageable) {
        return notificationRepository.findAll(pageable);
    }

    /**
     * Method used for searching notifications.
     *
     * @param searchParams - the object parameters by which the filtering is to be done.
     * @param searchTerm   - the search term by which the filtering is to be done.
     * @param pageable     - the wrapper object containing pagination data.
     * @return a list of the filtered notifications.
     */
    @Override
    public SearchResultResponse<Notification> search(List<String> searchParams, String searchTerm, Pageable pageable) {
        return searchRepository.search(searchParams, false, searchTerm, pageable);
    }

    /**
     * Method used for reading the total number of entities from the database.
     *
     * @return the total number of notifications from the database.
     */
    @Override
    public Long findTotalSize() {
        return notificationRepository.count();
    }

    /**
     * Method used for fetching a notification with the specified ID.
     *
     * @param id - notification's ID.
     * @return an optional with the found notification.
     */
    @Override
    public Optional<Notification> findById(NotificationId id) {
        return notificationRepository.findById(id);
    }

    /**
     * Method used for sending a notification to the appropriate user.
     *
     * @param listenerId - the ID of the listener to whom the notification is being sent to.
     * @param objectId   - the ID of the object for which the notification is being triggered.
     * @return an optional with the sent notification.
     */
    @Override
    public Optional<Notification> send(ListenerId listenerId, String objectId) {
        return findById(NotificationId.of(listenerId.getId(), objectId))
                .map(notification -> {
                    notification.trigger();
                    notificationRepository.save(notification);
                    notificationRepository.flush();
                    return notification;
                });
    }
}
