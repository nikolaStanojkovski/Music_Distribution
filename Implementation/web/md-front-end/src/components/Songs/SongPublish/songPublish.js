import React from 'react';
import useSongPublish from "./useSongPublish";
import viewSongPublish from "./viewSongPublish";

const PublishSong = (props) => {

    const {
        songs,
        tiers,
        selectedArtist,
        subscriptionFee,
        transactionFee,
        updateCover,
        handleChange,
        onFormSubmit
    } = useSongPublish(props);

    return (
        <div className="container mm-4 my-5">
            <div className={"row mb-5"}>
                <div className={"col-md"}>
                    <h1 className="display-5">Share your new song with the public</h1>
                    <p className="text-muted">Make your new music known to the general audience by sharing it.</p>
                </div>
            </div>

            <div className={"row"}>
                <div className="col-md">
                    {
                        viewSongPublish(
                            {
                                songs,
                                tiers,
                                selectedArtist,
                                subscriptionFee,
                                transactionFee,
                                updateCover,
                                handleChange,
                                onFormSubmit
                            }
                        )
                    }
                </div>
            </div>
        </div>
    );
};

export default PublishSong;