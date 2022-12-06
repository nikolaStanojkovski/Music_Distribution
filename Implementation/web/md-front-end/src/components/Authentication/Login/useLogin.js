import {useHistory} from "react-router-dom";
import React from "react";

const useLogin  = (props) => {

    const History = useHistory();

    const emailDomains = props.emailDomains;
    const [formData, updateFormData] = React.useState({
        username: "",
        emailDomain: 0,
        password: "",
    });

    const handleChange = (e) => {
        updateFormData({
            ...formData,
            [e.target.name]: e.target.value.trim()
        })
    }

    const onFormSubmit = (e) => {
        e.preventDefault();

        const username = formData.username;
        const emailDomain = formData.emailDomain;
        const password = formData.password;

        props.loginArtist(username, emailDomain, password);
        History.push("/");
    }
    return {emailDomains, handleChange, onFormSubmit};
}

export default useLogin;