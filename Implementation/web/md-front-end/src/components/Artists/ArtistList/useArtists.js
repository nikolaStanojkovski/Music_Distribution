import React from "react";
import ScreenElementsUtil from "../../../util/screenElementsUtil";
import {API_BASE_URL, ARTIST_PICTURE_URL} from "../../../constants/endpoint";
import {PNG} from "../../../constants/extension";

const useArtists = (props) => {

    const artists = props.artists;
    const loadArtists = props.loadArtists;
    const [fetch, setFetch] = React.useState(false);
    const [showModal, setShowModal] = React.useState(false);
    const [profilePictureSource, updateProfilePictureSource] = React.useState(null);

    React.useEffect(() => {
        if(!fetch) {
            loadArtists(0);
            setFetch(true);
        }
    }, [loadArtists, fetch]);

    const fetchArtistPicture = (e, id) => {
        if (ScreenElementsUtil.isClickableTableRow(e, id)) {
            updateProfilePictureSource(`${API_BASE_URL}${ARTIST_PICTURE_URL}/${id}.${PNG}`);
            setShowModal(true);
        }
    }

    return {artists, showModal, profilePictureSource, setShowModal, loadArtists, fetchArtistPicture};
}

export default useArtists;