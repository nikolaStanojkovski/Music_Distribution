import React from "react";
import {EMPTY_STRING} from "../../../constants/alphabet";
import ScreenElementsUtil from "../../../util/screenElementsUtil";
import {toast} from "react-toastify";
import {ARTIST_LOGIN_FAILED} from "../../../constants/exception";

const useLogin = (props) => {

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

    const onFormSubmit = async (e) => {
        e.preventDefault();

        const username = formData.username;
        const emailDomain = formData.emailDomain;
        const password = formData.password;

        if (username && password) {
            if (await props.loginArtist(username, emailDomain, password)) {
                ScreenElementsUtil.reloadDomain();
            } else {
                toast.error(ARTIST_LOGIN_FAILED);
            }
        } else {
            toast.error(ARTIST_LOGIN_FAILED);
        }
    }
    return {emailDomains, handleChange, onFormSubmit};
}

export default useLogin;