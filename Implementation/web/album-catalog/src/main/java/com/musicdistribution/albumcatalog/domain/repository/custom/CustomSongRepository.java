package com.musicdistribution.albumcatalog.domain.repository.custom;

import com.musicdistribution.albumcatalog.domain.models.entity.Song;
import com.musicdistribution.albumcatalog.domain.util.SearchUtil;
import lombok.AllArgsConstructor;
import org.hibernate.SessionFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
@AllArgsConstructor
public class CustomSongRepository implements SearchRepository<Song> {

    private final EntityManager entityManager;
    private final EntityManagerFactory entityManagerFactory;

    @Override
    public Page<Song> search(List<String> searchParameters, String searchTerm, Pageable pageable) {
        SessionFactory sessionFactory = entityManagerFactory.unwrap(SessionFactory.class);
        List<String> formattedSearchParams = SearchUtil.buildSearchParams(searchParameters, Song.class.getName(), sessionFactory);

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Song> cq = cb.createQuery(Song.class);

        Root<Song> artistRoot = cq.from(Song.class);
        cq.where(convertToPredicates(formattedSearchParams, artistRoot, cb, searchTerm));

        List<Song> resultList = entityManager.createQuery(cq)
                .setMaxResults(pageable.getPageSize())
                .setFirstResult(Integer.parseInt(String.valueOf(pageable.getOffset())))
                .getResultList();
        return new PageImpl<>(resultList, pageable, resultList.size());
    }

    private Predicate[] convertToPredicates(List<String> searchParams,
                                            Root<Song> entityRoot,
                                            CriteriaBuilder cb,
                                            String searchTerm) {
        return searchParams.stream()
                .map(param -> cb.like(SearchUtil.convertToPredicateExpression(param, entityRoot),
                        String.format("%%%s%%", searchTerm)))
                .toArray(Predicate[]::new);
    }
}
