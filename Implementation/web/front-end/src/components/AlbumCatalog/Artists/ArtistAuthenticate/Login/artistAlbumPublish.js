import React from 'react';
import {useHistory} from 'react-router-dom';
import ArtistLogin from "../artistLogin";

const LoginArtistPublishAlbum = (props) => {

    const History = useHistory();
    const [formData, updateFormData] = React.useState({
        username: "",
        domainName: 0,
        password: ""
    });

    const handleChange = (e) => {
        updateFormData({
            ...formData,
            [e.target.name]: e.target.value.trim()
        })
    }

    const onFormSubmit = async (e) => {
        e.preventDefault();
        const username = formData.username;
        const domainName = formData.domainName;
        const password = formData.password;

        let isLogged = await props.loginArtistPublish(username, domainName, password);

        isLogged ? History.push("/albums/publish") : History.push("/albums");
    }

    return (
        <ArtistLogin emailDomains={props.emailDomains} onFormSubmit={onFormSubmit} handleChange={handleChange} />
    );
};

export default LoginArtistPublishAlbum;