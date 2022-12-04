import {useHistory} from "react-router-dom";
import React from "react";

const useAlbumPublish = (props) => {
    const History = useHistory();

    const transactionFee = props.transactionFee;
    const genres = props.genres;
    const songs = props.songs;
    const tiers = props.tiers;
    const selectedArtist = props.selectedArtist;
    const [cover, updateCover] = React.useState(null);
    const [subscriptionFee, updateSubscriptionFee] = React.useState({});
    const [songIdList, updateSongIdList] = React.useState([]);
    const [formData, updateFormData] = React.useState({
        albumName: "",
        albumGenre: "",
        albumTier: "",
        artistName: "",
        producerName: "",
        composerName: ""
    });

    const handleSubscriptionFee = (tier) => {
        props.subscriptionFee(tier).then((data) => {
            updateSubscriptionFee(data.data);
        });
    }

    const handleChange = (e) => {
        updateFormData({
            ...formData,
            [e.target.name]: e.target.value.trim()
        });

        if (e.target.name === "albumTier") {
            handleSubscriptionFee(e.target.value);
        }
    }

    const onFormSubmit = (e) => {
        e.preventDefault();

        const albumName = formData.albumName;
        const albumGenre = formData.albumGenre;
        const albumTier = formData.albumTier;

        if (songIdList && songIdList.length > 0
            && albumName && albumGenre && albumTier
            && subscriptionFee && props.transactionFee) {
            props.publishAlbum(cover, songIdList.map(song => song.value)
                    .filter(term => term !== undefined),
                albumName, albumGenre, albumTier,
                subscriptionFee, props.transactionFee,
                formData.artistName, formData.producerName, formData.composerName);

            History.push("/checkout/success");
        }
    }

    return {
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
    };
}

export default useAlbumPublish;