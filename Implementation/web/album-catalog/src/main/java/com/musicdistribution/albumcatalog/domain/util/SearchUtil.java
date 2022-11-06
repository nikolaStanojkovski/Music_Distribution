package com.musicdistribution.albumcatalog.domain.util;

import com.musicdistribution.albumcatalog.domain.constants.EntityConstants;

import java.util.List;
import java.util.stream.Collectors;

public final class SearchUtil {

    public static String getAlbumSearchParams(List<String> searchParams) {
        List<String> filteredParams = convertToSearchParams(searchParams,
                List.of(EntityConstants.ALBUM_ID, EntityConstants.ALBUM_NAME,
                        EntityConstants.ALBUM_GENRE, EntityConstants.ALBUM_CREATOR,
                        EntityConstants.ALBUM_SUBSCRIPTION_FEE, EntityConstants.ALBUM_TRANSACTION_FEE,
                        EntityConstants.ALBUM_TIER, EntityConstants.ALBUM_SONG_LENGTH));
        return getFormattedSearchParams(filteredParams);
    }

    private static List<String> convertToSearchParams(List<String> searchParams,
                                                      List<String> entityConstants) {
        return searchParams.stream().filter(param -> !param.isBlank())
                .map(param -> param.replace("-", "."))
                .filter(param -> entityConstants.stream().anyMatch(param::equals))
                .map(param -> String.format("a.%s", param))
                .collect(Collectors.toList());
    }

    private static String getFormattedSearchParams(List<String> searchParams) {
        return String.join(", ", searchParams);
    }
}
