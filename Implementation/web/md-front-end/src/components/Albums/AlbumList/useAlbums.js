import React from 'react';
import SearchParamUtil from "../../../util/searchParamUtil";
import ScreenElementsUtil from "../../../util/screenElementsUtil";
import {ALBUM_COVER_URL, API_BASE_URL} from "../../../constants/endpoint";

const useAlbums = (props) => {

    const albums = props.albums;
    const filterAlbums = props.filterAlbums;
    const loadAlbums = props.loadAlbums;
    const [filter, setFilter] = React.useState(false);
    const [showModal, setShowModal] = React.useState(false);
    const [imageSource, updateImageSource] = React.useState(undefined);

    const fetchAlbums = () => {
        const searchParams = SearchParamUtil.getSearchParams();
        if (searchParams && searchParams.key && searchParams.value) {
            props.filterAlbums(0, searchParams.key, searchParams.value);
            setFilter(true);
        } else {
            props.loadAlbums(0);
        }
    }

    const fetchAlbumCover = (e, id) => {
        if (ScreenElementsUtil.isClickableTableRow(e, id)) {
            updateImageSource(`${API_BASE_URL}${ALBUM_COVER_URL}/${id}.png`);
            setShowModal(true);
        }
    }

    return { albums,
        filter,
        imageSource,
        showModal,
        setShowModal,
        fetchAlbums,
        fetchAlbumCover,
        filterAlbums,
        loadAlbums
    };
}

export default useAlbums;