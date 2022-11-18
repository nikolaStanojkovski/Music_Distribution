package com.musicdistribution.storageservice.domain.repository.custom;

import com.musicdistribution.storageservice.domain.model.response.SearchResultResponse;
import com.musicdistribution.storageservice.domain.model.entity.Song;
import com.musicdistribution.storageservice.domain.repository.SearchRepository;
import com.musicdistribution.storageservice.util.SearchUtil;
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
     * @param searchParameters - the parameters by which the filtering will be done.
     * @param searchTerm       - the term which is being searched upon.
     * @param pageable         - pagination data for the song entity object.
     * @return the results of the filtering for songs which meet the search criteria.
     */
    @Override
    public SearchResultResponse<Song> search(List<String> searchParameters, String searchTerm, Pageable pageable) {
        SessionFactory sessionFactory = entityManagerFactory.unwrap(SessionFactory.class);
        List<String> formattedSearchParams = SearchUtil.buildSearchParams(searchParameters, Song.class.getName(), sessionFactory);

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Song> cq = cb.createQuery(Song.class);

        Root<Song> SongRoot = cq.from(Song.class);
        cq.where(SearchUtil.convertToPredicates(formattedSearchParams, SongRoot, cb, searchTerm));

        List<Song> resultList = entityManager.createQuery(cq)
                .setMaxResults(pageable.getPageSize())
                .setFirstResult(Integer.parseInt(String.valueOf(pageable.getOffset())))
                .getResultList();
        Integer resultListSize = entityManager.createQuery(cq).getResultList().size();
        return new SearchResultResponse<>(new PageImpl<>(resultList, pageable, resultList.size()), resultListSize);
    }
}
