import React from 'react';
import useSongRaiseTier from "./useSongRaiseTier";
import viewSongRaiseTier from "./viewSongRaiseTier";

const SongRaiseTier = (props) => {

    const {
        songs,
        tiers,
        subscriptionFee,
        transactionFee,
        handleSongTier,
        onFormSubmit,
        handleSongChange
    } = useSongRaiseTier(props);

    return (
        <div className="container mm-4 my-5">
            <div className={"row mb-5"}>
                <div className={"col-md"}>
                    <h1 className="display-5">Raise an song's tier</h1>
                    <p className="text-muted">Increase the popularity of the music in your song by raising its
                        tier.</p>
                </div>
            </div>

            <div className={"row"}>
                <div className="col-md">
                    {
                        viewSongRaiseTier({
                            songs,
                            tiers,
                            subscriptionFee,
                            transactionFee,
                            handleSongTier,
                            onFormSubmit,
                            handleSongChange
                        })
                    }
                </div>
            </div>
        </div>
    );
};

export default SongRaiseTier;