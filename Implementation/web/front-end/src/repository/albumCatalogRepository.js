import axios from '../custom-axios/axios-album-catalog';

const AlbumCatalogService = {
    loginArtist: (username, domainName, password) => {
        return axios.post("/auth/login", {
            "username": username,
            "emailDomain": domainName,
            "password": password
        });
    },
    registerArtist: (profilePicture, username, emailDomain, telephoneNumber, firstName, lastName, artName, password) => {
        const formData = new FormData();
        formData.append('profilePicture', profilePicture);
        formData.append('artistRequest', new Blob([JSON.stringify({
            "username": username,
            "emailDomain": emailDomain,
            "telephoneNumber": telephoneNumber,
            "firstName": firstName,
            "lastName": lastName,
            "artName": artName,
            "password": password
        })], {
            type: "application/json"
        }));
        return axios.post("/auth/register", formData);
    },
    logoutArtist: () => {
        if (localStorage.getItem('accessToken')) {
            localStorage.removeItem('accessToken');
            localStorage.removeItem('loggedArtist');
        }
    },

    fetchAlbums: () => {
        return axios.get("/resource/albums");
    },
    fetchArtists: () => {
        return axios.get("/resource/artists");
    },
    fetchSongs: () => {
        return axios.get("/resource/songs");
    },

    fetchEmailDomains: () => {
        return axios.get("/email-domains");
    },
    fetchGenres: () => {
        return axios.get("/genres");
    },
    fetchTiers: () => {
        return axios.get("/tiers");
    },

    filterSongs: (key, value) => {
        return axios.get(`/resource/songs/search?searchParams=${key}&searchTerm=${value}`);
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

    filterAlbums: (key, value) => {
        return axios.get(`/resource/albums/search?searchParams=${key}&searchTerm=${value}`);
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

    getTransactionFee: (locale) => {
        return axios.get(`/payment/transaction?locale=${locale}`);
    },
    getSubscriptionFee: (tier) => {
        return axios.get(`/payment/subscription?tier=${tier}`);
    },
}

export default AlbumCatalogService;