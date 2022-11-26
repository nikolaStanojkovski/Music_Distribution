import axios from "../../custom-axios/axiosStreamingService";
import {DEFAULT_PAGE_NUMBER, DEFAULT_PAGE_SIZE} from "../../constants/pagination";

const ArtistRepository = {
    fetchArtists: (pageNumber) => {
        return axios.get(`/resource/artists?page=${pageNumber || DEFAULT_PAGE_NUMBER}&size=${DEFAULT_PAGE_SIZE}`);
    },
    fetchArtistLocal: () => {
        return JSON.parse(localStorage.getItem('loggedArtist'));
    }
}

export default ArtistRepository;