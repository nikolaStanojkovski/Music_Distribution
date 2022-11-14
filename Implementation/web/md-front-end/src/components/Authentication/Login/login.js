import React from "react";
import {Link, useHistory} from "react-router-dom";

const Login = (props) => {

    const History = useHistory();
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
                    <form onSubmit={onFormSubmit}>
                        <div className="form-group">
                            <div className={"row"}>
                                <div className="col-12 form-inline">
                                    <div className="input-group">
                                        <input type="text"
                                               className="form-control"
                                               id="username"
                                               name="username"
                                               required
                                               placeholder="Username"
                                               onChange={handleChange}/>
                                        <div className="input-group-prepend">
                                            <span className="input-group-text">@</span>
                                        </div>
                                        <select onChange={handleChange} name="emailDomain" className="form-control">
                                            {(props.emailDomains) ? props.emailDomains.map((term) =>
                                                <option key={term} value={term}>{term}</option>
                                            ) : undefined}
                                        </select>
                                        <div className="input-group-prepend">
                                            <span className="input-group-text">.com</span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <br/>

                        <div className="form-group">
                            <div className={"row"}>
                                <div className={"col-12"}>
                                    <input type="password"
                                           className="form-control"
                                           id="password"
                                           name="password"
                                           required
                                           placeholder="Enter the password"
                                           onChange={handleChange}/>
                                </div>
                            </div>
                        </div>
                        <br/>

                        <button id="submit" type="submit" className="btn btn-dark w-100 mt-3">Sign In</button>

                        <br/>
                    </form>

                    <Link to={"/register"} className="btn btn-outline-dark w-100 mt-3">Sign Up</Link>
                </div>
                <div className={"col-md-2"}/>
            </div>
        </div>
    );
};

export default Login;