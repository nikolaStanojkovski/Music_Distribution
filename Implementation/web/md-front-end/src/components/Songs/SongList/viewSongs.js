import React from "react";
import {CREATOR, FORMATTED_STRING, IS_A_SINGLE, SONG_LENGTH} from "../../../constants/model";
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
                    <th scope={"col"}>Artist</th>
                    {
                        props.renderTableRowHeader()
                    }
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
                            <td>{(term[CREATOR]['userPersonalInfo'].artName)
                                ? term[CREATOR]['userPersonalInfo'].artName
                                : term[CREATOR]['userPersonalInfo'].fullName}</td>
                            {
                                props.renderTableRowData(term)
                            }
                            <td className={"table-cell-clickable"}>
                                <button key={term.id} onClick={props.fetchSong}
                                        className={`btn btn-outline-secondary btn-block bi bi-play play-pause-button`}>
                                </button>
                            </td>
                        </tr>
                    );
                }) : undefined}
                </tbody>
            </table>
        </div>
    );
}

export default viewSongs;