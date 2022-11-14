package com.musicdistribution.storageservice.domain.repository.custom;

import com.musicdistribution.storageservice.domain.model.SearchResult;
import com.musicdistribution.storageservice.domain.model.entity.Artist;
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
public class CustomArtistRepository implements SearchRepository<Artist> {

    private final EntityManager entityManager;
    private final EntityManagerFactory entityManagerFactory;

    @Override
    public SearchResult<Artist> search(List<String> searchParameters, String searchTerm, Pageable pageable) {
        SessionFactory sessionFactory = entityManagerFactory.unwrap(SessionFactory.class);
        List<String> formattedSearchParams = SearchUtil.buildSearchParams(searchParameters, Artist.class.getName(), sessionFactory);

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Artist> cq = cb.createQuery(Artist.class);

        Root<Artist> artistRoot = cq.from(Artist.class);
        cq.where(SearchUtil.convertToPredicates(formattedSearchParams, artistRoot, cb, searchTerm));

        List<Artist> resultList = entityManager.createQuery(cq)
                .setMaxResults(pageable.getPageSize())
                .setFirstResult(Integer.parseInt(String.valueOf(pageable.getOffset())))
                .getResultList();
        Integer resultListSize = entityManager.createQuery(cq).getResultList().size();
        return new SearchResult<>(new PageImpl<>(resultList, pageable, resultList.size()), resultListSize);
    }
}
