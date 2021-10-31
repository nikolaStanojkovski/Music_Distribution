import axios from '../custom-axios/axios-album-publishing';

const AlbumPublishingService = {
    fetchDistributors: () => {
        return axios.get("/distributors");
    },
    fetchPublishedAlbums: () => {
        return axios.get("/publishedAlbums");
    },
    fetchAlbumTiers: () => {
        return axios.get("/albumTiers");
    },
    publishAlbum: (albumId, albumName, artistId, artistInformation, musicPublisherId, albumTier, subscriptionFee, transactionFee) => {
        return axios.post("/distributors/publish", {
            "albumId" : albumId,
            "albumName" : albumName,
            "artistId" : artistId,
            "artistInformation" : artistInformation,
            "musicPublisherId" : musicPublisherId,
            "albumTier" : albumTier,
            "subscriptionFee" : subscriptionFee,
            "transactionFee" : transactionFee
        });
    },
    unpublishAlbum: (publishedAlbumId) => {
        return axios.post("/distributors/unPublish", {
            "id" : publishedAlbumId
        });
    },
    raiseAlbumTier: (publishedAlbumId, albumTier, subscriptionFee, transactionFee) => {
        return axios.post("/distributors/raiseAlbumTier", {
            "publishedAlbumId" : publishedAlbumId,
            "albumTier" : albumTier,
            "subscriptionFee" : subscriptionFee,
            "transactionFee" : transactionFee,
        });
    },
};

export default AlbumPublishingService;