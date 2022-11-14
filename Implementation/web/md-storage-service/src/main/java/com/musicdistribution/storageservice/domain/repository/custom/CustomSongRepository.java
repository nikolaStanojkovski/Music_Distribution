package com.musicdistribution.storageservice.domain.repository.custom;

import com.musicdistribution.storageservice.domain.model.SearchResult;
import com.musicdistribution.storageservice.domain.model.entity.Song;
import com.musicdistribution.storageservice.domain.repository.SearchRepository;
import com.musicdistribution.storageservice.domain.util.SearchUtil;
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

@Repository
@AllArgsConstructor
public class CustomSongRepository implements SearchRepository<Song> {

    private final EntityManager entityManager;
    private final EntityManagerFactory entityManagerFactory;

    @Override
    public SearchResult<Song> search(List<String> searchParameters, String searchTerm, Pageable pageable) {
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
        return new SearchResult<>(new PageImpl<>(resultList, pageable, resultList.size()), resultListSize);
    }
}
