import React from "react";
import ArtistRepository from "../../../repository/streaming-service/artistRepository";
import GenreRepository from "../../../repository/streaming-service/enum/genreRepository";
import {toast} from "react-toastify";
import {GENRE_FETCH_FAILED} from "../../../constants/exception";

const useGenreService = () => {

    const [genres, setGenres] = React.useState([]);
    React.useEffect(() => {
        loadGenres();
    }, []);

    const loadGenres = () => {
        if (ArtistRepository.fetchArtistLocal()) {
            GenreRepository.fetchGenres()
                .then((data) => {
                    setGenres(data.data);
                }).catch(() => {
                toast.error(GENRE_FETCH_FAILED);
            });
        }
    }

    return {genres};
}

export default useGenreService;