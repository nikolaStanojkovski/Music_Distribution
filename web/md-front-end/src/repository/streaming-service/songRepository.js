import axios from "../../api/apiStreamingService";
import {DEFAULT_PAGE_NUMBER, DEFAULT_PAGE_SIZE, DEFAULT_SORT_ORDER} from "../../constants/pagination";
import {FILE, RESOURCE, SEARCH, SONGS, SONGS_CREATE, SONGS_PUBLISH, SONGS_RAISE_TIER} from "../../constants/endpoint";
import {COVER, PAGE, SEARCH_PARAMS, SEARCH_TERM, SIZE, SONG_REQUEST, SORT} from "../../constants/model";
import {APPLICATION_JSON} from "../../constants/extension";
import {TOKEN_PLACEHOLDER} from "../../constants/transaction";

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
    createSong: (file, songRequest) => {
        const formData = new FormData();
        formData.append(FILE, file);
        formData.append(SONG_REQUEST, new Blob([songRequest], {
            type: APPLICATION_JSON
        }));
        return axios.post(`${RESOURCE}${SONGS_CREATE}`, formData);
    },
    publishSong: (cover, songRequest, tokenId) => {
        const formData = new FormData();
        formData.append(COVER, cover);
        formData.append(SONG_REQUEST, new Blob([songRequest], {
            type: APPLICATION_JSON
        }));
        formData.append(TOKEN_PLACEHOLDER, new Blob([tokenId], {
            type: APPLICATION_JSON
        }));
        return axios.post(`${RESOURCE}${SONGS_PUBLISH}`, formData);
    },
    raiseTierSong: (songRequest, tokenId) => {
        return axios.post(`${RESOURCE}${SONGS_RAISE_TIER}`, songRequest, {
            params: {
                token: tokenId
            }
        });
    },
}

export default SongRepository;