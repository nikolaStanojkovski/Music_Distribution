import React from 'react';
import {useHistory} from 'react-router-dom';

const Register = (props) => {

    const History = useHistory();
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

        if(password && repeatPassword && password !== repeatPassword) {
            alert('The passwords do not match');
            return;
        }

        props.registerArtist(username, emailDomain, telephoneNumber,
            firstName, lastName, artName, password, repeatPassword);
        History.push("/");
    }

    const toggleAccordionItems = (e) => {
        const clickedElement = e.target;
        if(clickedElement && clickedElement instanceof HTMLElement) {
            clickedElement.classList.toggle('collapsed');
            const accordionItemContainer = clickedElement.parentElement.parentElement.querySelector(".accordion-collapse.collapse");
            if(accordionItemContainer && accordionItemContainer instanceof HTMLElement) {
                accordionItemContainer.classList.toggle('show');
            }
        }
    }

    return (
        <div className="container mm-4 my-5">
            <div className={"row mb-5"}>
                <h1 className="display-5">Sign Up</h1>
                <p className="text-muted">Join us and become a part of the community.</p>
            </div>
            <div className={"row"}>
                <div className="col-md-8">
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
                        <br />

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
                                    <button className="accordion-button collapsed" type="button" onClick={toggleAccordionItems}>
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
                                                   required
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
                                                   required
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
                                                   required
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
                                                   required
                                                   placeholder="Enter your art name"
                                                   onChange={handleChange}/>
                                        </div>
                                    </div>
                                    <br/>
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

export default Register;