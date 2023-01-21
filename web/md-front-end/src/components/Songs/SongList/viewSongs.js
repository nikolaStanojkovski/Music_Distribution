import React from "react";
import {CREATOR, FORMATTED_STRING, IS_A_SINGLE, SONG_LENGTH, USER_PERSONAL_INFO} from "../../../constants/model";
import {EMPTY_STRING} from "../../../constants/alphabet";
import SongTableHeader from "./Partial/songTableHeader";
import SongTableData from "./Partial/songTableData";

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
                    <SongTableHeader/>
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
                            <td>{(term[CREATOR][USER_PERSONAL_INFO].artName)
                                ? term[CREATOR][USER_PERSONAL_INFO].artName
                                : term[CREATOR][USER_PERSONAL_INFO].fullName}</td>
                            <SongTableData term={term}/>
                            <td className={"table-cell-clickable"}>
                                <button onClick={props.fetchSong}
                                        className={`btn btn-outline-secondary btn-block bi bi-play play-pause-button`}>
                                </button>
                            </td>
                            <td className={"table-cell-clickable"}>
                                <button onClick={props.downloadSong}
                                        className={`btn btn-outline-secondary btn-block bi bi-download`}>
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