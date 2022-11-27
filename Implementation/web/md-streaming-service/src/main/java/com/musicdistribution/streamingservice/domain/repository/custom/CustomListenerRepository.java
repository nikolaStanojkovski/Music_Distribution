package com.musicdistribution.streamingservice.domain.repository.custom;

import com.musicdistribution.streamingservice.domain.model.entity.Listener;
import com.musicdistribution.streamingservice.domain.model.response.SearchResultResponse;
import com.musicdistribution.streamingservice.domain.repository.SearchRepository;
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
 * Custom repository for listener database entities.
 */
@Repository
@AllArgsConstructor
public class CustomListenerRepository implements SearchRepository<Listener> {

    private final EntityManager entityManager;

    private final EntityManagerFactory entityManagerFactory;

    /**
     * Method used to filter listener entity objects.
     *
     * @param searchParameters      - the parameters by which the filtering will be done.
     * @param shouldFilterPublished - a flag determining whether a filtering should be done by publishing status.
     * @param searchTerm            - the term which is being searched upon.
     * @param pageable              - pagination data for the notification entity object.
     * @return the results of the filtering for listeners which meet the search criteria.
     */
    @Override
    public SearchResultResponse<Listener> search(List<String> searchParameters, Boolean shouldFilterPublished,
                                                     String searchTerm, Pageable pageable) {
        SessionFactory sessionFactory = entityManagerFactory.unwrap(SessionFactory.class);
        List<String> formattedSearchParams = SearchUtil.buildSearchParams(searchParameters, Listener.class.getName(), sessionFactory);

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Listener> cq = cb.createQuery(Listener.class);

        Root<Listener> listenerRoot = cq.from(Listener.class);
        cq.where(SearchUtil.convertToAndPredicates(formattedSearchParams, listenerRoot, cb, searchTerm));

        List<Listener> resultList = entityManager.createQuery(cq)
                .setMaxResults(pageable.getPageSize())
                .setFirstResult(Integer.parseInt(String.valueOf(pageable.getOffset())))
                .getResultList();
        Integer resultListSize = entityManager.createQuery(cq).getResultList().size();
        return new SearchResultResponse<>(new PageImpl<>(resultList, pageable, resultList.size()), resultListSize);
    }
}
