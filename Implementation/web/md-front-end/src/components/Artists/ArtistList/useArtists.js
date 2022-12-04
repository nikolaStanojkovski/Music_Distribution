import React from "react";
import ScreenElementsUtil from "../../../util/screenElementsUtil";
import {API_BASE_URL, ARTIST_PICTURE_URL} from "../../../constants/endpoint";

const useArtists = (props) => {
    const artists = props.artists;
    const [showModal, setShowModal] = React.useState(false);
    const [profilePictureSource, updateProfilePictureSource] = React.useState(null);

    const fetchArtistPicture = (e, id) => {
        if (ScreenElementsUtil.isClickableTableRow(e, id)) {
            updateProfilePictureSource(`${API_BASE_URL}${ARTIST_PICTURE_URL}/${id}.png`);
            setShowModal(true);
        }
    }

    return {artists, showModal, profilePictureSource, setShowModal, fetchArtistPicture};
}

export default useArtists;