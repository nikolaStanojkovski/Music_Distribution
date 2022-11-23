import axios from "../../custom-axios/axiosStorageService";
import {DEFAULT_PAGE_NUMBER, DEFAULT_PAGE_SIZE} from "../../constants/pagination";

const AlbumRepository = {
    fetchAlbums: (pageNumber) => {
        return axios.get(`/resource/albums?page=${pageNumber || DEFAULT_PAGE_NUMBER}&size=${DEFAULT_PAGE_SIZE}`);
    },
    filterAlbums: (pageNumber, key, value) => {
        return axios.get(`/resource/albums/search?searchParams=${key}&searchTerm=${value}&page=${pageNumber || DEFAULT_PAGE_NUMBER}&size=${DEFAULT_PAGE_SIZE}`);
    },

    publishAlbum: (cover, songIdList,
                   albumName, albumGenre, albumTier,
                   subscriptionFee, transactionFee,
                   artistName, producerName, composerName) => {
        const formData = new FormData();
        formData.append('cover', cover);
        formData.append('albumTransactionRequest', new Blob([JSON.stringify({
            "songIdList": songIdList,
            "albumName": albumName,
            "albumGenre": albumGenre,
            "albumTier": albumTier,
            "subscriptionFee": subscriptionFee,
            "transactionFee": transactionFee,
            "artistName": artistName,
            "producerName": producerName,
            "composerName": composerName,
        })], {
            type: "application/json"
        }));
        return axios.post("/resource/albums/publish", formData);
    },
    raiseTierAlbum: (albumId, albumTier, subscriptionFee, transactionFee) => {
        return axios.post("/resource/albums/raise-tier", {
            "albumId": albumId,
            "albumTier": albumTier,
            "subscriptionFee": subscriptionFee,
            "transactionFee": transactionFee,
        });
    },
}

export default AlbumRepository;