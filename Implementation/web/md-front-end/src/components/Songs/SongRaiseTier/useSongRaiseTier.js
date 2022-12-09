import {useHistory} from "react-router-dom";
import React from "react";
import {EMPTY_STRING} from "../../../constants/alphabet";
import {PAYMENT_INFO, TIER} from "../../../constants/model";
import {CHECKOUT_SUCCESS} from "../../../constants/endpoint";

const useSongRaiseTier = (props) => {

    const History = useHistory();

    const songs = props.songs;
    const tiers = props.tiers;
    const transactionFee = (props.transactionFee) ? props.transactionFee : undefined;
    const [song, updateSong] = React.useState({});
    const [songTier, updateSongTier] = React.useState(EMPTY_STRING);
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
                if (filteredSongs.length > 0) {
                    updateSong(filteredSongs[0]);
                    handleSongTier(songTier);
                }
            }
        }
    }

    const handleSongTier = (tier) => {
        const paymentInfo = song[PAYMENT_INFO];
        if (song && paymentInfo) {
            const songTier = paymentInfo[TIER];
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
            History.push(CHECKOUT_SUCCESS);
        }
    }

    return {
        songs,
        tiers,
        subscriptionFee,
        transactionFee,
        handleSongTier,
        onFormSubmit,
        handleSongChange
    };
}

export default useSongRaiseTier;