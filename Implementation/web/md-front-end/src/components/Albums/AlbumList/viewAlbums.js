import {Link} from "react-router-dom";
import React from "react";

const viewAlbums = (props) => {
    return (
        <div className={"table-responsive"}>
            <table className={"table table-striped"}>
                <thead>
                <tr>
                    <th scope={"col"}>Name</th>
                    <th scope={"col"}>Length</th>
                    <th scope={"col"}>Genre</th>
                    <th scope={"col"}>Publisher</th>
                    <th scope={"col"}>Artist</th>
                    <th scope={"col"}>Producer</th>
                    <th scope={"col"}>Composer</th>
                    <th scope={"col"}>Songs</th>
                </tr>
                </thead>
                <tbody>
                {(props.albums && props.albums.content) ? props.albums.content.map((term) => {
                    return (
                        <tr key={term.id} className={'table-row-clickable align-middle'}
                            onClick={(e) => props.fetchAlbumCover(e, term.id)}>
                            <td>{term.albumName}</td>
                            <td>{term['totalLength']['formattedString']}</td>
                            <td>{term['genre']}</td>
                            <td>{(term['creator'] && term['creator']['userPersonalInfo'])
                                ? term['creator']['userPersonalInfo'].fullName : ''}</td>
                            <td>{(term['albumInfo']) ? term['albumInfo']['artistName'] : ''}</td>
                            <td>{(term['albumInfo']) ? term['albumInfo']['producerName'] : ''}</td>
                            <td>{(term['albumInfo']) ? term['albumInfo']['composerName'] : ''}</td>
                            <td className={"table-cell-clickable"}>
                                <Link to={"/songs?album_id=" + term.id}
                                      className={`btn btn-outline-secondary btn-block bi bi-list`}>
                                </Link>
                            </td>
                        </tr>
                    );
                }) : null}
                </tbody>
            </table>
        </div>
    );
}

export default viewAlbums;