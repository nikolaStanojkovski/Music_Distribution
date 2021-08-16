import axios from '../custom-axios/axios-album-catalog';

const AlbumCatalogService = {
    fetchAlbums: () => {
        return axios.get("/albums");
    },
    fetchArtists: () => {
        return axios.get("/artists");
    },
    fetchSongs: () => {
        return axios.get("/songs");
    },
    fetchEmailDomains: () => {
        return axios.get("/emailDomains");
    },
    fetchGenres: () => {
        return axios.get("/genres");
    },
    createSong: (songName, lengthInSeconds, creatorId, albumId) => {
        return axios.post("/songs/create", {
            "songName" : songName,
            "lengthInSeconds" : lengthInSeconds,
            "creatorId" : creatorId,
            "albumId" : albumId
        });
    },
    createAlbum: (albumName, genre, totalLength, isPublished, artistName, producerName, composerName, creatorId) => {
        return axios.post("/albums/create", {
            "albumName" : albumName,
            "genre" : genre,
            "isPublished" : isPublished,
            "artistName" : artistName,
            "producerName" : producerName,
            "composerName" : composerName,
            "creatorId" : creatorId
        });
    },
    loginArtist: (username, domainName, password) => {
        return axios.post("/artists/login", {
            "username" : username,
            "domainName" : domainName,
            "password" : password
        });
    },
    registerArtist: (username, emailDomain, telephoneNumber, firstName, lastName, artName, password) => {
        return axios.post("/artists/register", {
            "username" : username,
            "emailDomain" : emailDomain,
            "telephoneNumber" : telephoneNumber,
            "firstName": firstName,
            "lastName": lastName,
            "artName": artName,
            "password": password
        });
    }
}

export default AlbumCatalogService;