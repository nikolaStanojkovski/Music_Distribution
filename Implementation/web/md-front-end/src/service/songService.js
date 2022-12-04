import React from "react";
import SongRepository from "../repository/streaming-service/songRepository";

const useSongService = () => {

    const [songs, setSongs] = React.useState([]);
    React.useEffect(() => {
        loadSongs(0);
    }, []);

    const loadSongs = (pageNumber) => {
        SongRepository.fetchSongs(pageNumber)
            .then((data) => {
                setSongs(data.data);
            });
    }


    const filterSongs = (pageNumber, key, value) => {
        SongRepository.filterSongs(pageNumber, key, value)
            .then((data) => {
                setSongs(data.data);
            });
    }

    const fetchSong = (id) => {
        return SongRepository.fetchSong(id);
    }

    const createSong = (file, songName, lengthInSeconds, songGenre) => {
        SongRepository.createSong(file, songName, lengthInSeconds, songGenre)
            .then(() => {
                loadSongs(songs['pageable'].pageNumber);
            });
    }

    const publishSong = (cover, songId, songTier, subscriptionFee, transactionFee) => {
        SongRepository.publishSong(cover, songId, songTier, subscriptionFee, transactionFee)
            .then(() => {
                loadSongs(songs['pageable'].pageNumber);
            })
    }

    const raiseTierSong = (songId, songTier, subscriptionFee, transactionFee) => {
        SongRepository.raiseTierSong(songId, songTier, subscriptionFee, transactionFee)
            .then(() => {
                loadSongs(songs['pageable'].pageNumber);
            });
    }

    return {
        songs,
        loadSongs,
        filterSongs,
        fetchSong,
        createSong,
        publishSong,
        raiseTierSong
    };
}

export default useSongService;