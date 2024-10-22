package com.musicdistribution.streamingservice.domain.repository.custom;

import com.musicdistribution.sharedkernel.domain.repository.SearchRepository;
import com.musicdistribution.sharedkernel.domain.response.SearchResultResponse;
import com.musicdistribution.streamingservice.constant.EntityConstants;
import com.musicdistribution.streamingservice.domain.model.entity.core.Song;
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
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Custom repository for song database entities.
 */
@Repository
@AllArgsConstructor
public class CustomSongRepository implements SearchRepository<Song> {

    private final EntityManager entityManager;

    private final EntityManagerFactory entityManagerFactory;

    /**
     * Method used to filter song entity objects.
     *
     * @param searchParameters      - the parameters by which the filtering will be done.
     * @param shouldFilterPublished - a flag determining whether the filtering should be done by publishing status.
     * @param searchTerm            - the term which is being searched upon.
     * @param pageable              - pagination data for the song entity object.
     * @return the results of the filtering for songs which meet the search criteria.
     */
    @Override
    public SearchResultResponse<Song> search(List<String> searchParameters, Boolean shouldFilterPublished,
                                             String searchTerm, Pageable pageable) {
        SessionFactory sessionFactory = entityManagerFactory.unwrap(SessionFactory.class);
        List<String> formattedSearchParams = SearchUtil.buildSearchParams(searchParameters, Song.class.getName(), sessionFactory);

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Song> cq = cb.createQuery(Song.class);

        Root<Song> songRoot = cq.from(Song.class);
        Predicate orPredicates = SearchUtil.convertToOrPredicates(formattedSearchParams, songRoot, cb, searchTerm);
        if (pageable.getSort().isSorted()) {
            cq.orderBy(QueryUtils.toOrders(pageable.getSort(), songRoot, cb));
        }

        cq.where((shouldFilterPublished)
                ? cb.and(filterSingleSongs(cb, songRoot), orPredicates)
                : orPredicates);
        List<Song> resultList = entityManager.createQuery(cq)
                .setMaxResults(pageable.getPageSize())
                .setFirstResult(Integer.parseInt(String.valueOf(pageable.getOffset())))
                .getResultList();
        Integer resultListSize = entityManager.createQuery(cq).getResultList().size();
        return new SearchResultResponse<>(new PageImpl<>(resultList, pageable, resultList.size()), resultListSize);
    }

    /**
     * Method used to create predicate for filtering published songs which are singles.
     *
     * @param cb   - the criteria builder wrapper object.
     * @param root - the class root wrapper object.
     * @return the predicate containing the single songs filtering.
     */
    private Predicate filterSingleSongs(CriteriaBuilder cb,
                                        Root<Song> root) {
        return cb.and(cb.equal(SearchUtil
                .convertToPredicateExpression(EntityConstants.IS_A_SINGLE,
                        root), Boolean.TRUE));
    }
}
