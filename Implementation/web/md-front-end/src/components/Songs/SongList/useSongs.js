import {API_BASE_URL, AUDIO_STREAM_URL, SONG_COVER_URL} from "../../../constants/endpoint";
import ScreenElementsUtil from "../../../util/screenElementsUtil";
import React from "react";
import SearchParamUtil from "../../../util/searchParamUtil";

const useSongs = (props) => {

    const songs = props.songs;
    const filterSongs = props.filterSongs;
    const loadSongs = props.loadSongs;
    const [filter, setFilter] = React.useState(false);
    const [showModal, setShowModal] = React.useState(false);
    const [imageSource, updateImageSource] = React.useState(undefined);
    const [audioPlayer, updateAudioPlayer] = React.useState(undefined);

    React.useEffect(() => {
        const searchParams = SearchParamUtil.getSearchParams();
        if (searchParams && searchParams.key && searchParams.value) {
            props.filterSongs(0, searchParams.key, searchParams.value);
            setFilter(true);
        } else {
            props.loadSongs(0);
        }
    }, []);

    const togglePlayButtonClassList = (button) => {
        button.classList.toggle('bi-play');
        button.classList.toggle('bi-stop');
    }

    const togglePlayButton = (button) => {
        if (button && button instanceof HTMLElement) {
            togglePlayButtonClassList(button);
            const songId = button.parentElement.parentElement.getAttribute('data-id');
            Array.from(document.querySelectorAll(".play-pause-button")).forEach((btn) => {
                const itemSongId = btn.parentElement.parentElement.getAttribute('data-id');
                if (itemSongId !== songId && btn.classList.contains('bi-stop')) {
                    togglePlayButtonClassList(btn);
                }
            });
        }
    }

    const playAudio = (songId, button) => {
        if (button.classList.contains('bi-play')) {
            if (audioPlayer) {
                audioPlayer.pause();
            }
            const audio = new Audio(`${API_BASE_URL}${AUDIO_STREAM_URL}/${songId}.mp3`);
            audio.play().catch((error) => console.error(error));
            updateAudioPlayer(audio);
        } else {
            if (audioPlayer) {
                audioPlayer.pause();
            }
        }
    }

    const fetchSong = (e) => {
        const button = e.target;
        if (button && button instanceof HTMLElement) {
            const buttonCellWrapper = button.parentElement;
            if (buttonCellWrapper && buttonCellWrapper instanceof HTMLElement) {
                const tableRow = buttonCellWrapper.parentElement;
                if (tableRow && tableRow instanceof HTMLElement
                    && tableRow.hasAttributes()) {
                    const songId = tableRow.getAttribute('data-id');
                    if (songId) {
                        playAudio(songId, button);
                    }
                }
            }


            togglePlayButton(button);
        }
    }

    const fetchSongCover = (e, id, isASingle) => {
        if (ScreenElementsUtil.isClickableTableRow(e, id) && isASingle) {
            updateImageSource(`${API_BASE_URL}${SONG_COVER_URL}/${id}.png`);
            setShowModal(true);
        }
    }

    return {
        songs,
        imageSource,
        showModal,
        filter,
        setShowModal,
        fetchSongCover,
        fetchSong,
        filterSongs,
        loadSongs
    };
}

export default useSongs;