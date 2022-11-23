import React from 'react';
import {useHistory} from 'react-router-dom';

const CreateSong = (props) => {

    const History = useHistory();
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

    return (
        <div className="container mm-4 my-5">
            <div className={"row mb-5"}>
                <div className={"col-md"}>
                    <h1 className="display-5">Upload a new song</h1>
                    <p className="text-muted">Share with us a recent song from your music collection.</p>
                </div>
            </div>

            <div className={"row"}>
                <div className="col-md">
                    <form onSubmit={onFormSubmit}>
                        <div className="form-group">
                            <label className="upload-drop-container">
                                <span className="upload-drop-title">Drop files here</span>
                                <input type="file" id="songUpload" accept="audio/mpeg"
                                       onChange={(e) => handleUpload(e.target.files[0])}/>
                                <span className={"text-muted"}><b>mpeg</b> file format accepted</span>
                            </label>
                        </div>
                        <br/>

                        <div className="form-group">
                            <input type="text"
                                   className="form-control"
                                   id="songName"
                                   name="songName"
                                   required
                                   placeholder="Enter the song name"
                                   onChange={handleChange}/>
                        </div>
                        <br/>

                        <div className="form-group">
                            <select onChange={handleChange} name="songGenre" className="form-control">
                                <option className={"text-muted"} value={null} disabled={true} selected={true}>
                                    -- Choose song genre --
                                </option>
                                {(props.genres) ? props.genres.map((term) => {
                                        return <option key={term} value={term}>{term}</option>;
                                    }
                                ) : undefined}
                            </select>
                        </div>
                        <br/>

                        <div className="form-group">
                            <input name="artistName" disabled={true} required={true}
                                   value={props.selectedArtist['userPersonalInfo'].fullName}
                                   className="form-control disabled"/>
                        </div>
                        <br/>

                        <div className="form-group">
                            <input name="songLength" disabled={true} id={"songLength"} required={true}
                                   value={(lengthInSeconds > 0) ? getFormattedSongLength(lengthInSeconds) : "-- Song length --"}
                                   className="form-control disabled"/>
                        </div>
                        <br/>

                        <br/>

                        <button id="submit" type="submit" className="btn btn-dark w-100">Submit</button>
                        <br/>
                    </form>

                </div>
            </div>
        </div>
    );
};

export default CreateSong;