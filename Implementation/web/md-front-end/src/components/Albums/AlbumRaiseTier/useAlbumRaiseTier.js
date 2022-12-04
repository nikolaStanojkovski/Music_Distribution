import {useHistory} from "react-router-dom";
import React from "react";

const useAlbumRaiseTier = (props) => {

    const History = useHistory();

    const transactionFee = props.transactionFee;
    const albums = props.albums;
    const tiers = props.tiers;
    const [album, updateAlbum] = React.useState({});
    const [albumTier, updateAlbumTier] = React.useState("");
    const [subscriptionFee, updateSubscriptionFee] = React.useState({});

    const handleCalculatedFee = (existingSubscriptionFee, currentSubscriptionFee,
                                 tier, albumTier) => {
        if (existingSubscriptionFee.amount > currentSubscriptionFee.amount) {
            updateAlbumTier(albumTier);
            return {amount: 0.0, currency: existingSubscriptionFee.currency};
        } else {
            updateAlbumTier(tier);
            return {
                amount: currentSubscriptionFee.amount - existingSubscriptionFee.amount,
                currency: existingSubscriptionFee.currency
            };
        }
    }

    const handleSubscriptionFee = (tier, albumTier) => {
        props.subscriptionFee(tier).then((data) => {
            const currentSubscriptionFee = data.data;
            props.subscriptionFee(albumTier).then((data) => {
                const existingSubscriptionFee = data.data;

                const calculatedFee = handleCalculatedFee(existingSubscriptionFee,
                    currentSubscriptionFee, tier, albumTier);
                updateSubscriptionFee(calculatedFee);
            });
        });
    }

    const handleAlbumChange = (e) => {
        const albumId = e.target.value;
        if (albumId) {
            const filteredAlbums = props.albums.content.filter(album => album.id === albumId);
            if (filteredAlbums) {
                if (filteredAlbums.content.length > 0) {
                    updateAlbum(filteredAlbums[0]);
                    handleAlbumTier(albumTier);
                }
            }
        }
    }

    const handleAlbumTier = (tier) => {
        const paymentInfo = album['paymentInfo'];
        if (paymentInfo) {
            const albumTier = paymentInfo['tier'];
            if (tier && paymentInfo) {
                handleSubscriptionFee(tier, albumTier);
            }
        }
    }

    const onFormSubmit = (e) => {
        e.preventDefault();

        const albumId = album.id;
        if (albumId && albumTier && subscriptionFee &&
            subscriptionFee.amount > 0 && props.transactionFee) {
            props.raiseTierAlbum(albumId, albumTier, subscriptionFee, props.transactionFee);
            History.push("/checkout/success");
        }
    }

    return {
        subscriptionFee,
        transactionFee,
        albums,
        tiers,
        onFormSubmit,
        handleAlbumChange,
        handleAlbumTier
    };
}

export default useAlbumRaiseTier;