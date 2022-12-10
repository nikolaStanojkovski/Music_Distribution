import React from 'react';
import SearchParamUtil from "../../../util/searchParamUtil";
import ScreenElementsUtil from "../../../util/screenElementsUtil";
import {ALBUM_COVER_URL, API_BASE_URL} from "../../../constants/endpoint";
import {PNG} from "../../../constants/extension";

const useAlbums = (props) => {

    const albums = props.albums;
    const filterAlbums = props.filterAlbums;
    const loadAlbums = props.loadAlbums;
    const searchParams = SearchParamUtil.getSearchParams();
    const [filter, setFilter] = React.useState(false);
    const [showModal, setShowModal] = React.useState(false);
    const [imageSource, updateImageSource] = React.useState(undefined);

    React.useEffect(() => {
        if (searchParams && searchParams.key && searchParams.value) {
            filterAlbums(0, searchParams.key, searchParams.value);
            setFilter(true);
        } else {
            loadAlbums(0);
        }
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, []);

    const fetchAlbumCover = (e, id) => {
        if (ScreenElementsUtil.isClickableTableRow(e, id)) {
            updateImageSource(`${API_BASE_URL}${ALBUM_COVER_URL}/${id}.${PNG}`);
            setShowModal(true);
        }
    }

    return {
        albums,
        filter,
        imageSource,
        showModal,
        searchParams,
        setShowModal,
        fetchAlbumCover,
        filterAlbums,
        loadAlbums
    };
}

export default useAlbums;