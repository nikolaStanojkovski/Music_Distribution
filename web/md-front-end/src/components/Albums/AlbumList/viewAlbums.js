import {Link} from "react-router-dom";
import React from "react";
import {
    ALBUM_INFO, ARTIST_NAME, COMPOSER_NAME,
    CREATOR,
    FORMATTED_STRING,
    FULL_NAME,
    GENRE, PRODUCER_NAME,
    TOTAL_LENGTH,
    USER_PERSONAL_INFO
} from "../../../constants/model";
import {EMPTY_STRING} from "../../../constants/alphabet";
import {ALBUM_ID, SONGS} from "../../../constants/endpoint";

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
                            <td>{term[TOTAL_LENGTH][FORMATTED_STRING]}</td>
                            <td>{term[GENRE]}</td>
                            <td>{(term[CREATOR]
                                    && term[CREATOR][USER_PERSONAL_INFO]
                                    && term[CREATOR][USER_PERSONAL_INFO]
                                    && term[CREATOR][USER_PERSONAL_INFO][FULL_NAME])
                                ? term[CREATOR][USER_PERSONAL_INFO][FULL_NAME] : EMPTY_STRING}</td>
                            <td>{(term[ALBUM_INFO]) ? term[ALBUM_INFO][ARTIST_NAME] : EMPTY_STRING}</td>
                            <td>{(term[ALBUM_INFO]) ? term[ALBUM_INFO][PRODUCER_NAME] : EMPTY_STRING}</td>
                            <td>{(term[ALBUM_INFO]) ? term[ALBUM_INFO][COMPOSER_NAME] : EMPTY_STRING}</td>
                            <td className={"table-cell-clickable"}>
                                <Link to={`${SONGS}?${ALBUM_ID}=${term.id}`}
                                      className={`btn btn-outline-secondary btn-block bi bi-list`}>
                                </Link>
                            </td>
                        </tr>
                    );
                }) : undefined}
                </tbody>
            </table>
        </div>
    );
}

export default viewAlbums;