package com.musicdistribution.streamingservice.application.service;

import com.musicdistribution.streamingservice.domain.model.entity.core.Notification;
import com.musicdistribution.streamingservice.domain.model.entity.id.ListenerId;
import com.musicdistribution.streamingservice.domain.model.entity.id.NotificationId;
import com.musicdistribution.sharedkernel.domain.response.SearchResultResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * A service that contains the specific business logic for notification entities.
 */
public interface NotificationService {

    /**
     * Method used for fetching a page of notifications from the database.
     *
     * @param pageable - the wrapper object containing pagination data.
     * @return a page of the notifications.
     */
    Page<Notification> findAll(Pageable pageable);

    /**
     * Method used for reading the total number of entities from the database.
     *
     * @return the total number of notifications from the database.
     */
    Long findTotalSize();

    /**
     * Method used for searching notifications.
     *
     * @param searchParams - the object parameters by which the filtering is to be done.
     * @param searchTerm   - the search term by which the filtering is to be done.
     * @param pageable     - the wrapper object containing pagination data.
     * @return a list of the filtered notifications.
     */
    SearchResultResponse<Notification> search(List<String> searchParams, String searchTerm, Pageable pageable);

    /**
     * Method used for fetching a notification with the specified ID.
     *
     * @param id - notification's ID.
     * @return an optional with the found notification.
     */
    Optional<Notification> findById(NotificationId id);

    /**
     * Method used for sending a notification to the appropriate user.
     *
     * @param listenerId - the ID of the listener to whom the notification is being sent to.
     * @param objectId   - the ID of the object for which the notification is being triggered.
     * @return an optional with the sent notification.
     */
    Optional<Notification> send(ListenerId listenerId, String objectId);
}
