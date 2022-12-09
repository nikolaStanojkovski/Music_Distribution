import React from "react";
import ArtistRepository from "../../repository/streaming-service/artistRepository";
import AuthRepository from "../../repository/streaming-service/authRepository";
import ScreenElementsUtil from "../../util/screenElementsUtil";
import {ACCESS_TOKEN, JWT_TOKEN, LOGGED_ARTIST} from "../../constants/auth";
import {ARTIST_RESPONSE, PAGEABLE} from "../../constants/model";

const useArtistService = () => {

    const [artists, setArtists] = React.useState([]);
    React.useEffect(() => {
        loadArtists(0);
    }, []);

    const loadArtists = (pageNumber) => {
        ArtistRepository.fetchArtists(pageNumber)
            .then((data) => {
                setArtists(data.data);
            });
    }

    const loginArtist = async (username, domainName, password) => {
        await AuthRepository.loginArtist(username, domainName, password)
            .then((data) => {
                localStorage.setItem(LOGGED_ARTIST, JSON.stringify(data.data[ARTIST_RESPONSE]));
                localStorage.setItem(ACCESS_TOKEN, data.data[JWT_TOKEN]);
            });

        window.location.reload();
    }

    const registerArtist = (profilePicture, username, emailDomain, telephoneNumber, firstName, lastName, artName, password) => {
        AuthRepository.registerArtist(profilePicture, username, emailDomain, telephoneNumber, firstName, lastName, artName, password)
            .then(() => {
                loadArtists(artists[PAGEABLE].pageNumber);
            });
    }

    const logoutArtist = () => {
        AuthRepository.logoutArtist();
        ScreenElementsUtil.reloadDomain();
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