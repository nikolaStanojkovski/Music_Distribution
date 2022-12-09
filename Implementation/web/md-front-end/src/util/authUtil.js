import {ACCESS_TOKEN, LOGGED_ARTIST} from "../constants/auth";

const AuthUtil = {
    isAuthorized() {
        const accessToken = localStorage.getItem(ACCESS_TOKEN);
        const loggedArtist = JSON.parse(localStorage.getItem(LOGGED_ARTIST));

        return (accessToken && loggedArtist);
    }
}

export default AuthUtil;