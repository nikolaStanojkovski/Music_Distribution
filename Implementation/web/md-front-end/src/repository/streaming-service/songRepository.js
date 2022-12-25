import axios from "../../custom-axios/axiosStreamingService";
import {DEFAULT_PAGE_NUMBER, DEFAULT_PAGE_SIZE, DEFAULT_SORT_ORDER} from "../../constants/pagination";
import {FILE, RESOURCE, SEARCH, SONGS, SONGS_CREATE, SONGS_PUBLISH, SONGS_RAISE_TIER} from "../../constants/endpoint";
import {
    COVER,
    LENGTH_IN_SECONDS,
    PAGE,
    SEARCH_PARAMS,
    SEARCH_TERM,
    SIZE,
    SONG_GENRE,
    SONG_ID,
    SONG_NAME,
    SONG_REQUEST,
    SONG_TIER,
    SONG_TRANSACTION_REQUEST, SORT,
    SUBSCRIPTION_FEE,
    TRANSACTION_FEE
} from "../../constants/model";
import {APPLICATION_JSON} from "../../constants/extension";

const SongRepository = {
    fetchSongs: (pageNumber) => {
        return axios.get(`${RESOURCE}${SONGS}?${PAGE}=${pageNumber || DEFAULT_PAGE_NUMBER}&${SIZE}=${DEFAULT_PAGE_SIZE}&${SORT}=${DEFAULT_SORT_ORDER}`);
    },
    filterSongs: (pageNumber, pageSize, key, value) => {
        return axios.get(`${RESOURCE}${SONGS}${SEARCH}?${SEARCH_PARAMS}=${key}&${SEARCH_TERM}=${value}&${PAGE}=${pageNumber || DEFAULT_PAGE_NUMBER}&${SIZE}=${pageSize || DEFAULT_PAGE_SIZE}&${SORT}=${DEFAULT_SORT_ORDER}`)
    },

    fetchSong: (id) => {
        return axios.get(`${RESOURCE}${SONGS}/${FILE}/${id}`);
    },
    createSong: (file, songName, lengthInSeconds, songGenre) => {
        const formData = new FormData();
        formData.append(FILE, file);
        const songRequest = {};
        songRequest[`${SONG_NAME}`] = songName;
        songRequest[`${LENGTH_IN_SECONDS}`] = lengthInSeconds;
        songRequest[`${SONG_GENRE}`] = songGenre;
        formData.append(SONG_REQUEST, new Blob([JSON.stringify(songRequest)], {
            type: APPLICATION_JSON
        }));
        return axios.post(`${RESOURCE}${SONGS_CREATE}`, formData);
    },
    publishSong: (cover, songId, songTier, subscriptionFee, transactionFee) => {
        const formData = new FormData();
        formData.append(COVER, cover);
        const songTransactionRequest = {};
        songTransactionRequest[`${SONG_ID}`] = songId;
        songTransactionRequest[`${SONG_TIER}`] = songTier;
        songTransactionRequest[`${SUBSCRIPTION_FEE}`] = subscriptionFee;
        songTransactionRequest[`${TRANSACTION_FEE}`] = transactionFee;
        formData.append(SONG_TRANSACTION_REQUEST, new Blob([JSON.stringify(songTransactionRequest)], {
            type: APPLICATION_JSON
        }));
        return axios.post(`${RESOURCE}${SONGS_PUBLISH}`, formData);
    },
    raiseTierSong: (songId, songTier, subscriptionFee, transactionFee) => {
        const songRequest = {};
        songRequest[`${SONG_ID}`] = songId;
        songRequest[`${SONG_TIER}`] = songTier;
        songRequest[`${SUBSCRIPTION_FEE}`] = subscriptionFee;
        songRequest[`${TRANSACTION_FEE}`] = transactionFee;
        return axios.post(`${RESOURCE}${SONGS_RAISE_TIER}`, songRequest);
    },
}

export default SongRepository;