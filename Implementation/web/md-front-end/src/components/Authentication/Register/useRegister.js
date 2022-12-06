import {useHistory} from "react-router-dom";
import React from "react";

const useRegister = (props) => {

    const History = useHistory();

    const emailDomains = props.emailDomains;
    const [profilePicture, updateProfilePicture] = React.useState(null);
    const [formData, updateFormData] = React.useState({
        username: "",
        emailDomain: 0,
        telephoneNumber: "",
        firstName: "",
        lastName: "",
        artName: "",
        password: "",
        repeatPassword: ""
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
            alert('The passwords do not match');
            return;
        }

        props.registerArtist(profilePicture, username, emailDomain, telephoneNumber,
            firstName, lastName, artName, password, repeatPassword);
        History.push("/");
    }

    return {emailDomains, updateProfilePicture, handleChange, onFormSubmit};
}

export default useRegister;