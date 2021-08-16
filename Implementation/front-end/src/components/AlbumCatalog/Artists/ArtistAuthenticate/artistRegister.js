import React from 'react';
import {Link, useHistory} from 'react-router-dom';

const RegisterArtist = (props) => {

    const History = useHistory();
    const [formData, updateFormData] = React.useState({
        username: "",
        emailDomain: 0,
        telephoneNumber: "",
        firstName: "",
        lastName: "",
        artName: "",
        password: ""
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

        props.registerArtist(username, emailDomain, telephoneNumber, firstName, lastName, artName, password);
        History.push("/songs");
    }

    return (
        <div className="container">
            <h1 className={"text-center mt-4"}>Create a new artist</h1>
            <br/>
            <div className={""}>
                <div className="col-md-6">
                    <br/>
                    <form onSubmit={onFormSubmit}>

                        <div className="form-group">
                            <label htmlFor="telephoneNumber">Telephone number</label>
                            <input type="text"
                                   className="form-control"
                                   id="telephoneNumber"
                                   name="telephoneNumber"
                                   required
                                   placeholder="Enter the telephone number"
                                   onChange={handleChange}/>
                        </div>
                        <br/>

                        <div className="form-group">
                            <label htmlFor="firstName">First Name</label>
                            <input type="text"
                                   className="form-control"
                                   id="firstName"
                                   name="firstName"
                                   required
                                   placeholder="Enter the first name"
                                   onChange={handleChange}/>
                        </div>
                        <br/>

                        <div className="form-group">
                            <label htmlFor="lastName">Last Name</label>
                            <input type="text"
                                   className="form-control"
                                   id="lastName"
                                   name="lastName"
                                   required
                                   placeholder="Enter the last name"
                                   onChange={handleChange}/>
                        </div>
                        <br/>

                        <div className="form-group">
                            <label htmlFor="artName">Art Name</label>
                            <input type="text"
                                   className="form-control"
                                   id="artName"
                                   name="artName"
                                   required
                                   placeholder="Enter the art name"
                                   onChange={handleChange}/>
                        </div>
                        <br/>

                        <hr />
                        <br />

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
                                    <select onChange={handleChange} name="emailDomain" className="form-control">
                                        {props.emailDomains.map((term) =>
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
                </div>
            </div>
        </div>
    );
};

export default RegisterArtist;