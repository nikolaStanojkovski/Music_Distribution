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
    publishAlbum: (albumId, artistId, musicPublisherId, albumTier, subscriptionFee, transactionFee) => {
        return axios.post("/distributors/publish", {
            "albumId" : albumId,
            "artistId" : artistId,
            "musicPublisherId" : musicPublisherId,
            "albumTier" : albumTier,
            "subscriptionFee" : subscriptionFee,
            "transactionFee" : transactionFee
        });
    },
};

export default AlbumPublishingService;