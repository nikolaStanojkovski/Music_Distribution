import axios from "../../custom-axios/axiosStreamingService";
import {DEFAULT_PAGE_NUMBER, DEFAULT_PAGE_SIZE, DEFAULT_SORT_ORDER} from "../../constants/pagination";
import {ALBUMS, ALBUMS_PUBLISH, ALBUMS_RAISE_TIER, RESOURCE, SEARCH} from "../../constants/endpoint";
import {ALBUM_REQUEST, COVER, PAGE, SEARCH_PARAMS, SEARCH_TERM, SIZE, SORT} from "../../constants/model";
import {APPLICATION_JSON} from "../../constants/extension";

const AlbumRepository = {
    fetchAlbums: (pageNumber) => {
        return axios.get(`${RESOURCE}${ALBUMS}?${PAGE}=${pageNumber || DEFAULT_PAGE_NUMBER}&${SIZE}=${DEFAULT_PAGE_SIZE}&${SORT}=${DEFAULT_SORT_ORDER}`);
    },
    filterAlbums: (pageNumber, pageSize, key, value) => {
        return axios.get(`${RESOURCE}${ALBUMS}${SEARCH}?${SEARCH_PARAMS}=${key}&${SEARCH_TERM}=${value}&${PAGE}=${pageNumber || DEFAULT_PAGE_NUMBER}&${SIZE}=${pageSize || DEFAULT_PAGE_SIZE}&${SORT}=${DEFAULT_SORT_ORDER}`);
    },

    publishAlbum: (cover, albumRequest) => {
        const formData = new FormData();
        formData.append(COVER, cover);
        console.log(albumRequest);
        formData.append(ALBUM_REQUEST, new Blob([albumRequest], {
            type: APPLICATION_JSON
        }));
        return axios.post(`${RESOURCE}${ALBUMS_PUBLISH}`, formData);
    },
    raiseTierAlbum: (albumRequest) => {
        return axios.post(`${RESOURCE}${ALBUMS_RAISE_TIER}`, albumRequest);
    },
}

export default AlbumRepository;