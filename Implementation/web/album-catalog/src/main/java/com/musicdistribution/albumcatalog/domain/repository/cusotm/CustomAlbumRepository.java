package com.musicdistribution.albumcatalog.domain.repository.cusotm;

import com.musicdistribution.albumcatalog.domain.models.entity.Album;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;

@Repository
@AllArgsConstructor
public class CustomAlbumRepository {

    private final EntityManager entityManager;

    //    TODO: Implement this search logic
    public Page<Album> search(String searchParams, String searchTerm, Pageable pageable) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
//        CriteriaQuery<Album> cq = cb.createQuery(Album.class);
//
//        Root<Album> book = cq.from(Album.class);
//        Predicate authorNamePredicate = cb.equal(book.get("author"), authorName);
//        Predicate titlePredicate = cb.like(book.get("title"), "%" + title + "%");
//        cq.where(authorNamePredicate, titlePredicate);
//
//        TypedQuery<Album> query = entityManager.createQuery(cq);
//        return query.getResultList();
        return Page.empty();
    }
}
