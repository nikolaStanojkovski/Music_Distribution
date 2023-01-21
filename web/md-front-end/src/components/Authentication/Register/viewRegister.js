import ScreenElementsUtil from "../../../util/screenElementsUtil";
import React from "react";

const viewRegister = (props) => {
    return (
        <form onSubmit={props.onFormSubmit}>
            <div className={"form-group"}>
                <label className="upload-drop-container">
                    <span className="upload-drop-title">Profile picture</span>
                    <input type="file" id="songUpload" accept="image/png, image/jpeg" required={true}
                           onChange={(e) => props.updateProfilePicture(e.target.files[0])}/>
                    <span
                        className={"text-muted"}><b>png</b> and <b>jpeg</b> file formats accepted</span>
                </label>
                <br/>
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

            <div className="form-group">
                <div className={"row"}>
                    <div className={"col-12"}>
                        <input type="password"
                               className="form-control"
                               id="repeatPassword"
                               name="repeatPassword"
                               required
                               placeholder="Repeat the password"
                               onChange={props.handleChange}/>
                    </div>
                </div>
            </div>
            <br/>

            <div className="accordion">
                <div className="accordion-item">
                    <h2 className="accordion-header">
                        <button className="accordion-button collapsed" type="button"
                                onClick={e => ScreenElementsUtil.toggleAccordionItems(e)}>
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
                                       onChange={props.handleChange}/>
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
                                       onChange={props.handleChange}/>
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
                                       onChange={props.handleChange}/>
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
                                       onChange={props.handleChange}/>
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
    );
}

export default viewRegister;