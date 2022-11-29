package com.musicdistribution.streamingservice.domain.repository.custom;

import com.musicdistribution.streamingservice.domain.model.entity.core.Notification;
import com.musicdistribution.sharedkernel.domain.response.SearchResultResponse;
import com.musicdistribution.sharedkernel.domain.repository.SearchRepository;
import com.musicdistribution.streamingservice.util.SearchUtil;
import lombok.AllArgsConstructor;
import org.hibernate.SessionFactory;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Custom repository for notification database entities.
 */
@Repository
@AllArgsConstructor
public class CustomNotificationRepository implements SearchRepository<Notification> {

    private final EntityManager entityManager;

    private final EntityManagerFactory entityManagerFactory;

    /**
     * Method used to filter notification entity objects.
     *
     * @param searchParameters      - the parameters by which the filtering will be done.
     * @param shouldFilterPublished - a flag determining whether the filtering should be done by publishing status.
     * @param searchTerm            - the term which is being searched upon.
     * @param pageable              - pagination data for the notification entity object.
     * @return the results of the filtering for notifications which meet the search criteria.
     */
    @Override
    public SearchResultResponse<Notification> search(List<String> searchParameters, Boolean shouldFilterPublished,
                                                     String searchTerm, Pageable pageable) {
        SessionFactory sessionFactory = entityManagerFactory.unwrap(SessionFactory.class);
        List<String> formattedSearchParams = SearchUtil.buildSearchParams(searchParameters, Notification.class.getName(), sessionFactory);

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Notification> cq = cb.createQuery(Notification.class);

        Root<Notification> notificationRoot = cq.from(Notification.class);
        cq.where(SearchUtil.convertToAndPredicates(formattedSearchParams, notificationRoot, cb, searchTerm));

        List<Notification> resultList = entityManager.createQuery(cq)
                .setMaxResults(pageable.getPageSize())
                .setFirstResult(Integer.parseInt(String.valueOf(pageable.getOffset())))
                .getResultList();
        Integer resultListSize = entityManager.createQuery(cq).getResultList().size();
        return new SearchResultResponse<>(new PageImpl<>(resultList, pageable, resultList.size()), resultListSize);
    }
}
