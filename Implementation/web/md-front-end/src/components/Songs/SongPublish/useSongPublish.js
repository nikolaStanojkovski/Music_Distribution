import React from "react";
import {EMPTY_STRING} from "../../../constants/alphabet";
import {COVER, SONG_TIER} from "../../../constants/model";
import {CREATOR_ID} from "../../../constants/endpoint";
import {toast} from "react-toastify";
import {SONG_PUBLISHING_FAILED} from "../../../constants/exception";
import PaymentUtil from "../../../util/paymentUtil";
import {
    TRANSACTION_OBJECT_PLACEHOLDER,
    TRANSACTION_TYPE,
    TRANSACTION_TYPE_PLACEHOLDER
} from "../../../constants/transaction";
import FileUtil from "../../../util/fileUtil";
import RequestUtil from "../../../util/requestUtil";

const useSongPublish = (props) => {

    const songs = props.songs;
    const songsTotalLength = props.songsTotalLength;
    const filterSongs = props.filterSongs;
    const selectedArtist = props.selectedArtist;
    const tiers = props.tiers;
    const transactionFee = (props.transactionFee) ? props.transactionFee : undefined;
    const [cover, updateCover] = React.useState(null);
    const [subscriptionFee, updateSubscriptionFee] = React.useState(EMPTY_STRING);
    const [formData, updateFormData] = React.useState({
        songId: EMPTY_STRING,
        songTier: EMPTY_STRING,
    });
    const [fetch, setFetch] = React.useState(false);

    React.useEffect(() => {
        if (!fetch) {
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

        if (songId && songTier && subscriptionFee && transactionFee) {
            localStorage.setItem(TRANSACTION_TYPE_PLACEHOLDER, TRANSACTION_TYPE.SONG_PUBLISH.toString());
            FileUtil.convertAndSaveToString(COVER, cover);
            const songRequest = RequestUtil.constructSongRequest(songId, songTier, subscriptionFee, transactionFee);
            localStorage.setItem(TRANSACTION_OBJECT_PLACEHOLDER, JSON.stringify(songRequest));

            const totalAmount = PaymentUtil.getTotalAmount(transactionFee, subscriptionFee);
            props.createPayment(totalAmount);
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