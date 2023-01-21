import React from 'react';
import useSongCreate from "./useSongCreate";
import viewSongCreate from "./viewSongCreate";

const CreateSong = (props) => {

    const {
        genres,
        selectedArtist,
        lengthInSeconds,
        getFormattedSongLength,
        handleChange,
        handleUpload,
        onFormSubmit
    } = useSongCreate(props);

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
                    {
                        viewSongCreate(
                            {
                                genres,
                                selectedArtist,
                                lengthInSeconds,
                                getFormattedSongLength,
                                handleChange,
                                handleUpload,
                                onFormSubmit
                            }
                        )
                    }
                </div>
            </div>
        </div>
    );
};

export default CreateSong;