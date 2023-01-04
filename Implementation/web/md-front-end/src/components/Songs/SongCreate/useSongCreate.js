import React from "react";
import StringUtil from "../../../util/stringUtil";
import {EMPTY_STRING} from "../../../constants/alphabet";
import {toast} from "react-toastify";
import {SONG_AUDIO_READ_FAILED, SONG_CREATION_FAILED} from "../../../constants/exception";
import {AUDIO_ELEMENT, DURATION_CHANGE_EVENT} from "../../../constants/screen";
import ScreenElementsUtil from "../../../util/screenElementsUtil";
import RequestUtil from "../../../util/requestUtil";

const useSongCreate = (props) => {

    const genres = props.genres;
    const selectedArtist = props.selectedArtist;
    const [song, updateSong] = React.useState(null);
    const [lengthInSeconds, updateLengthInSeconds] = React.useState(0);
    const [formData, updateFormData] = React.useState({
        songName: EMPTY_STRING,
        songGenre: EMPTY_STRING
    });

    const getFormattedSongLength = (lengthInSeconds) => {
        const minutes = StringUtil.formatMinutes(lengthInSeconds);
        const seconds = StringUtil.formatSeconds(lengthInSeconds);
        return `${minutes}:${seconds}`;
    }

    const handleChange = (e) => {
        updateFormData({
            ...formData,
            [e.target.name]: e.target.value,
        });
    }

    const handleUpload = (file) => {
        const reader = new FileReader();
        const audio = document.createElement(AUDIO_ELEMENT);
        reader.onload = function (e) {
            const result = e.target.result;
            if (result) {
                audio.src = result.toString();
                audio.addEventListener(DURATION_CHANGE_EVENT, function () {
                    const duration = audio.duration;
                    if (duration) {
                        const lengthInSeconds = parseInt(audio.duration.toString());
                        updateSong(file);
                        updateLengthInSeconds(lengthInSeconds);
                    }
                }, false);
            } else {
                toast.error(SONG_AUDIO_READ_FAILED);
            }
        };
        reader.readAsDataURL(file);
    }

    const onFormSubmit = async (e) => {
        e.preventDefault();

        const songName = formData.songName;
        const songGenre = formData.songGenre;

        if (songName && songGenre && lengthInSeconds && songGenre) {
            const songRequest = RequestUtil
                .constructSongCreateRequest(songName, lengthInSeconds, songGenre);
            if (await props.createSong(song, JSON.stringify(songRequest))) {
                ScreenElementsUtil.reloadDomain();
            } else {
                toast.error(SONG_CREATION_FAILED);
            }
        } else {
            toast.error(SONG_CREATION_FAILED);
        }
    }

    return {genres, selectedArtist, lengthInSeconds, getFormattedSongLength, handleChange, handleUpload, onFormSubmit};
}

export default useSongCreate;