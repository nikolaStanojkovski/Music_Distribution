import {useHistory} from "react-router-dom";
import React from "react";
import {EMPTY_STRING} from "../../../constants/alphabet";
import {DEFAULT} from "../../../constants/endpoint";

const useLogin = (props) => {

    const History = useHistory();

    const emailDomains = props.emailDomains;
    const [formData, updateFormData] = React.useState({
        username: EMPTY_STRING,
        emailDomain: 0,
        password: EMPTY_STRING,
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

        if(username && password) {
            props.loginArtist(username, emailDomain, password);
            History.push(DEFAULT);
        }
    }
    return {emailDomains, handleChange, onFormSubmit};
}

export default useLogin;