import React from "react";
import {EMPTY_STRING} from "../../../constants/alphabet";
import {ALBUM_TIER, COVER, PICTURE_FILE_LIMIT} from "../../../constants/model";
import {CREATOR_ID} from "../../../constants/endpoint";
import {toast} from "react-toastify";
import {ALBUM_PUBLISHING_FAILED} from "../../../constants/exception";
import PaymentUtil from "../../../util/paymentUtil";
import {
    TRANSACTION_OBJECT_PLACEHOLDER,
    TRANSACTION_TYPE,
    TRANSACTION_TYPE_PLACEHOLDER
} from "../../../constants/transaction";
import FileUtil from "../../../util/fileUtil";
import RequestUtil from "../../../util/requestUtil";

const useAlbumPublish = (props) => {

    const transactionFee = (props.transactionFee) ? props.transactionFee : undefined;
    const genres = props.genres;
    const songs = props.songs;
    const songsTotalLength = props.songsTotalLength;
    const filterSongs = props.filterSongs;
    const tiers = props.tiers;
    const selectedArtist = props.selectedArtist;
    const [cover, updateCover] = React.useState(null);
    const [subscriptionFee, updateSubscriptionFee] = React.useState({});
    const [songIdList, updateSongIdList] = React.useState([]);
    const [formData, updateFormData] = React.useState({
        albumName: EMPTY_STRING,
        albumGenre: EMPTY_STRING,
        albumTier: EMPTY_STRING,
        artistName: EMPTY_STRING,
        producerName: EMPTY_STRING,
        composerName: EMPTY_STRING
    });
    const [fetch, setFetch] = React.useState(false);

    React.useEffect(() => {
        if (!fetch) {
            filterSongs(0, songsTotalLength, CREATOR_ID, selectedArtist.id);
            setFetch(true);
        }
    }, [fetch, filterSongs, songsTotalLength, selectedArtist.id]);

    const handleSubscriptionFee = async (tier) => {
        const subscriptionFee = await PaymentUtil.getSubscriptionFeeWithoutCalculation(props, tier);
        updateSubscriptionFee(subscriptionFee);
    }

    const handleChange = async (e) => {
        updateFormData({
            ...formData,
            [e.target.name]: e.target.value.trim()
        });

        if (e.target.name === ALBUM_TIER) {
            await handleSubscriptionFee(e.target.value);
        }
    }

    const onFormSubmit = async (e) => {
        e.preventDefault();

        const albumName = formData.albumName;
        const albumGenre = formData.albumGenre;
        const albumTier = formData.albumTier;

        if (songIdList && songIdList.length > 0
            && albumName && albumGenre && albumTier
            && subscriptionFee && props.transactionFee
            && FileUtil.validateFile(cover, PICTURE_FILE_LIMIT)) {
            localStorage.setItem(TRANSACTION_TYPE_PLACEHOLDER, TRANSACTION_TYPE.ALBUM_PUBLISH.toString());
            FileUtil.convertAndSaveToString(COVER, cover);
            const albumRequest = RequestUtil.constructAlbumPublishRequest(songIdList
                    .map(song => song.value)
                    .filter(term => term !== undefined),
                albumName, albumGenre, albumTier,
                subscriptionFee, transactionFee,
                formData.artistName, formData.producerName, formData.composerName);
            localStorage.setItem(TRANSACTION_OBJECT_PLACEHOLDER, JSON.stringify(albumRequest));

            const totalAmount = PaymentUtil.getTotalAmount(transactionFee, subscriptionFee);
            props.createPayment(totalAmount);
        } else {
            toast.error(ALBUM_PUBLISHING_FAILED);
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