import React from "react";
import AlbumRepository from "../repository/streaming-service/albumRepository";

const useAlbumService = () => {

    const [albums, setAlbums] = React.useState([]);
    React.useEffect(() => {
        loadAlbums(0);
    }, []);

    const loadAlbums = (pageNumber) => {
        AlbumRepository.fetchAlbums(pageNumber)
            .then((data) => {
                setAlbums(data.data);
            });
    }

    const filterAlbums = (pageNumber, key, value) => {
        AlbumRepository.filterAlbums(pageNumber, key, value)
            .then((data) => {
                setAlbums(data.data);
            });
    }

    const publishAlbum = (cover, songIdList,
                          albumName, albumGenre, albumTier,
                          subscriptionFee, transactionFee,
                          artistName, producerName, composerName) => {
        AlbumRepository.publishAlbum(cover, songIdList,
            albumName, albumGenre, albumTier,
            subscriptionFee, transactionFee,
            artistName, producerName, composerName)
            .then(() => {
                loadAlbums(albums['pageable'].pageNumber);
            });
    }

    const raiseTierAlbum = (albumId, albumTier, subscriptionFee, transactionFee) => {
        AlbumRepository.raiseTierAlbum(albumId, albumTier, subscriptionFee, transactionFee)
            .then(() => {
                loadAlbums(albums['pageable'].pageNumber);
            });
    }

    return {albums, loadAlbums, filterAlbums, publishAlbum, raiseTierAlbum};
}

export default useAlbumService;