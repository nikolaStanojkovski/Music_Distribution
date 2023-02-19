import React from 'react';
import AuthHeader from "./AuthHeader/authHeader";
import NonAuthHeader from "./NonAuthHeader/nonAuthHeader";
import {ACCESS_TOKEN, LOGGED_ARTIST} from "../../../constants/auth";

const Header = (props) => {
    const accessToken = localStorage.getItem(ACCESS_TOKEN);
    const loggedArtist = JSON.parse(localStorage.getItem(LOGGED_ARTIST));

    return (accessToken && loggedArtist)
        ? <AuthHeader loggedArtist={loggedArtist} logoutArtist={props.logoutArtist}/>
        : <NonAuthHeader/>;
}

export default Header;