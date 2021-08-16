import React from 'react';
import {Link} from 'react-router-dom';

const songs = (props) => {
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
                    <Link to={"/artists/login/song"} className={"btn btn-success"}>
                        Add Song
                    </Link>
                </div>
            </div>
            <br />
            <div className={"row"}>
                <div className={"table-responsive"}>
                    <table className={"table table-striped"}>
                        <thead>
                        <tr>
                            <th scope={"col"}>Name</th>
                            <th scope={"col"}>Length</th>
                            <th scope={"col"}>Single</th>
                            <th scope={"col"}>Creator</th>
                            <th scope={"col"}>Album</th>
                        </tr>
                        </thead>
                        <tbody>
                        {props.songs.map((term) => {
                            var yesNo = "";
                            if(term.isASingle === true)
                                yesNo = "Yes";
                            else
                                yesNo = "No";

                            var creator = "None";
                            var album = "None";
                            if(term.creator !== null && term.creator !== undefined)
                                creator = term.creator.artistPersonalInfo.firstName + " " + term.creator.artistPersonalInfo.lastName;
                            if(term.album !== null && term.album !== undefined)
                                album = term.album.albumName;

                            return (
                                <tr>
                                    <td>{term.songName}</td>
                                    <td>{term.songLength.lengthMinutes}m : {term.songLength.lengthSeconds % 60}s</td>
                                    <td>{yesNo}</td>
                                    <td>{creator}</td>
                                    <td>{album}</td>
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

export default songs;