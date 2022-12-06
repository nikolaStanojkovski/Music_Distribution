import {useHistory} from "react-router-dom";
import React from "react";

const useSongCreate = (props) => {

    const History = useHistory();

    const genres = props.genres;
    const selectedArtist = props.selectedArtist;
    const [song, updateSong] = React.useState(null);
    const [lengthInSeconds, updateLengthInSeconds] = React.useState(0);
    const [formData, updateFormData] = React.useState({
        songName: "",
        songGenre: ""
    });

    const getFormattedSongLength = (lengthInSeconds) => {
        const minutes = parseInt((lengthInSeconds / 60).toString());
        const seconds = parseInt((lengthInSeconds % 60).toString());
        return `${(minutes > 10) ? minutes : '0' + minutes}:${(seconds > 10) ? seconds : '0' + seconds}`;
    }

    const handleChange = (e) => {
        updateFormData({
            ...formData,
            [e.target.name]: e.target.value,
        });
    }

    const handleUpload = (file) => {
        const reader = new FileReader();
        const audio = document.createElement('audio');
        reader.onload = function (e) {
            const result = e.target.result;
            if (result) {
                audio.src = result.toString();
                audio.addEventListener('durationchange', function () {
                    const duration = audio.duration;
                    if (duration) {
                        const lengthInSeconds = parseInt(audio.duration.toString());
                        updateSong(file);
                        updateLengthInSeconds(lengthInSeconds);
                    }
                }, false);
            }
        };
        reader.readAsDataURL(file);
    }

    const onFormSubmit = (e) => {
        e.preventDefault();

        const songName = formData.songName;
        const songGenre = formData.songGenre;

        if(songName && songGenre && lengthInSeconds && songGenre) {
            props.createSong(song, songName, lengthInSeconds, songGenre);
            History.push("/");
        }
    }

    return {genres, selectedArtist, lengthInSeconds, getFormattedSongLength, handleChange, handleUpload, onFormSubmit};
}

export default useSongCreate;