import axios from '../custom-axios/axios-album-catalog';

const AlbumCatalogService = {
    loginArtist: (username, domainName, password) => {
        return axios.post("/auth/login", {
            "username": username,
            "emailDomain": domainName,
            "password": password
        });
    },
    registerArtist: (username, emailDomain, telephoneNumber, firstName, lastName, artName, password) => {
        return axios.post("/auth/register", {
            "username": username,
            "emailDomain": emailDomain,
            "telephoneNumber": telephoneNumber,
            "firstName": firstName,
            "lastName": lastName,
            "artName": artName,
            "password": password
        });
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
    publishSong: (songId, songTier, subscriptionFee, transactionFee) => {
        return axios.post("/resource/songs/publish", {
            "songId" : songId,
            "songTier" : songTier,
            "subscriptionFee" : subscriptionFee,
            "transactionFee" : transactionFee,
        })
    },

    createAlbum: (albumName, genre, totalLength, isPublished, artistName, producerName, composerName, creatorId) => {
        return axios.post("/resource/albums/create", {
            "albumName": albumName,
            "genre": genre,
            "isPublished": isPublished,
            "artistName": artistName,
            "producerName": producerName,
            "composerName": composerName,
            "creatorId": creatorId
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