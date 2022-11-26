import axios from "../../../custom-axios/axiosStreamingService";

const GenreRepository = {
    fetchGenres: () => {
        return axios.get("/genres");
    },
}

export default GenreRepository;