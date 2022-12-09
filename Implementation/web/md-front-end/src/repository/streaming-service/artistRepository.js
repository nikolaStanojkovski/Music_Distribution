import axios from "../../custom-axios/axiosStreamingService";
import {DEFAULT_PAGE_NUMBER, DEFAULT_PAGE_SIZE} from "../../constants/pagination";
import {ARTISTS, RESOURCE} from "../../constants/endpoint";
import {LOGGED_ARTIST} from "../../constants/auth";
import {PAGE, SIZE} from "../../constants/model";

const ArtistRepository = {
    fetchArtists: (pageNumber) => {
        return axios.get(`${RESOURCE}${ARTISTS}?${PAGE}=${pageNumber || DEFAULT_PAGE_NUMBER}&${SIZE}=${DEFAULT_PAGE_SIZE}`);
    },
    fetchArtistLocal: () => {
        return JSON.parse(localStorage.getItem(LOGGED_ARTIST));
    }
}

export default ArtistRepository;