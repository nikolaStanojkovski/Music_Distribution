import AuthUtil from "../../../util/authUtil";
import React from "react";

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
                            className={`${(term['isASingle']) ? 'table-row-clickable' : ''} align-middle`}
                            onClick={(e) => props.fetchSongCover(e, term.id, term['isASingle'])}>
                            <td>{term.songName}</td>
                            <td>{term.songGenre}</td>
                            <td>{term['songLength']['formattedString']}</td>
                            {
                                (AuthUtil.isAuthorized())
                                    ? <td>{(term['isPublished']) ? 'Yes' : 'No'}</td> : null
                            }
                            <td>{(term['isASingle']) ? 'Yes' : 'No'}</td>
                            <td>{term['creator']['userPersonalInfo'].fullName}</td>
                            <td>{(term['album']) ? term['album'].albumName : ''}</td>
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