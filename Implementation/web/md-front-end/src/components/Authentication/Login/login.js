import React from "react";
import {Link} from "react-router-dom";
import useLogin from "./useLogin";
import viewLogin from "./viewLogin";

const Login = (props) => {

    const {emailDomains, handleChange, onFormSubmit} = useLogin(props);

    return (
        <div className="container mm-4 my-5">
            <div className={"row"}>
                <div className={"col-md-2"}/>
                <div className="col-md-8 text-center p-5 rounded-4 auth-form border-bottom">
                    <h1 className="display-5">Sign In</h1>
                    <p className="text-muted">Use the account you made to access it and begin promoting your music.</p>
                    <br/>
                    <br/>
                    <br/>

                    {viewLogin({emailDomains, handleChange, onFormSubmit})}

                    <Link to={"/register"} className="btn btn-outline-dark w-100 mt-3">Sign Up</Link>
                </div>
                <div className={"col-md-2"}/>
            </div>
        </div>
    );
};

export default Login;