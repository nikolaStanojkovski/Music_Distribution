import axios from '../../custom-axios/axiosStreamingService';

const AuthRepository = {
    loginArtist: (username, domainName, password) => {
        return axios.post("/auth/login", {
            "username": username,
            "emailDomain": domainName,
            "password": password
        });
    },
    registerArtist: (profilePicture, username, emailDomain, telephoneNumber, firstName, lastName, artName, password) => {
        const formData = new FormData();
        formData.append('profilePicture', profilePicture);
        formData.append('artistRequest', new Blob([JSON.stringify({
            "username": username,
            "emailDomain": emailDomain,
            "telephoneNumber": telephoneNumber,
            "firstName": firstName,
            "lastName": lastName,
            "artName": artName,
            "password": password
        })], {
            type: "application/json"
        }));
        return axios.post("/auth/register", formData);
    },
    logoutArtist: () => {
        if (localStorage.getItem('accessToken')) {
            localStorage.removeItem('accessToken');
            localStorage.removeItem('loggedArtist');
        }
    },
}

export default AuthRepository;