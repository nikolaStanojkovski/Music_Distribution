const AuthUtil = {
    isAuthorized() {
        const accessToken = localStorage.getItem('accessToken');
        const loggedArtist = JSON.parse(localStorage.getItem('loggedArtist'));

        return (accessToken && loggedArtist);
    }
}

export default AuthUtil;