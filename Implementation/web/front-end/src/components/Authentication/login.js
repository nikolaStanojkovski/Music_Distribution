import React from "react";

const Login = ({emailDomains, handleChange, onFormSubmit}) => {
    return (
        <div className="container mm-4 my-5">
            <div className={"row mb-5"}>
                <h1 className="display-5">Sign In</h1>
                <p className="text-muted">Use the account you made to access it and begin promoting your music.</p>
            </div>
            <div className={"row"}>
                <div className="col-md-6">
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
                                            {emailDomains.map((term) =>
                                                <option key={term} value={term}>{term}</option>
                                            )}
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

                        <button id="submit" type="submit" className="btn btn-dark mt-4">Submit</button>

                        <br/>
                    </form>
                </div>
            </div>
        </div>
    );
};

export default Login;