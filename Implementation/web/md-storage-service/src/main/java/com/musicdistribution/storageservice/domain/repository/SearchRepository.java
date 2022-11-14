package com.musicdistribution.storageservice.domain.repository;

import com.musicdistribution.storageservice.domain.model.SearchResult;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface SearchRepository<T> {

    SearchResult<T> search(List<String> searchParameters, String searchTerm, Pageable pageable);
}
