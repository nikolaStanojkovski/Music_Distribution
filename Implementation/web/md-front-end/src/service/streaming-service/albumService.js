import React from "react";
import AlbumRepository from "../../repository/streaming-service/albumRepository";
import {PAGEABLE} from "../../constants/model";
import {toast} from "react-toastify";
import {
    ALBUM_FETCH_FAILED,
    ALBUM_FILTER_FAILED,
    ALBUM_PUBLISHING_FAILED,
    ALBUM_RAISE_TIER_FAILED
} from "../../constants/exception";

const useAlbumService = () => {

    const [albums, setAlbums] = React.useState([]);
    React.useEffect(() => {
        loadAlbums(0);
    }, []);

    const loadAlbums = (pageNumber) => {
        AlbumRepository.fetchAlbums(pageNumber)
            .then((data) => {
                setAlbums(data.data);
            }).catch(() => {
            toast.error(ALBUM_FETCH_FAILED);
        });
    }

    const filterAlbums = (pageNumber, key, value) => {
        AlbumRepository.filterAlbums(pageNumber, key, value)
            .then((data) => {
                setAlbums(data.data);
            }).catch(() => {
            toast.error(ALBUM_FILTER_FAILED);
        });
    }

    const publishAlbum = (cover, songIdList,
                          albumName, albumGenre, albumTier,
                          subscriptionFee, transactionFee,
                          artistName, producerName, composerName) => {
        return AlbumRepository.publishAlbum(cover, songIdList,
            albumName, albumGenre, albumTier,
            subscriptionFee, transactionFee,
            artistName, producerName, composerName)
            .then(() => {
                loadAlbums(albums[PAGEABLE].pageNumber);
                return true;
            }).catch(() => {
            toast.error(ALBUM_PUBLISHING_FAILED);
            return false;
        });
    }

    const raiseTierAlbum = (albumId, albumTier, subscriptionFee, transactionFee) => {
        return AlbumRepository.raiseTierAlbum(albumId, albumTier, subscriptionFee, transactionFee)
            .then(() => {
                loadAlbums(albums[PAGEABLE].pageNumber);
                return true;
            }).catch(() => {
            toast.error(ALBUM_RAISE_TIER_FAILED);
            return false;
        });
    }

    return {albums, loadAlbums, filterAlbums, publishAlbum, raiseTierAlbum};
}

export default useAlbumService;