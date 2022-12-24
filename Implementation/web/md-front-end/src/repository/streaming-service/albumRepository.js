import axios from "../../custom-axios/axiosStreamingService";
import {DEFAULT_PAGE_NUMBER, DEFAULT_PAGE_SIZE, DEFAULT_SORT_ORDER} from "../../constants/pagination";
import {ALBUMS, ALBUMS_PUBLISH, ALBUMS_RAISE_TIER, RESOURCE, SEARCH} from "../../constants/endpoint";
import {
    ALBUM_GENRE,
    ALBUM_ID,
    ALBUM_NAME,
    ALBUM_TIER,
    ALBUM_TRANSACTION_REQUEST,
    ARTIST_NAME,
    COMPOSER_NAME,
    COVER,
    PAGE,
    PRODUCER_NAME,
    SEARCH_PARAMS,
    SEARCH_TERM,
    SIZE,
    SONG_ID_LIST,
    SORT,
    SUBSCRIPTION_FEE,
    TRANSACTION_FEE
} from "../../constants/model";
import {APPLICATION_JSON} from "../../constants/extension";

const AlbumRepository = {
    fetchAlbums: (pageNumber) => {
        return axios.get(`${RESOURCE}${ALBUMS}?${PAGE}=${pageNumber || DEFAULT_PAGE_NUMBER}&${SIZE}=${DEFAULT_PAGE_SIZE}&${SORT}=${DEFAULT_SORT_ORDER}`);
    },
    filterAlbums: (pageNumber, key, value) => {
        return axios.get(`${RESOURCE}${ALBUMS}${SEARCH}?${SEARCH_PARAMS}=${key}&${SEARCH_TERM}=${value}&${PAGE}=${pageNumber || DEFAULT_PAGE_NUMBER}&${SIZE}=${DEFAULT_PAGE_SIZE}&${SORT}=${DEFAULT_SORT_ORDER}`);
    },

    publishAlbum: (cover, songIdList,
                   albumName, albumGenre, albumTier,
                   subscriptionFee, transactionFee,
                   artistName, producerName, composerName) => {
        const formData = new FormData();
        formData.append(COVER, cover);
        const albumTransactionRequest = {};
        albumTransactionRequest[`${SONG_ID_LIST}`] = songIdList;
        albumTransactionRequest[`${ALBUM_NAME}`] = albumName;
        albumTransactionRequest[`${ALBUM_GENRE}`] = albumGenre;
        albumTransactionRequest[`${ALBUM_TIER}`] = albumTier;
        albumTransactionRequest[`${SUBSCRIPTION_FEE}`] = subscriptionFee;
        albumTransactionRequest[`${TRANSACTION_FEE}`] = transactionFee;
        albumTransactionRequest[`${ARTIST_NAME}`] = artistName;
        albumTransactionRequest[`${PRODUCER_NAME}`] = producerName;
        albumTransactionRequest[`${COMPOSER_NAME}`] = composerName;
        formData.append(ALBUM_TRANSACTION_REQUEST, new Blob([JSON.stringify(albumTransactionRequest)], {
            type: APPLICATION_JSON
        }));
        return axios.post(`${RESOURCE}${ALBUMS_PUBLISH}`, formData);
    },
    raiseTierAlbum: (albumId, albumTier, subscriptionFee, transactionFee) => {
        const raiseAlbumTierRequest = {};
        raiseAlbumTierRequest[`${ALBUM_ID}`] = albumId;
        raiseAlbumTierRequest[`${ALBUM_TIER}`] = albumTier;
        raiseAlbumTierRequest[`${SUBSCRIPTION_FEE}`] = subscriptionFee;
        raiseAlbumTierRequest[`${TRANSACTION_FEE}`] = transactionFee;
        return axios.post(`${RESOURCE}${ALBUMS_RAISE_TIER}`, raiseAlbumTierRequest);
    },
}

export default AlbumRepository;