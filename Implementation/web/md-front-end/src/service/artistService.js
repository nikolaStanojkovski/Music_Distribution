import React from "react";
import ArtistRepository from "../repository/streaming-service/artistRepository";
import AuthRepository from "../repository/streaming-service/authRepository";

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
                localStorage.setItem('loggedArtist', JSON.stringify(data.data['artistResponse']));
                localStorage.setItem('accessToken', data.data['jwtToken']);
            });

        window.location.reload();
    }

    const registerArtist = (profilePicture, username, emailDomain, telephoneNumber, firstName, lastName, artName, password) => {
        AuthRepository.registerArtist(profilePicture, username, emailDomain, telephoneNumber, firstName, lastName, artName, password)
            .then(() => {
                loadArtists(artists['pageable'].pageNumber);
            });
    }

    const logoutArtist = () => {
        AuthRepository.logoutArtist();
        const baseUrl = window.location.origin;
        window.location.replace(baseUrl);
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