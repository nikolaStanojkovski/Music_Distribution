import {useHistory} from "react-router-dom";
import React from "react";

const useSongPublish = (props) => {

    const History = useHistory();

    const songs = props.songs;
    const tiers = props.tiers;
    const transactionFee = props.transactionFee;
    const selectedArtist = props.selectedArtist;
    const [cover, updateCover] = React.useState(null);
    const [subscriptionFee, updateSubscriptionFee] = React.useState("");
    const [formData, updateFormData] = React.useState({
        songId: "",
        songTier: "",
    });

    const handleSubscriptionFee = (tier) => {
        if (tier && tier !== "") {
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

        if (e.target.name === "songTier") {
            handleSubscriptionFee(e.target.value);
        }
    }

    const onFormSubmit = (e) => {
        e.preventDefault();
        const songId = formData.songId;
        const songTier = formData.songTier;

        if (songId && songTier && subscriptionFee && props.transactionFee) {
            props.publishSong(cover, songId, songTier, subscriptionFee, props.transactionFee);

            History.push("/checkout/success");
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