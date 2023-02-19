import axios from "../../../api/apiStreamingService";
import {GENRES} from "../../../constants/endpoint";

const GenreRepository = {
    fetchGenres: () => {
        return axios.get(GENRES);
    },
}

export default GenreRepository;