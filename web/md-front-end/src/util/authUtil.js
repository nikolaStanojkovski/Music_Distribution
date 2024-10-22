import {ACCESS_TOKEN, LOGGED_ARTIST} from "../constants/auth";
import jwt from "jsonwebtoken";
import {EMPTY_STRING} from "../constants/alphabet";

const AuthUtil = {
    getToken() {
        const token = localStorage.getItem(ACCESS_TOKEN);
        return !AuthUtil.isExpired(token) ? token : EMPTY_STRING;
    },
    isAuthorized() {
        const accessToken = localStorage.getItem(ACCESS_TOKEN);
        const loggedArtist = JSON.parse(localStorage.getItem(LOGGED_ARTIST));

        return (accessToken && loggedArtist && !this.isExpired(accessToken));
    },
    isExpired(token) {
        let decodedToken = jwt.decode(token, {complete: true});
        let dateNow = new Date();
        return (decodedToken) ? decodedToken.exp < dateNow.getTime() : false;
    }
}

export default AuthUtil;