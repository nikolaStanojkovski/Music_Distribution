import React from "react";

const viewLogin = (props) => {
    return (
        <form onSubmit={props.onFormSubmit}>
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
                                   onChange={props.handleChange}/>
                            <div className="input-group-prepend">
                                <span className="input-group-text">@</span>
                            </div>
                            <select onChange={props.handleChange} name="emailDomain" className="form-control">
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
                               onChange={props.handleChange}/>
                    </div>
                </div>
            </div>
            <br/>

            <button id="submit" type="submit" className="btn btn-dark w-100 mt-3">Sign In</button>

            <br/>
        </form>
    );
}

export default viewLogin;