import React from "react";
import SongRepository from "../../repository/streaming-service/songRepository";
import {PAGEABLE} from "../../constants/model";
import {toast} from "react-toastify";
import {
    SONG_CREATION_FAILED,
    SONG_FETCH_FAILED,
    SONG_FILTER_FAILED,
    SONG_PUBLISHING_FAILED,
    SONG_RAISE_TIER_FAILED
} from "../../constants/exception";

const useSongService = () => {

    const [songs, setSongs] = React.useState([]);
    React.useEffect(() => {
        loadSongs(0);
    }, []);

    const loadSongs = (pageNumber) => {
        SongRepository.fetchSongs(pageNumber)
            .then((data) => {
                setSongs(data.data);
            }).catch(() => {
            toast.error(SONG_FETCH_FAILED);
        });
    }


    const filterSongs = (pageNumber, key, value) => {
        SongRepository.filterSongs(pageNumber, key, value)
            .then((data) => {
                setSongs(data.data);
            }).catch(() => {
            toast.error(SONG_FILTER_FAILED);
        });
    }

    const fetchSong = (id) => {
        return SongRepository.fetchSong(id);
    }

    const createSong = (file, songName, lengthInSeconds, songGenre) => {
        return SongRepository.createSong(file, songName, lengthInSeconds, songGenre)
            .then(() => {
                loadSongs(songs[PAGEABLE].pageNumber);
                return true;
            }).catch(() => {
                toast.error(SONG_CREATION_FAILED);
                return false;
            });
    }

    const publishSong = (cover, songId, songTier, subscriptionFee, transactionFee) => {
        return SongRepository.publishSong(cover, songId, songTier, subscriptionFee, transactionFee)
            .then(() => {
                loadSongs(songs[PAGEABLE].pageNumber);
                return true;
            }).catch(() => {
                toast.error(SONG_PUBLISHING_FAILED);
                return false;
            });
    }

    const raiseTierSong = (songId, songTier, subscriptionFee, transactionFee) => {
        return SongRepository.raiseTierSong(songId, songTier, subscriptionFee, transactionFee)
            .then(() => {
                loadSongs(songs[PAGEABLE].pageNumber);
                return true;
            }).catch(() => {
                toast.error(SONG_RAISE_TIER_FAILED);
                return false;
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