import axios from "../../../custom-axios/axiosStorageService";

const GenreRepository = {
    fetchGenres: () => {
        return axios.get("/genres");
    },
}

export default GenreRepository;