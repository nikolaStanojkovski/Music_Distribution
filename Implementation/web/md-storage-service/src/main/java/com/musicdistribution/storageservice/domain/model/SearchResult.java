package com.musicdistribution.storageservice.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Page;

@Getter
@AllArgsConstructor
public class SearchResult<T> {

    private final Page<T> resultPage;
    private final Integer resultSize;
}
