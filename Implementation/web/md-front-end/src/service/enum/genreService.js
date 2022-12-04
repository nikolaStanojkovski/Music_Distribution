import React from "react";
import ArtistRepository from "../../repository/streaming-service/artistRepository";
import GenreRepository from "../../repository/streaming-service/enum/genreRepository";

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
                });
        }
    }

    return {genres};
}

export default useGenreService;