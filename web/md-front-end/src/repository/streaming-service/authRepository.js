import axios from '../../api/apiStreamingService';
import {ARTIST, AUTH, LOGIN, REGISTER} from "../../constants/endpoint";
import {ARTIST_REQUEST, PROFILE_PICTURE} from "../../constants/model";
import {APPLICATION_JSON} from "../../constants/extension";
import {ACCESS_TOKEN, LOGGED_ARTIST} from "../../constants/auth";
import RequestUtil from "../../util/requestUtil";

const AuthRepository = {
    loginArtist: (username, domainName, password) => {
        return axios.post(`${AUTH}${LOGIN}`, RequestUtil.constructLoginRequest(username, domainName, password));
    },
    registerArtist: (profilePicture, username, emailDomain, telephoneNumber, firstName, lastName, artName, password) => {
        const formData = new FormData();
        formData.append(PROFILE_PICTURE, profilePicture);
        const artistRequest = RequestUtil.constructRegistrationRequest(username, emailDomain,
            telephoneNumber, firstName,
            lastName, artName, password);
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