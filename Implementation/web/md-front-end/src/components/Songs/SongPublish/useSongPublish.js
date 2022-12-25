import {useHistory} from "react-router-dom";
import React from "react";
import {EMPTY_STRING} from "../../../constants/alphabet";
import {SONG_TIER} from "../../../constants/model";
import {CHECKOUT, CREATOR_ID} from "../../../constants/endpoint";
import {toast} from "react-toastify";
import {SONG_PUBLISHING_FAILED} from "../../../constants/exception";
import PaymentUtil from "../../../util/paymentUtil";

const useSongPublish = (props) => {

    const history = useHistory();

    const songs = props.songs;
    const songsTotalLength = props.songsTotalLength;
    const filterSongs = props.filterSongs;
    const tiers = props.tiers;
    const transactionFee = (props.transactionFee) ? props.transactionFee : undefined;
    const selectedArtist = props.selectedArtist;
    const [cover, updateCover] = React.useState(null);
    const [subscriptionFee, updateSubscriptionFee] = React.useState(EMPTY_STRING);
    const [formData, updateFormData] = React.useState({
        songId: EMPTY_STRING,
        songTier: EMPTY_STRING,
    });
    const [fetch, setFetch] = React.useState(false);

    React.useEffect(() => {
        if(!fetch) {
            filterSongs(0, songsTotalLength, CREATOR_ID, selectedArtist.id);
            setFetch(true);
        }
    }, [fetch, filterSongs, songsTotalLength, selectedArtist.id]);

    const handleSubscriptionFee = async (tier) => {
        if (tier && tier !== EMPTY_STRING) {
            const calculatedFee = await PaymentUtil.getSubscriptionFeeWithoutCalculation(props, tier);
            updateSubscriptionFee(calculatedFee);
        }
    }

    const handleChange = async (e) => {
        updateFormData({
            ...formData,
            [e.target.name]: e.target.value.trim()
        });

        if (e.target.name === SONG_TIER) {
            await handleSubscriptionFee(e.target.value);
        }
    }

    const onFormSubmit = async (e) => {
        e.preventDefault();

        const songId = formData.songId;
        const songTier = formData.songTier;

        if (songId && songTier && subscriptionFee && props.transactionFee) {
            const result = await props.publishSong(cover, songId,
                songTier, subscriptionFee,
                props.transactionFee);
            history.push({
                pathname: CHECKOUT,
                state: {result: result}
            });
        } else {
            toast.error(SONG_PUBLISHING_FAILED);
        }
    }

    return {
        songs,
        tiers,
        selectedArtist,
        subscriptionFee,
        transactionFee,
        updateCover,
        handleChange,
        onFormSubmit
    };
}

export default useSongPublish;