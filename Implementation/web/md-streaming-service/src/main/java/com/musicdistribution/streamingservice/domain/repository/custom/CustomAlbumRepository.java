package com.musicdistribution.streamingservice.domain.repository.custom;

import com.musicdistribution.sharedkernel.domain.repository.SearchRepository;
import com.musicdistribution.sharedkernel.domain.response.SearchResultResponse;
import com.musicdistribution.streamingservice.domain.model.entity.core.Album;
import com.musicdistribution.streamingservice.util.SearchUtil;
import lombok.AllArgsConstructor;
import org.hibernate.SessionFactory;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.query.QueryUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Custom repository for album database entities.
 */
@Repository
@AllArgsConstructor
public class CustomAlbumRepository implements SearchRepository<Album> {

    private final EntityManager entityManager;

    private final EntityManagerFactory entityManagerFactory;

    /**
     * Method used to filter album entity objects.
     *
     * @param searchParameters      - the parameters by which the filtering will be done.
     * @param shouldFilterPublished - a flag determining whether the filtering should be done by publishing status.
     * @param searchTerm            - the term which is being searched upon.
     * @param pageable              - pagination data for the album entity object.
     * @return the results of the filtering for albums which meet the search criteria.
     */
    @Override
    public SearchResultResponse<Album> search(List<String> searchParameters, Boolean shouldFilterPublished,
                                              String searchTerm, Pageable pageable) {
        SessionFactory sessionFactory = entityManagerFactory.unwrap(SessionFactory.class);
        List<String> formattedSearchParams = SearchUtil.buildSearchParams(searchParameters, Album.class.getName(), sessionFactory);

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Album> cq = cb.createQuery(Album.class);

        Root<Album> albumRoot = cq.from(Album.class);
        if (pageable.getSort().isSorted()) {
            cq.orderBy(QueryUtils.toOrders(pageable.getSort(), albumRoot, cb));
        }

        cq.where(SearchUtil.convertToOrPredicates(formattedSearchParams, albumRoot, cb, searchTerm));
        List<Album> resultList = entityManager.createQuery(cq)
                .setMaxResults(pageable.getPageSize())
                .setFirstResult(Integer.parseInt(String.valueOf(pageable.getOffset())))
                .getResultList();
        Integer resultListSize = entityManager.createQuery(cq).getResultList().size();
        return new SearchResultResponse<>(new PageImpl<>(resultList, pageable, resultList.size()), resultListSize);
    }
}
