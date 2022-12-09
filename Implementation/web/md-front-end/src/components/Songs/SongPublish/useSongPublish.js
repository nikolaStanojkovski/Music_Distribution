import {useHistory} from "react-router-dom";
import React from "react";
import {EMPTY_STRING} from "../../../constants/alphabet";
import {SONG_TIER} from "../../../constants/model";
import {CHECKOUT_SUCCESS} from "../../../constants/endpoint";

const useSongPublish = (props) => {

    const History = useHistory();

    const songs = props.songs;
    const tiers = props.tiers;
    const transactionFee = (props.transactionFee) ? props.transactionFee : undefined;
    const selectedArtist = props.selectedArtist;
    const [cover, updateCover] = React.useState(null);
    const [subscriptionFee, updateSubscriptionFee] = React.useState(EMPTY_STRING);
    const [formData, updateFormData] = React.useState({
        songId: EMPTY_STRING,
        songTier: EMPTY_STRING,
    });

    const handleSubscriptionFee = (tier) => {
        if (tier && tier !== EMPTY_STRING) {
            props.subscriptionFee(tier).then((data) => {
                updateSubscriptionFee(data.data);
            })
        }
    }

    const handleChange = (e) => {
        updateFormData({
            ...formData,
            [e.target.name]: e.target.value.trim()
        });

        if (e.target.name === SONG_TIER) {
            handleSubscriptionFee(e.target.value);
        }
    }

    const onFormSubmit = (e) => {
        e.preventDefault();

        const songId = formData.songId;
        const songTier = formData.songTier;

        if (songId && songTier && subscriptionFee && props.transactionFee) {
            props.publishSong(cover, songId, songTier, subscriptionFee, props.transactionFee);

            History.push(CHECKOUT_SUCCESS);
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