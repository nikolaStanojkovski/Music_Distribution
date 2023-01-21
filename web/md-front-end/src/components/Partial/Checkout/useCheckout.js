import {useHistory} from "react-router-dom";
import React, {useEffect} from "react";
import {EMPTY_STRING} from "../../../constants/alphabet";
import {DEFAULT} from "../../../constants/endpoint";
import {
    TOKEN_PLACEHOLDER,
    TRANSACTION_OBJECT_PLACEHOLDER,
    TRANSACTION_TYPE,
    TRANSACTION_TYPE_PLACEHOLDER
} from "../../../constants/transaction";
import {TRANSACTION_FAILURE, TRANSACTION_SUCCESSFUL} from "../../../constants/exception";
import AlbumRepository from "../../../repository/streaming-service/albumRepository";
import SongRepository from "../../../repository/streaming-service/songRepository";
import {COVER} from "../../../constants/model";
import FileUtil from "../../../util/fileUtil";
import {toast} from "react-toastify";

const useCheckout = () => {

    const history = useHistory();

    const [message, setMessage] = React.useState(EMPTY_STRING);
    const [displayCheckmark, setDisplayCheckmark] = React.useState(false);
    const [resultWatermark, setResultWatermark] = React.useState(false);

    const isCheckoutValid = () => {
        const token = new URLSearchParams(window.location.search).get(TOKEN_PLACEHOLDER);
        return token && token.length > 0;
    }

    const getResult = () => {
        const transactionType = localStorage.getItem(TRANSACTION_TYPE_PLACEHOLDER);
        const transactionObject = localStorage.getItem(TRANSACTION_OBJECT_PLACEHOLDER);
        const tokenId = new URLSearchParams(window.location.search).get(TOKEN_PLACEHOLDER);

        if (transactionType && transactionObject) {
            switch (transactionType) {
                case TRANSACTION_TYPE.SONG_PUBLISH:
                    const songCover = FileUtil.convertToBlob(localStorage.getItem(COVER));
                    if (!songCover) {
                        toast.error(TRANSACTION_FAILURE);
                        return false;
                    }
                    return SongRepository.publishSong(songCover, transactionObject, tokenId);
                case TRANSACTION_TYPE.SONG_RAISE_TIER:
                    return SongRepository.raiseTierSong(JSON.parse(transactionObject), tokenId);
                case TRANSACTION_TYPE.ALBUM_PUBLISH:
                    const albumCover = FileUtil.convertToBlob(localStorage.getItem(COVER));
                    if (!albumCover) {
                        toast.error(TRANSACTION_FAILURE);
                        return false;
                    }
                    return AlbumRepository.publishAlbum(albumCover, transactionObject, tokenId);
                case TRANSACTION_TYPE.ALBUM_RAISE_TIER:
                    return AlbumRepository.raiseTierAlbum(JSON.parse(transactionObject), tokenId);
                default:
                    return undefined;
            }
        }
        return undefined;
    }

    useEffect(() => {
        if (isCheckoutValid()) {
            getResult().then(result => {
                setMessage((result)
                    ? TRANSACTION_SUCCESSFUL
                    : TRANSACTION_FAILURE);
                setDisplayCheckmark(true);
                setResultWatermark(result !== false);
            }).catch(() => {
                setMessage(TRANSACTION_FAILURE);
                setDisplayCheckmark(true);
                setResultWatermark(false);
            }).finally(() => {
                clearPaymentStorage();
            })
        } else {
            history.push(DEFAULT);
        }
    }, [history]);

    const clearPaymentStorage = () => {
        localStorage.removeItem(TRANSACTION_TYPE_PLACEHOLDER);
        localStorage.removeItem(TRANSACTION_OBJECT_PLACEHOLDER);
        if (localStorage.getItem(COVER)) {
            localStorage.removeItem(COVER);
        }
    }

    return {message, displayCheckmark, resultWatermark};
}

export default useCheckout;