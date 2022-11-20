import React from 'react';
import {useHistory} from 'react-router-dom';
import StringUtil from "../../../util/stringUtil";

const SongRaiseTier = (props) => {

    const History = useHistory();
    const [song, updateSong] = React.useState({});
    const [songTier, updateSongTier] = React.useState("");
    const [subscriptionFee, updateSubscriptionFee] = React.useState({});

    const handleCalculatedFee = (existingSubscriptionFee, currentSubscriptionFee,
                                 tier, songTier) => {
        if (existingSubscriptionFee.amount > currentSubscriptionFee.amount) {
            updateSongTier(songTier);
            return {amount: 0.0, currency: existingSubscriptionFee.currency};
        } else {
            updateSongTier(tier);
            return {
                amount: currentSubscriptionFee.amount - existingSubscriptionFee.amount,
                currency: existingSubscriptionFee.currency
            };
        }
    }

    const handleSubscriptionFee = (tier, songTier) => {
        props.subscriptionFee(tier).then((data) => {
            const currentSubscriptionFee = data.data;
            props.subscriptionFee(songTier).then((data) => {
                const existingSubscriptionFee = data.data;

                const calculatedFee = handleCalculatedFee(existingSubscriptionFee,
                    currentSubscriptionFee, tier, songTier);
                updateSubscriptionFee(calculatedFee);
            });
        });
    }

    const handleSongChange = (e) => {
        const songId = e.target.value;
        if (songId) {
            const filteredSongs = props.songs.content.filter(song => song.id === songId);
            if (filteredSongs) {
                if(filteredSongs.length > 0) {
                    updateSong(filteredSongs[0]);
                    handleSongTier(songTier);
                }
            }
        }
    }

    const handleSongTier = (tier) => {
        const paymentInfo = song['paymentInfo'];
        if (paymentInfo) {
            const songTier = paymentInfo['tier'];
            if (tier && paymentInfo) {
                handleSubscriptionFee(tier, songTier);
            }
        }
    }

    const onFormSubmit = (e) => {
        e.preventDefault();

        const songId = song.id;
        if (songId && songTier && subscriptionFee &&
            subscriptionFee.amount > 0 && props.transactionFee) {
            props.raiseTierSong(songId, songTier, subscriptionFee, props.transactionFee);
            History.push("/checkout/success");
        }
    }

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
                    <form onSubmit={onFormSubmit}>
                        <div className="form-group">
                            <select onChange={handleSongChange}
                                    name="song" className="form-control">
                                <option className={"text-muted"} value={null} disabled={true} selected={true}>
                                    -- Choose song --
                                </option>
                                {(props.songs && props.songs.content) ? props.songs.content.map((term) => {
                                        return (term['isPublished'] && term['isASingle'])
                                            ? <option key={term.id} value={term.id}>{term.songName}</option>
                                            : undefined;
                                    }
                                ) : undefined}
                            </select>
                        </div>
                        <br/>

                        <div className="form-group">
                            <select onChange={(e) => handleSongTier(e.target.value)}
                                    name="songTier" className="form-control" required={true}>
                                <option className={"text-muted"} value={null} disabled={true} selected={true}>
                                    -- Choose tier --
                                </option>
                                {(props.tiers) ? props.tiers.map((term) => {
                                        return <option key={term} value={term}>{term}</option>;
                                    }
                                ) : undefined}
                            </select>
                        </div>
                        <br/>

                        <div className="form-group">
                            <span>
                                <input name="subscriptionFee" disabled={true}
                                       id="subscriptionFee"
                                       value={StringUtil.formatCurrency(subscriptionFee.amount,
                                           subscriptionFee.currency)}
                                       className="form-control disabled"/>&nbsp;
                            </span>
                            <span className={"text-muted"}>Subscription fee is based on the tier our platform offers for distribution</span>
                        </div>
                        <br/>

                        <div className="form-group">
                            <input name="transactionFee" disabled={true}
                                   id={"transactionFee"}
                                   value={StringUtil.formatCurrency(props.transactionFee.amount,
                                       props.transactionFee.currency)}
                                   className="form-control disabled"/>
                            <span className={"text-muted"}>Transaction fee is fixed and based on your location and country</span>
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

export default SongRaiseTier;