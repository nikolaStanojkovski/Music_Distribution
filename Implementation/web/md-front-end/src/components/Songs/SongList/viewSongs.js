import AuthUtil from "../../../util/authUtil";
import React from "react";
import {ALBUM, CREATOR, FORMATTED_STRING, IS_A_SINGLE, IS_PUBLISHED, SONG_LENGTH} from "../../../constants/model";
import {EMPTY_STRING} from "../../../constants/alphabet";

const viewSongs = (props) => {

    return (
        <div className={"table-responsive"}>
            <table className={"table table-striped"}>
                <thead>
                <tr>
                    <th scope={"col"}>Name</th>
                    <th scope={"col"}>Genre</th>
                    <th scope={"col"}>Length</th>
                    {
                        (AuthUtil.isAuthorized())
                            ? <th scope={"col"}>Is Published</th> : null
                    }
                    <th scope={"col"}>Is Single</th>
                    <th scope={"col"}>Artist</th>
                    <th scope={"col"}>Album</th>
                    <th scope={"col"}/>
                </tr>
                </thead>
                <tbody>
                {(props.songs && props.songs.content) ? props.songs.content.map((term) => {
                    return (
                        <tr key={term.id} data-id={term.id}
                            className={`${(term[IS_A_SINGLE]) ? 'table-row-clickable' : EMPTY_STRING} align-middle`}
                            onClick={(e) => props.fetchSongCover(e, term.id, term[IS_A_SINGLE])}>
                            <td>{term.songName}</td>
                            <td>{term.songGenre}</td>
                            <td>{term[SONG_LENGTH][FORMATTED_STRING]}</td>
                            {
                                (AuthUtil.isAuthorized())
                                    ? <td>{(term[IS_PUBLISHED]) ? 'Yes' : 'No'}</td> : null
                            }
                            <td>{(term[IS_A_SINGLE]) ? 'Yes' : 'No'}</td>
                            <td>{term[CREATOR]['userPersonalInfo'].fullName}</td>
                            <td>{(term[ALBUM]) ? term[ALBUM].albumName : EMPTY_STRING}</td>
                            <td className={"table-cell-clickable"}>
                                <button onClick={props.fetchSong}
                                        className={`btn btn-outline-secondary btn-block bi bi-play play-pause-button`}>
                                </button>
                            </td>
                        </tr>
                    );
                }) : null}
                </tbody>
            </table>
        </div>
    );
}

export default viewSongs;