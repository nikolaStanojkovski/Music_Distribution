import React from "react";
import {Link} from "react-router-dom";

const ArtistLogin = ({ emailDomains, handleChange, onFormSubmit }) => {
    return (
        <div className="container">
            <h1 className={"text-center mt-4"}>Prove us that you are artist</h1>
            <br/>
            <div className={""}>
                <div className="col-md-6">
                    <br/>
                    <form onSubmit={onFormSubmit}>
                        <div className="form-group">
                            <label htmlFor="name">Email</label>
                            <div className={"row"}>
                                <div className="col-5">
                                    <input type="text"
                                           className="form-control"
                                           id="username"
                                           name="username"
                                           required
                                           placeholder="Username"
                                           onChange={handleChange}/>
                                </div>
                                <div className={"col-1"}>@</div>
                                <div className={"col-5"}>
                                    <select onChange={handleChange} name="domainName" className="form-control">
                                        {emailDomains.map((term) =>
                                            <option value={term}>{term}</option>
                                        )}
                                    </select>
                                </div>
                                <div className={"col-1"}>.com</div>
                            </div>
                        </div>
                        <br/>

                        <div className="form-group">
                            <label htmlFor="password">Password</label>
                            <input type="password"
                                   className="form-control"
                                   id="password"
                                   name="password"
                                   required
                                   placeholder="Enter the password"
                                   onChange={handleChange}/>
                        </div>
                        <br/>

                        <button id="submit" type="submit" className="btn btn-primary">Submit</button>

                        <br/>
                    </form>

                    <Link type="button" className="btn btm-info" to={"/artists/register"}>Register Artist</Link>
                </div>
            </div>
        </div>
    );
};

export default ArtistLogin;