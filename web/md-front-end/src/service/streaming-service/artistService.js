import React from "react";
import ArtistRepository from "../../repository/streaming-service/artistRepository";
import AuthRepository from "../../repository/streaming-service/authRepository";
import ScreenElementsUtil from "../../util/screenElementsUtil";
import {ACCESS_TOKEN, JWT_TOKEN, LOGGED_ARTIST} from "../../constants/auth";
import {ARTIST_RESPONSE, PAGEABLE} from "../../constants/model";
import {toast} from "react-toastify";
import {
    ARTIST_FETCH_FAILED,
    ARTIST_LOGIN_FAILED,
    ARTIST_LOGOUT_FAILED,
    ARTIST_REGISTER_FAILED
} from "../../constants/exception";

const useArtistService = () => {

    const [artists, setArtists] = React.useState([]);
    React.useEffect(() => {
        loadArtists(0);
    }, []);

    const loadArtists = (pageNumber) => {
        ArtistRepository.fetchArtists(pageNumber)
            .then((data) => {
                setArtists(data.data);
            }).catch(() => {
            toast.error(ARTIST_FETCH_FAILED);
        });
    }

    const loginArtist = async (username, domainName, password) => {
        return AuthRepository.loginArtist(username, domainName, password)
            .then((data) => {
                localStorage.setItem(LOGGED_ARTIST, JSON.stringify(data.data[ARTIST_RESPONSE]));
                localStorage.setItem(ACCESS_TOKEN, data.data[JWT_TOKEN]);
                return true;
            }).catch(() => {
                toast.error(ARTIST_LOGIN_FAILED);
                return false;
            });
    }

    const registerArtist = (profilePicture, username, emailDomain, telephoneNumber, firstName, lastName, artName, password) => {
        return AuthRepository.registerArtist(profilePicture, username, emailDomain, telephoneNumber, firstName, lastName, artName, password)
            .then(() => {
                loadArtists(artists[PAGEABLE].pageNumber);
                return true;
            }).catch(() => {
                toast.error(ARTIST_REGISTER_FAILED);
                return false;
            });
    }

    const logoutArtist = () => {
        if(AuthRepository.logoutArtist()) {
            ScreenElementsUtil.reloadDomain();
        } else {
            toast.error(ARTIST_LOGOUT_FAILED);
        }
    }

    return {
        artists,
        loadArtists,
        loginArtist,
        registerArtist,
        logoutArtist
    };
}

export default useArtistService;