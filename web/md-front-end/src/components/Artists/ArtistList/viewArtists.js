import {Link} from "react-router-dom";
import React from "react";
import {FULL_ADDRESS, USER_CONTACT_INFO, USER_PERSONAL_INFO} from "../../../constants/model";
import {ALBUMS, CREATOR_ID, SONGS} from "../../../constants/endpoint";

const viewArtists = (props) => {
    return (
        <div className={"table-responsive"}>
            <table className={"table table-striped"}>
                <thead>
                <tr>
                    <th scope={"col"}>Email</th>
                    <th scope={"col"}>Telephone number</th>
                    <th scope={"col"}>Name</th>
                    <th scope={"col"}>Art Name</th>
                    <th scope={"col"}>Albums</th>
                    <th scope={"col"}>Songs</th>
                </tr>
                </thead>
                <tbody>
                {(props.artists && props.artists.content) ? props.artists.content.map((term) => {
                    return (
                        <tr key={term.id} className={"table-row-clickable align-middle"}
                            onClick={(e) => props.fetchArtistPicture(e, term.id)}>
                            <td>{term[USER_CONTACT_INFO].email[FULL_ADDRESS]}</td>
                            <td>{term[USER_CONTACT_INFO].telephoneNumber}</td>
                            <td>{term[USER_PERSONAL_INFO].fullName}</td>
                            <td>{term[USER_PERSONAL_INFO].artName}</td>
                            <td className={"table-cell-clickable"}>
                                <Link to={`${ALBUMS}?${CREATOR_ID}=${term.id}`}
                                      className={`btn btn-outline-secondary btn-block bi bi-list`}>
                                </Link>
                            </td>
                            <td className={"table-cell-clickable"}>
                                <Link to={`${SONGS}?${CREATOR_ID}=${term.id}`}
                                      className={`btn btn-outline-secondary btn-block bi bi-list`}>
                                </Link>
                            </td>
                        </tr>
                    );
                }) : <tr/>}
                </tbody>
            </table>
        </div>
    );
}

export default viewArtists;