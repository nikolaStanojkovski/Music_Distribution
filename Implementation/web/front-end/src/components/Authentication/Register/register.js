import React from 'react';
import {Link, useHistory} from 'react-router-dom';

const Register = (props) => {

    const History = useHistory();
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

    const toggleAccordionItems = (e) => {
        const clickedElement = e.target;
        if (clickedElement && clickedElement instanceof HTMLElement) {
            clickedElement.classList.toggle('collapsed');
            const accordionItemContainer = clickedElement.parentElement.parentElement.querySelector(".accordion-collapse.collapse");
            if (accordionItemContainer && accordionItemContainer instanceof HTMLElement) {
                accordionItemContainer.classList.toggle('show');
            }
        }
    }

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

                    <form onSubmit={onFormSubmit}>
                        <div className={"form-group"}>
                            <label className="upload-drop-container">
                                <span className="upload-drop-title">Profile picture</span>
                                <input type="file" id="songUpload" accept="image/png, image/jpeg" required={true}
                                       onChange={(e) => updateProfilePicture(e.target.files[0])}/>
                                <span
                                    className={"text-muted"}><b>png</b> and <b>jpeg</b> file formats accepted</span>
                            </label>
                            <br />
                        </div>

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
                                            {props.emailDomains.map((term) =>
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

                        <div className="form-group">
                            <div className={"row"}>
                                <div className={"col-12"}>
                                    <input type="password"
                                           className="form-control"
                                           id="repeatPassword"
                                           name="repeatPassword"
                                           required
                                           placeholder="Repeat the password"
                                           onChange={handleChange}/>
                                </div>
                            </div>
                        </div>
                        <br/>

                        <div className="accordion" id="accordionExample">
                            <div className="accordion-item">
                                <h2 className="accordion-header" id="headingOne">
                                    <button className="accordion-button collapsed" type="button"
                                            onClick={toggleAccordionItems}>
                                        Additional information
                                    </button>
                                </h2>
                                <div id="collapseOne" className="accordion-collapse collapse">
                                    <div className={"row"}>
                                        <div className="form-group form-group-inner mt-4">
                                            <input type="tel"
                                                   className="form-control"
                                                   id="telephoneNumber"
                                                   name="telephoneNumber"
                                                   placeholder="Enter your telephone number"
                                                   onChange={handleChange}/>
                                        </div>
                                    </div>
                                    <br/>
                                    <div className={"row"}>
                                        <div className="form-group form-group-inner">
                                            <input type="text"
                                                   className="form-control"
                                                   id="firstName"
                                                   name="firstName"
                                                   placeholder="Enter your first name"
                                                   onChange={handleChange}/>
                                        </div>
                                    </div>
                                    <br/>
                                    <div className={"row"}>
                                        <div className="form-group form-group-inner">
                                            <input type="text"
                                                   className="form-control"
                                                   id="lastName"
                                                   name="lastName"
                                                   placeholder="Enter your last name"
                                                   onChange={handleChange}/>
                                        </div>
                                    </div>
                                    <br/>
                                    <div className={"row"}>
                                        <div className="form-group form-group-inner">
                                            <input type="text"
                                                   className="form-control"
                                                   id="artName"
                                                   name="artName"
                                                   placeholder="Enter your art name"
                                                   onChange={handleChange}/>
                                        </div>
                                    </div>
                                    <br/>
                                </div>
                            </div>
                        </div>

                        <br/>

                        <button id="submit" type="submit" className="btn btn-dark w-100 mt-3">Sign Up</button>

                        <br/>
                    </form>

                    <Link to={"/login"} className="btn btn-outline-dark w-100 mt-3">Sign In</Link>

                </div>
                <div className={"col-md-2"}/>
            </div>
        </div>
    );
};

export default Register;