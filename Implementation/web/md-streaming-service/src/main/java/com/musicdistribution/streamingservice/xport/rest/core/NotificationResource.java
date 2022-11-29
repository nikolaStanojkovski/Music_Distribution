package com.musicdistribution.streamingservice.xport.rest.core;

import com.musicdistribution.sharedkernel.config.ApiController;
import com.musicdistribution.streamingservice.constant.EntityConstants;
import com.musicdistribution.streamingservice.constant.PathConstants;
import com.musicdistribution.streamingservice.domain.model.entity.core.Notification;
import com.musicdistribution.streamingservice.domain.model.entity.id.ListenerId;
import com.musicdistribution.streamingservice.domain.model.entity.id.NotificationId;
import com.musicdistribution.sharedkernel.domain.response.SearchResultResponse;
import com.musicdistribution.streamingservice.domain.model.response.core.NotificationResponse;
import com.musicdistribution.streamingservice.domain.service.IEncryptionSystem;
import com.musicdistribution.streamingservice.service.NotificationService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Notification Rest Controller.
 */
@ApiController
@AllArgsConstructor
@RequestMapping(PathConstants.API_NOTIFICATIONS)
public class NotificationResource {

    private final NotificationService notificationService;

    private final IEncryptionSystem encryptionSystem;

    /**
     * Method used for searching notifications.
     *
     * @param searchParams - the object parameters by which a filtering is to be done.
     * @param searchTerm   - the search term by which the filtering is to be done.
     * @param pageable     - the wrapper object containing pagination data.
     * @return the page with the filtered notifications.
     */
    @GetMapping(PathConstants.SEARCH)
    public Page<NotificationResponse> search(@RequestParam String[] searchParams,
                                             @RequestParam String searchTerm,
                                             Pageable pageable) {
        SearchResultResponse<Notification> notificationSearchResultResponse =
                notificationService.search(List.of(searchParams),
                        (List.of(searchParams).stream().filter(param ->
                                param.contains(EntityConstants.ID)).count() == searchParams.length)
                                ? encryptionSystem.decrypt(searchTerm) : searchTerm, pageable);
        return new PageImpl<>(notificationSearchResultResponse.getResultPage()
                .stream()
                .map(notification -> NotificationResponse.from(notification,
                        encryptionSystem.encrypt(notification.getId().getUuid1()),
                        encryptionSystem.encrypt(notification.getId().getUuid2())
                )).collect(Collectors.toList()), pageable, notificationSearchResultResponse.getResultSize());
    }

    /**
     * Method used for fetching information about a specific notification.
     *
     * @param listenerId   - listener's ID.
     * @param publishingId - publishing's ID.
     * @return the found notification.
     */
    @GetMapping(PathConstants.ID)
    public ResponseEntity<NotificationResponse> findById(@RequestParam String listenerId,
                                                         @RequestParam String publishingId) {
        return this.notificationService.findById(NotificationId.of(
                encryptionSystem.decrypt(listenerId), encryptionSystem.decrypt(publishingId)))
                .map(notification -> ResponseEntity.ok().body(NotificationResponse.from(
                        notification,
                        encryptionSystem.encrypt(notification.getId().getUuid1()),
                        encryptionSystem.encrypt(notification.getId().getUuid2())
                ))).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Method used for fetching information about a specific notification.
     *
     * @param listenerId   - listener's ID.
     * @param publishingId - publishing's ID.
     * @return the found notification.
     */
    @GetMapping(PathConstants.SEND)
    public ResponseEntity<NotificationResponse> send(@RequestParam String listenerId,
                                                     @RequestParam String publishingId) {
        return this.notificationService.send(
                ListenerId.of(encryptionSystem.decrypt(listenerId)), publishingId)
                .map(notification -> ResponseEntity.ok().body(NotificationResponse.from(
                        notification,
                        encryptionSystem.encrypt(notification.getId().getUuid1()),
                        encryptionSystem.encrypt(notification.getId().getUuid2())
                ))).orElseGet(() -> ResponseEntity.notFound().build());
    }
}