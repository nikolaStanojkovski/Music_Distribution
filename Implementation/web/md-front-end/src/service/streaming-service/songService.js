import React from "react";
import SongRepository from "../../repository/streaming-service/songRepository";
import {PAGEABLE} from "../../constants/model";

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
                loadSongs(songs[PAGEABLE].pageNumber);
            });
    }

    const publishSong = (cover, songId, songTier, subscriptionFee, transactionFee) => {
        SongRepository.publishSong(cover, songId, songTier, subscriptionFee, transactionFee)
            .then(() => {
                loadSongs(songs[PAGEABLE].pageNumber);
            })
    }

    const raiseTierSong = (songId, songTier, subscriptionFee, transactionFee) => {
        SongRepository.raiseTierSong(songId, songTier, subscriptionFee, transactionFee)
            .then(() => {
                loadSongs(songs[PAGEABLE].pageNumber);
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