import axios from "../../custom-axios/axiosStorageService";
import {DEFAULT_PAGE_NUMBER, DEFAULT_PAGE_SIZE} from "../../constants/pagination";

const SongRepository = {
    fetchSongs: (pageNumber) => {
        return axios.get(`/resource/songs?page=${pageNumber || DEFAULT_PAGE_NUMBER}&size=${DEFAULT_PAGE_SIZE}`);
    },
    filterSongs: (pageNumber, key, value) => {
        return axios.get(`/resource/songs/search?searchParams=${key}&searchTerm=${value}&page=${pageNumber || DEFAULT_PAGE_NUMBER}&size=${DEFAULT_PAGE_SIZE}`);
    },

    fetchSong: (id) => {
        return axios.get(`/resource/songs/file/${id}`);
    },
    createSong: (file, songName, lengthInSeconds, songGenre) => {
        const formData = new FormData();
        formData.append('file', file);
        formData.append('songRequest', new Blob([JSON.stringify({
            "songName": songName,
            "lengthInSeconds": lengthInSeconds,
            "songGenre": songGenre
        })], {
            type: "application/json"
        }));
        return axios.post("/resource/songs/create", formData);
    },
    publishSong: (cover, songId, songTier, subscriptionFee, transactionFee) => {
        const formData = new FormData();
        formData.append('cover', cover);
        formData.append('songTransactionRequest', new Blob([JSON.stringify({
            "songId": songId,
            "songTier": songTier,
            "subscriptionFee": subscriptionFee,
            "transactionFee": transactionFee,
        })], {
            type: "application/json"
        }));
        return axios.post("/resource/songs/publish", formData);
    },
    raiseTierSong: (songId, songTier, subscriptionFee, transactionFee) => {
        return axios.post("/resource/songs/raise-tier", {
            "songId": songId,
            "songTier": songTier,
            "subscriptionFee": subscriptionFee,
            "transactionFee": transactionFee,
        });
    },
}

export default SongRepository;