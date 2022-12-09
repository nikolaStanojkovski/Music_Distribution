import axios from "../../../custom-axios/axiosStreamingService";
import {GENRES} from "../../../constants/endpoint";

const GenreRepository = {
    fetchGenres: () => {
        return axios.get(GENRES);
    },
}

export default GenreRepository;