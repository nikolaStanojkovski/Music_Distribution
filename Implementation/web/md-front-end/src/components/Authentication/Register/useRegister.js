import React from "react";
import {EMPTY_STRING} from "../../../constants/alphabet";
import {ARTIST_REGISTER_FAILED, PASSWORD_TOO_SHORT, PASSWORDS_DONT_MATCH} from "../../../constants/exception";
import ScreenElementsUtil from "../../../util/screenElementsUtil";
import {toast} from "react-toastify";

const useRegister = (props) => {

    const emailDomains = props.emailDomains;
    const [profilePicture, updateProfilePicture] = React.useState(null);
    const [formData, updateFormData] = React.useState({
        username: EMPTY_STRING,
        emailDomain: 0,
        telephoneNumber: EMPTY_STRING,
        firstName: EMPTY_STRING,
        lastName: EMPTY_STRING,
        artName: EMPTY_STRING,
        password: EMPTY_STRING,
        repeatPassword: EMPTY_STRING
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
        const telephoneNumber = formData.telephoneNumber;
        const firstName = formData.firstName;
        const lastName = formData.lastName;
        const artName = formData.artName;
        const password = formData.password;
        const repeatPassword = formData.repeatPassword;

        if (password && repeatPassword && password !== repeatPassword) {
            toast.error(PASSWORDS_DONT_MATCH);
            return;
        }

        if(password.length < 6 || repeatPassword.length < 6) {
            toast.error(PASSWORD_TOO_SHORT);
            return;
        }

        if (username && firstName && lastName && artName && password && repeatPassword) {
            if (await props.registerArtist(profilePicture, username, emailDomain, telephoneNumber,
                firstName, lastName, artName, password, repeatPassword)) {
                ScreenElementsUtil.reloadDomain();
            } else {
                toast.error(ARTIST_REGISTER_FAILED);
            }
        } else {
            toast.error(ARTIST_REGISTER_FAILED);
        }
    }

    return {emailDomains, updateProfilePicture, handleChange, onFormSubmit};
}

export default useRegister;