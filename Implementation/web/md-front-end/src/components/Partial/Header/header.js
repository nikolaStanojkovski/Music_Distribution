import React from 'react';
import AuthHeader from "./AuthHeader/authHeader";
import NonAuthHeader from "./NonAuthHeader/nonAuthHeader";

const Header = (props) => {
    const accessToken = localStorage.getItem('accessToken');
    const loggedArtist = JSON.parse(localStorage.getItem('loggedArtist'));

    return (accessToken && loggedArtist)
        ? <AuthHeader loggedArtist={loggedArtist} logoutArtist={props.logoutArtist}/>
        : <NonAuthHeader/>;
}

export default Header;