import {useHistory} from "react-router-dom";
import React from "react";
import {EMPTY_STRING} from "../../../constants/alphabet";
import {PAYMENT_INFO, TIER} from "../../../constants/model";
import {CHECKOUT} from "../../../constants/endpoint";
import {toast} from "react-toastify";
import {SONG_HIGHER_TIER_WARNING, SONG_RAISE_TIER_FAILED} from "../../../constants/exception";
import PaymentUtil from "../../../util/paymentUtil";

const useSongRaiseTier = (props) => {

    const history = useHistory();

    const songs = props.songs;
    const tiers = props.tiers;
    const transactionFee = (props.transactionFee) ? props.transactionFee : undefined;
    const [song, updateSong] = React.useState({});
    const [songTier, updateSongTier] = React.useState(EMPTY_STRING);
    const [subscriptionFee, updateSubscriptionFee] = React.useState({});


    const handleSongChange = async (e) => {
        const songId = e.target.value;
        if (songId) {
            const filteredSongs = props.songs.content.filter(song => song.id === songId);
            if (filteredSongs) {
                if (filteredSongs.length > 0) {
                    updateSong(filteredSongs[0]);
                    await handleSongTier(songTier);
                }
            }
        }
    }

    const handleSongTier = async (tier) => {
        const paymentInfo = song[PAYMENT_INFO];
        if (song && paymentInfo) {
            const songTier = paymentInfo[TIER];
            if (tier && paymentInfo) {
                const subscriptionFee = await PaymentUtil
                    .getSubscriptionFeeWithCalculation(props,
                        tier, songTier,
                        updateSongTier,
                        SONG_HIGHER_TIER_WARNING);
                updateSubscriptionFee(subscriptionFee);
            }
        }
    }

    const onFormSubmit = async (e) => {
        e.preventDefault();

        if (subscriptionFee.amount === 0) {
            toast.error(SONG_HIGHER_TIER_WARNING);
        }

        const songId = song.id;
        if (songId && songTier && subscriptionFee &&
            subscriptionFee.amount > 0 && props.transactionFee) {
            const result = await props.raiseTierSong(songId, songTier,
                subscriptionFee, props.transactionFee);
            history.push({
                pathname: CHECKOUT,
                state: {result: result}
            });
        } else {
            toast.error(SONG_RAISE_TIER_FAILED);
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