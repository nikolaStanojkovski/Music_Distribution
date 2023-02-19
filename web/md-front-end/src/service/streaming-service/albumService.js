import React from "react";
import AlbumRepository from "../../repository/streaming-service/albumRepository";
import {toast} from "react-toastify";
import {ALBUM_FETCH_FAILED, ALBUM_FILTER_FAILED} from "../../constants/exception";
import {TOTAL_ELEMENTS} from "../../constants/pagination";

const useAlbumService = () => {

    const [albums, setAlbums] = React.useState([]);
    const [albumsTotalLength, setAlbumsTotalLength] = React.useState(0);

    React.useEffect(() => {
        loadAlbums(0);
    }, []);

    const loadAlbums = (pageNumber) => {
        AlbumRepository.fetchAlbums(pageNumber)
            .then((data) => {
                setAlbums(data.data);
                setAlbumsTotalLength(data.data[TOTAL_ELEMENTS]);
            }).catch(() => {
            toast.error(ALBUM_FETCH_FAILED);
        });
    }

    const filterAlbums = (pageNumber, pageSize, key, value) => {
        AlbumRepository.filterAlbums(pageNumber, pageSize, key, value)
            .then((data) => {
                setAlbums(data.data);
            }).catch(() => {
            toast.error(ALBUM_FILTER_FAILED);
        });
    }

    return {albums, albumsTotalLength, loadAlbums, filterAlbums};
}

export default useAlbumService;