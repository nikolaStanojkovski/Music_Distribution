import React from 'react';
import {Link} from 'react-router-dom';

const artists = (props) => {
    return (
        <div className={"container mm-4 mt-5"}>
            <div className={"row"}>
                <div className={"col-12"}>
                    <form className="form-inline my-2 my-lg-0">
                        <Link to={"/artists/login/publish"} className="btn btn-primary w-100">Publish Album</Link>
                    </form>
                </div>
            </div>
            <br />
            <div className={"row"}>
                <div className={"col-12"}>
                    <Link className={"btn btn-success"} to={"/artists/register"}>Create Artist</Link>
                </div>
            </div>
            <br />
            <div className={"row"}>
                <div className={"table-responsive"}>
                    <table className={"table table-striped"}>
                        <thead>
                        <tr>
                            <th scope={"col"}>Email</th>
                            <th scope={"col"}>Telephone number</th>
                            <th scope={"col"}>First Name</th>
                            <th scope={"col"}>Last Name</th>
                            <th scope={"col"}>Art Name</th>
                        </tr>
                        </thead>
                        <tbody>
                        {props.artists.map((term) => {
                            return (
                                <tr>
                                    <td>{term.artistContactInfo.email.fullAddress}</td>
                                    <td>{term.artistContactInfo.telephoneNumber}</td>

                                    <td>{term.artistPersonalInfo.firstName}</td>
                                    <td>{term.artistPersonalInfo.lastName}</td>
                                    <td>{term.artistPersonalInfo.artName}</td>
                                </tr>
                            );
                        })}
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    )
}

export default artists;