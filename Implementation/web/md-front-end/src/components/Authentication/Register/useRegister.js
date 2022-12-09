import {useHistory} from "react-router-dom";
import React from "react";
import {EMPTY_STRING} from "../../../constants/alphabet";
import {DEFAULT} from "../../../constants/endpoint";
import {PASSWORDS_DONT_MATCH} from "../../../constants/exception";

const useRegister = (props) => {

    const History = useHistory();

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

    const onFormSubmit = (e) => {
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
            alert(PASSWORDS_DONT_MATCH);
            return;
        }

        if(username && firstName && lastName && artName && password && repeatPassword) {
            props.registerArtist(profilePicture, username, emailDomain, telephoneNumber,
                firstName, lastName, artName, password, repeatPassword);
            History.push(DEFAULT);
        }
    }

    return {emailDomains, updateProfilePicture, handleChange, onFormSubmit};
}

export default useRegister;