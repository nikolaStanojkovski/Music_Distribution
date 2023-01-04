import {
    ALBUM_GENRE,
    ALBUM_ID,
    ALBUM_NAME,
    ALBUM_TIER,
    ARTIST_NAME,
    COMPOSER_NAME,
    LENGTH_IN_SECONDS,
    PRODUCER_NAME,
    SONG_GENRE,
    SONG_ID,
    SONG_ID_LIST,
    SONG_NAME,
    SONG_TIER,
    SUBSCRIPTION_FEE,
    TRANSACTION_FEE
} from "../constants/model";

const RequestUtil = {
    constructSongCreateRequest(songName, lengthInSeconds, songGenre) {
        const songRequest = {};
        songRequest[`${SONG_NAME}`] = songName;
        songRequest[`${LENGTH_IN_SECONDS}`] = lengthInSeconds;
        songRequest[`${SONG_GENRE}`] = songGenre;
        return songRequest;
    },
    constructSongRequest(songId, songTier, subscriptionFee, transactionFee) {
        const songRequest = {};
        songRequest[`${SONG_ID}`] = songId;
        songRequest[`${SONG_TIER}`] = songTier;
        songRequest[`${SUBSCRIPTION_FEE}`] = subscriptionFee;
        songRequest[`${TRANSACTION_FEE}`] = transactionFee;
        return songRequest;
    },
    constructAlbumPublishRequest(songIdList,
                                 albumName, albumGenre, albumTier,
                                 subscriptionFee, transactionFee,
                                 artistName, producerName, composerName) {
        const albumRequest = {};
        albumRequest[`${SONG_ID_LIST}`] = songIdList;
        albumRequest[`${ALBUM_NAME}`] = albumName;
        albumRequest[`${ALBUM_GENRE}`] = albumGenre;
        albumRequest[`${ALBUM_TIER}`] = albumTier;
        albumRequest[`${SUBSCRIPTION_FEE}`] = subscriptionFee;
        albumRequest[`${TRANSACTION_FEE}`] = transactionFee;
        albumRequest[`${ARTIST_NAME}`] = artistName;
        albumRequest[`${PRODUCER_NAME}`] = producerName;
        albumRequest[`${COMPOSER_NAME}`] = composerName;
        return albumRequest;
    },
    constructAlbumRaiseTierRequest(albumId, albumTier, subscriptionFee, transactionFee) {
        const albumRequest = {};
        albumRequest[`${ALBUM_ID}`] = albumId;
        albumRequest[`${ALBUM_TIER}`] = albumTier;
        albumRequest[`${SUBSCRIPTION_FEE}`] = subscriptionFee;
        albumRequest[`${TRANSACTION_FEE}`] = transactionFee;
        return albumRequest;
    },
}

export default RequestUtil;