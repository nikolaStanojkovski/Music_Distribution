package com.musicdistribution.storageservice.domain.repository;

import com.musicdistribution.storageservice.domain.model.response.SearchResultResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Custom search repository for database entities.
 */
@Component
public interface SearchRepository<T> {

    /**
     * Method used to filter database entity objects.
     *
     * @param searchParameters - the parameters by which the filtering will be done.
     * @param searchTerm       - the term which is being searched upon.
     * @param pageable         - pagination data for the entity object.
     * @return a response containing the search results of the appropriate entity.
     */
    SearchResultResponse<T> search(List<String> searchParameters,
                                   String searchTerm,
                                   Pageable pageable);
}
