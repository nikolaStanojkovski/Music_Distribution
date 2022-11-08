package com.musicdistribution.albumcatalog.domain.repository.custom;

import com.musicdistribution.albumcatalog.domain.models.entity.Album;
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
public class CustomAlbumRepository implements SearchRepository<Album> {

    private final EntityManager entityManager;
    private final EntityManagerFactory entityManagerFactory;

    @Override
    public Page<Album> search(List<String> searchParameters, String searchTerm, Pageable pageable) {
        SessionFactory sessionFactory = entityManagerFactory.unwrap(SessionFactory.class);
        List<String> formattedSearchParams = SearchUtil.buildSearchParams(searchParameters, Album.class.getName(), sessionFactory);

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Album> cq = cb.createQuery(Album.class);

        Root<Album> albumRoot = cq.from(Album.class);
        cq.where(convertToPredicates(formattedSearchParams, albumRoot, cb, searchTerm));

        List<Album> resultList = entityManager.createQuery(cq)
                .setMaxResults(pageable.getPageSize())
                .setFirstResult(Integer.parseInt(String.valueOf(pageable.getOffset())))
                .getResultList();
        return new PageImpl<>(resultList, pageable, resultList.size());
    }

    private Predicate[] convertToPredicates(List<String> searchParams,
                                            Root<Album> entityRoot,
                                            CriteriaBuilder cb,
                                            String searchTerm) {
        return searchParams.stream()
                .map(param -> cb.like(SearchUtil.convertToPredicateExpression(param, entityRoot),
                        String.format("%%%s%%", searchTerm)))
                .toArray(Predicate[]::new);
    }
}
