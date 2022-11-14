package com.musicdistribution.storageservice.domain.repository.custom;

import com.musicdistribution.storageservice.domain.model.SearchResult;
import com.musicdistribution.storageservice.domain.model.entity.Album;
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
public class CustomAlbumRepository implements SearchRepository<Album> {

    private final EntityManager entityManager;
    private final EntityManagerFactory entityManagerFactory;

    @Override
    public SearchResult<Album> search(List<String> searchParameters, String searchTerm, Pageable pageable) {
        SessionFactory sessionFactory = entityManagerFactory.unwrap(SessionFactory.class);
        List<String> formattedSearchParams = SearchUtil.buildSearchParams(searchParameters, Album.class.getName(), sessionFactory);

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Album> cq = cb.createQuery(Album.class);

        Root<Album> AlbumRoot = cq.from(Album.class);
        cq.where(SearchUtil.convertToPredicates(formattedSearchParams, AlbumRoot, cb, searchTerm));

        List<Album> resultList = entityManager.createQuery(cq)
                .setMaxResults(pageable.getPageSize())
                .setFirstResult(Integer.parseInt(String.valueOf(pageable.getOffset())))
                .getResultList();
        Integer resultListSize = entityManager.createQuery(cq).getResultList().size();
        return new SearchResult<>(new PageImpl<>(resultList, pageable, resultList.size()), resultListSize);
    }
}
