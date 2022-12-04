import {Link} from "react-router-dom";
import React from "react";

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
                            <td>{term['userContactInfo'].email['fullAddress']}</td>
                            <td>{term['userContactInfo'].telephoneNumber}</td>
                            <td>{term['userPersonalInfo'].fullName}</td>
                            <td>{term['userPersonalInfo'].artName}</td>
                            <td className={"table-cell-clickable"}>
                                <Link to={"/albums?creator_id=" + term.id}
                                      className={`btn btn-outline-secondary btn-block bi bi-list`}>
                                </Link>
                            </td>
                            <td className={"table-cell-clickable"}>
                                <Link to={"/songs?creator_id=" + term.id}
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