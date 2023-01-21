import {
    ALBUM_GENRE,
    ALBUM_ID,
    ALBUM_NAME,
    ALBUM_TIER,
    ART_NAME,
    ARTIST_NAME,
    COMPOSER_NAME,
    EMAIL_DOMAIN,
    FIRST_NAME,
    LAST_NAME,
    LENGTH_IN_SECONDS,
    PASSWORD,
    PRODUCER_NAME,
    SONG_GENRE,
    SONG_ID,
    SONG_ID_LIST,
    SONG_NAME,
    SONG_TIER,
    SUBSCRIPTION_FEE,
    TELEPHONE_NUMBER,
    TRANSACTION_FEE,
    USERNAME
} from "../constants/model";
import {ALLOW_ORIGIN_HEADER, AUTH_ROLE, AUTHORIZATION} from "../constants/endpoint";
import {ASTERISK} from "../constants/alphabet";
import AuthUtil from "./authUtil";
import {ARTIST_ROLE} from "../constants/auth";

const RequestUtil = {
    constructRequestHeaders() {
        const headers = {};
        headers[`${ALLOW_ORIGIN_HEADER}`] = ASTERISK;
        headers[`${AUTHORIZATION}`] = `Bearer ${AuthUtil.getToken()}`;
        headers[`${AUTH_ROLE}`] = ARTIST_ROLE;
        return headers;
    },
    constructLoginRequest(username, domainName, password) {
        const loginRequest = {};
        loginRequest[`${USERNAME}`] = username;
        loginRequest[`${EMAIL_DOMAIN}`] = domainName;
        loginRequest[`${PASSWORD}`] = password;
        return loginRequest;
    },
    constructRegistrationRequest(username, emailDomain, telephoneNumber, firstName, lastName, artName, password) {
        const artistRequest = {};
        artistRequest[`${USERNAME}`] = username;
        artistRequest[`${EMAIL_DOMAIN}`] = emailDomain;
        artistRequest[`${TELEPHONE_NUMBER}`] = telephoneNumber;
        artistRequest[`${FIRST_NAME}`] = firstName;
        artistRequest[`${LAST_NAME}`] = lastName;
        artistRequest[`${ART_NAME}`] = artName;
        artistRequest[`${PASSWORD}`] = password;
        return artistRequest;
    },
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
    }
}

export default RequestUtil;