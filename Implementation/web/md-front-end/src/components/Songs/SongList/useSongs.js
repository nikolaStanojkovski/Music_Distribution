import {API_BASE_URL, RESOURCE_STREAM, SONG_COVER_URL} from "../../../constants/endpoint";
import ScreenElementsUtil from "../../../util/screenElementsUtil";
import React from "react";
import SearchParamUtil from "../../../util/searchParamUtil";
import {MP3, PNG} from "../../../constants/extension";

const useSongs = (props) => {

    const songs = props.songs;
    const filterSongs = props.filterSongs;
    const loadSongs = props.loadSongs;
    const searchParams = SearchParamUtil.getSearchParams();
    const [filter, setFilter] = React.useState(false);
    const [showModal, setShowModal] = React.useState(false);
    const [imageSource, updateImageSource] = React.useState(undefined);
    const [audioPlayer, updateAudioPlayer] = React.useState(undefined);

    React.useEffect(() => {
        if (searchParams && searchParams.key && searchParams.value) {
            props.filterSongs(0, searchParams.key, searchParams.value);
            setFilter(true);
        } else {
            props.loadSongs(0);
        }
    }, []);

    const playAudio = (songId, button) => {
        if (button.classList.contains('bi-play')) {
            if (audioPlayer) {
                audioPlayer.pause();
            }
            const audio = new Audio(`${API_BASE_URL}${RESOURCE_STREAM}/${songId}.${MP3}`);
            if(audio) {
                audio.play().catch((error) => console.error(error));
                updateAudioPlayer(audio);
            }
        } else {
            if (audioPlayer) {
                audioPlayer.pause();
            }
        }
    }

    const fetchSong = (e) => {
        const button = e.target;
        ScreenElementsUtil.toggleSongPlayButton(button);

        const songId = ScreenElementsUtil.getTableRowId(button);
        if (songId) {
            playAudio(songId, button);
        }
    }

    const fetchSongCover = (e, id, isASingle) => {
        if (ScreenElementsUtil.isClickableTableRow(e, id) && isASingle) {
            updateImageSource(`${API_BASE_URL}${SONG_COVER_URL}/${id}.${PNG}`);
            setShowModal(true);
        }
    }

    return {
        songs,
        imageSource,
        showModal,
        filter,
        searchParams,
        setShowModal,
        fetchSongCover,
        fetchSong,
        filterSongs,
        loadSongs
    };
}

export default useSongs;