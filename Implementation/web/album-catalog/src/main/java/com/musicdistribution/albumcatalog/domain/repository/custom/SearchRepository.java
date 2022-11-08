package com.musicdistribution.albumcatalog.domain.repository.custom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface SearchRepository<T> {

    Page<T> search(List<String> searchParameters, String searchTerm, Pageable pageable);
}
