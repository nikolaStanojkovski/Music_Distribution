import React from "react";
import {EMPTY_STRING} from "../../../constants/alphabet";
import {PAYMENT_INFO, TIER} from "../../../constants/model";
import {CREATOR_ID} from "../../../constants/endpoint";
import {toast} from "react-toastify";
import {
    ALBUM_HIGHER_TIER_WARNING,
    ALBUM_RAISE_TIER_FAILED,
    SUBSCRIPTION_FEE_FETCH_FAILED
} from "../../../constants/exception";
import PaymentUtil from "../../../util/paymentUtil";
import {
    TRANSACTION_OBJECT_PLACEHOLDER,
    TRANSACTION_TYPE,
    TRANSACTION_TYPE_PLACEHOLDER
} from "../../../constants/transaction";
import RequestUtil from "../../../util/requestUtil";

const useAlbumRaiseTier = (props) => {

    const transactionFee = (props.transactionFee) ? props.transactionFee : undefined;
    const albums = props.albums;
    const albumsTotalLength = props.albumsTotalLength;
    const filterAlbums = props.filterAlbums;
    const selectedArtist = props.selectedArtist;
    const tiers = props.tiers;
    const [album, updateAlbum] = React.useState({});
    const [albumTier, updateAlbumTier] = React.useState(EMPTY_STRING);
    const [subscriptionFee, updateSubscriptionFee] = React.useState({});
    const [fetch, setFetch] = React.useState(false);

    React.useEffect(() => {
        if (!fetch) {
            filterAlbums(0, albumsTotalLength, CREATOR_ID, selectedArtist.id);
            setFetch(true);
        }
    }, [fetch, filterAlbums, albumsTotalLength, selectedArtist.id]);

    const handleAlbumChange = async (e) => {
        const albumId = e.target.value;
        if (albumId && props.albums && props.albums.content) {
            const filteredAlbums = props.albums.content.filter(album => album.id === albumId);
            if (filteredAlbums && filteredAlbums.length > 0) {
                updateAlbum(filteredAlbums[0]);
                await handleAlbumTier(albumTier);
            }
        }
    }

    const handleAlbumTier = async (tier) => {
        const paymentInfo = album[PAYMENT_INFO];
        if (paymentInfo) {
            const albumTier = paymentInfo[TIER];
            if (tier && paymentInfo) {
                const subscriptionFee = await PaymentUtil
                    .getSubscriptionFeeWithCalculation(props,
                        tier, albumTier,
                        updateAlbumTier,
                        ALBUM_HIGHER_TIER_WARNING);
                updateSubscriptionFee(subscriptionFee);
            }
        }
    }

    const onFormSubmit = async (e) => {
        e.preventDefault();

        if (subscriptionFee.amount === 0) {
            toast.error(SUBSCRIPTION_FEE_FETCH_FAILED);
            return;
        }

        const albumId = album.id;
        if (albumId && albumTier && subscriptionFee &&
            subscriptionFee.amount > 0 && props.transactionFee) {
            localStorage.setItem(TRANSACTION_TYPE_PLACEHOLDER,
                TRANSACTION_TYPE.ALBUM_RAISE_TIER.toString());
            const albumRequest = RequestUtil.constructAlbumRaiseTierRequest(albumId, albumTier, subscriptionFee, transactionFee);
            localStorage.setItem(TRANSACTION_OBJECT_PLACEHOLDER, JSON.stringify(albumRequest));

            const totalAmount = PaymentUtil.getTotalAmount(transactionFee, subscriptionFee);
            props.createPayment(totalAmount);
        } else {
            toast.error(ALBUM_RAISE_TIER_FAILED);
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