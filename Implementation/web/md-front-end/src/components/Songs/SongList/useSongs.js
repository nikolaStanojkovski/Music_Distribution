import {API_BASE_URL, RESOURCE_STREAM, SONG_COVER_URL} from "../../../constants/endpoint";
import ScreenElementsUtil from "../../../util/screenElementsUtil";
import React from "react";
import SearchParamUtil from "../../../util/searchParamUtil";
import {MP3, PNG} from "../../../constants/extension";
import {toast} from "react-toastify";
import {SONG_AUDIO_PLAY_FAILED} from "../../../constants/exception";
import {STOP_BUTTON} from "../../../constants/screen";
import AuthUtil from "../../../util/authUtil";
import {ALBUM, IS_A_SINGLE, IS_PUBLISHED} from "../../../constants/model";
import {EMPTY_STRING} from "../../../constants/alphabet";
import {DEFAULT_PAGE_SIZE} from "../../../constants/pagination";

const useSongs = (props) => {

    const songs = props.songs;
    const filterSongs = props.filterSongs;
    const loadSongs = props.loadSongs;
    const searchParams = SearchParamUtil.getSearchParams();
    const [fetch, setFetch] = React.useState(false);
    const [filter, setFilter] = React.useState(false);
    const [showModal, setShowModal] = React.useState(false);
    const [imageSource, updateImageSource] = React.useState(undefined);
    const [audioPlayer, updateAudioPlayer] = React.useState(undefined);

    React.useEffect(() => {
        const fetchSongs = () => {
            if (searchParams && searchParams.key && searchParams.value) {
                filterSongs(0, DEFAULT_PAGE_SIZE, searchParams.key, searchParams.value);
                setFilter(true);
            } else {
                loadSongs(0);
            }
        };

        if (!fetch) {
            fetchSongs();
            setFetch(true);
        }
    }, [searchParams, filterSongs, loadSongs, fetch]);

    const playAudio = (songId, button) => {
        if (button.classList.contains(STOP_BUTTON)) {
            if (audioPlayer) {
                audioPlayer.pause();
            }
            const audio = new Audio(`${API_BASE_URL}${RESOURCE_STREAM}/${songId}.${MP3}`);
            audio.play().catch(() => toast.error(SONG_AUDIO_PLAY_FAILED));
            updateAudioPlayer(audio);
        } else {
            if (audioPlayer) {
                audioPlayer.pause();
            } else {
                toast.error(SONG_AUDIO_PLAY_FAILED);
            }
        }
    }

    const fetchSong = (e) => {
        const button = e.target;
        ScreenElementsUtil.toggleSongPlayButton(button);

        const songId = ScreenElementsUtil.getTableRowId(button);
        if (songId) {
            playAudio(songId, button);
        } else {
            toast.error(SONG_AUDIO_PLAY_FAILED);
        }
    }

    const fetchSongCover = (e, id, isASingle) => {
        if (ScreenElementsUtil.isClickableTableRow(e, id) && isASingle) {
            updateImageSource(`${API_BASE_URL}${SONG_COVER_URL}/${id}.${PNG}`);
            setShowModal(true);
        }
    }

    const renderTableRowHeader = () => {
        if(!AuthUtil.isAuthorized()) {
            return null;
        }
        return [
            <th scope={"col"}>Is Published</th>,
            <th scope={"col"}>Is Single</th>,
            <th scope={"col"}>Album</th>
        ];
    }

    const renderTableRowData = (term) => {
        if(!AuthUtil.isAuthorized()) {
            return null;
        }
        return [
            <td>{(term[IS_PUBLISHED]) ? 'Yes' : 'No'}</td>,
            <td>{(term[IS_A_SINGLE]) ? 'Yes' : 'No'}</td>,
            <td>{(term[ALBUM]) ? term[ALBUM].albumName : EMPTY_STRING}</td>
        ];
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
        loadSongs,
        renderTableRowHeader,
        renderTableRowData
    };
}

export default useSongs;