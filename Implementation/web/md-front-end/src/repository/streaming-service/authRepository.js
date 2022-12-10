import axios from '../../custom-axios/axiosStreamingService';
import {ARTIST, AUTH, LOGIN, REGISTER} from "../../constants/endpoint";
import {
    ART_NAME,
    ARTIST_REQUEST,
    EMAIL_DOMAIN,
    FIRST_NAME,
    LAST_NAME,
    PASSWORD,
    PROFILE_PICTURE,
    TELEPHONE_NUMBER,
    USERNAME
} from "../../constants/model";
import {APPLICATION_JSON} from "../../constants/extension";
import {ACCESS_TOKEN, LOGGED_ARTIST} from "../../constants/auth";

const AuthRepository = {
    loginArtist: (username, domainName, password) => {
        const loginRequest = {};
        loginRequest[`${USERNAME}`] = username;
        loginRequest[`${EMAIL_DOMAIN}`] = domainName;
        loginRequest[`${PASSWORD}`] = password;
        return axios.post(`${AUTH}${LOGIN}`, loginRequest);
    },
    registerArtist: (profilePicture, username, emailDomain, telephoneNumber, firstName, lastName, artName, password) => {
        const formData = new FormData();
        formData.append(PROFILE_PICTURE, profilePicture);
        const artistRequest = {};
        artistRequest[`${USERNAME}`] = username;
        artistRequest[`${EMAIL_DOMAIN}`] = emailDomain;
        artistRequest[`${TELEPHONE_NUMBER}`] = telephoneNumber;
        artistRequest[`${FIRST_NAME}`] = firstName;
        artistRequest[`${LAST_NAME}`] = lastName;
        artistRequest[`${ART_NAME}`] = artName;
        artistRequest[`${PASSWORD}`] = password;
        formData.append(ARTIST_REQUEST, new Blob([JSON.stringify(artistRequest)], {
            type: APPLICATION_JSON
        }));
        return axios.post(`${AUTH}${REGISTER}${ARTIST}`, formData);
    },
    logoutArtist: () => {
        if (localStorage.getItem(ACCESS_TOKEN)) {
            localStorage.removeItem(ACCESS_TOKEN);
            localStorage.removeItem(LOGGED_ARTIST);
            return true;
        }
        return false;
    },
}

export default AuthRepository;