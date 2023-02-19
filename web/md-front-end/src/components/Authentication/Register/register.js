import React from 'react';
import {Link} from 'react-router-dom';
import useRegister from "./useRegister";
import viewRegister from "./viewRegister";
import {LOGIN} from "../../../constants/endpoint";

const Register = (props) => {

    const {
        emailDomains,
        updateProfilePicture,
        handleChange,
        onFormSubmit
    } = useRegister(props);

    return (
        <div className="container mm-4 my-5">
            <div className={"row"}>
                <div className={"col-md-2"}/>
                <div className="col-md-8 text-center p-5 rounded-4 auth-form border-bottom">
                    <h1 className="display-5">Sign Up</h1>
                    <p className="text-muted">Join us and become a part of the community.</p>
                    <br/>
                    <br/>
                    <br/>
                    {
                        viewRegister(
                            {
                                emailDomains,
                                updateProfilePicture,
                                handleChange,
                                onFormSubmit
                            }
                        )
                    }
                    <Link to={LOGIN} className="btn btn-outline-dark w-100 mt-3">Sign In</Link>
                </div>
                <div className={"col-md-2"}/>
            </div>
        </div>
    );
};

export default Register;