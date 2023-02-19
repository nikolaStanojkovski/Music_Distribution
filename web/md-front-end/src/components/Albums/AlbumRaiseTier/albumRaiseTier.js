import React from 'react';
import useAlbumRaiseTier from "./useAlbumRaiseTier";
import viewAlbumRaiseTier from "./viewAlbumRaiseTier";

const AlbumRaiseTier = (props) => {

    const {
        subscriptionFee,
        transactionFee,
        albums,
        tiers,
        onFormSubmit,
        handleAlbumChange,
        handleAlbumTier
    } = useAlbumRaiseTier(props);

    return (
        <div className="container mm-4 my-5">
            <div className={"row mb-5"}>
                <div className={"col-md"}>
                    <h1 className="display-5">Raise an album's tier</h1>
                    <p className="text-muted">Increase the popularity of the music in your album by raising its
                        tier.</p>
                </div>
            </div>

            <div className={"row"}>
                <div className="col-md">
                    {
                        viewAlbumRaiseTier({
                            subscriptionFee,
                            transactionFee,
                            albums,
                            tiers,
                            onFormSubmit,
                            handleAlbumChange,
                            handleAlbumTier
                        })
                    }
                </div>
            </div>
        </div>
    );
};

export default AlbumRaiseTier;