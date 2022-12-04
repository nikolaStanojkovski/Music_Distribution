import React from 'react';
import useAlbumPublish from "./useAlbumPublish";
import viewAlbumPublish from "./viewAlbumPublish";

const PublishAlbum = (props) => {

    const {
        transactionFee,
        subscriptionFee,
        genres,
        tiers,
        songs,
        selectedArtist,
        updateCover,
        updateSongIdList,
        handleChange,
        onFormSubmit
    } = useAlbumPublish(props);

    return (
        <div className="container mm-4 my-5">
            <div className={"row mb-5"}>
                <div className={"col-md"}>
                    <h1 className="display-5">Share your new album</h1>
                    <p className="text-muted">Inform your fans and listeners about the emotional experience you
                        documented.</p>
                </div>
            </div>
            <div className={"row"}>
                <div className="col-md">
                    {
                        viewAlbumPublish({
                            transactionFee,
                            subscriptionFee,
                            genres,
                            tiers,
                            songs,
                            selectedArtist,
                            updateCover,
                            updateSongIdList,
                            handleChange,
                            onFormSubmit
                        })
                    }
                </div>
            </div>
        </div>
    );
};

export default PublishAlbum;