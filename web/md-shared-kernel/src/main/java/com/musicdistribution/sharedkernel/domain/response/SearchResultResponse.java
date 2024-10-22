package com.musicdistribution.sharedkernel.domain.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Page;

/**
 * Object used to transfer search result data
 * from the back-end to the front-end.
 *
 * @param <T> - the type of the object for which the search results are being wrapped for.
 */
@Getter
@AllArgsConstructor
public class SearchResultResponse<T> {

    private final Page<T> resultPage;
    private final Integer resultSize;
}
