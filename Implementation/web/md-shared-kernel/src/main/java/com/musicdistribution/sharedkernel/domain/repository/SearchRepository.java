package com.musicdistribution.sharedkernel.domain.repository;

import com.musicdistribution.sharedkernel.domain.response.SearchResultResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Custom search repository for database entities.
 *
 * @param <T> - the type of the object for which the search is being made.
 */
@Component
public interface SearchRepository<T> {

    /**
     * Method used to filter database entity objects.
     *
     * @param searchParameters      - the parameters by which the filtering will be done.
     * @param shouldFilterPublished - a flag determining whether a filtering should be done by publishing status.
     * @param searchTerm            - the term which is being searched upon.
     * @param pageable              - pagination data for the entity object.
     * @return a response containing the search results of the appropriate entity.
     */
    SearchResultResponse<T> search(List<String> searchParameters,
                                   Boolean shouldFilterPublished,
                                   String searchTerm,
                                   Pageable pageable);
}
