import React from 'react';
import {Link} from 'react-router-dom';

const albums = (props) => {
    return (

        <div className={"container mm-4 mt-5"}>
            <div className={"row"}>
                <div className={"col-4"}>
                    <form className="form-inline my-2 my-lg-0">
                        <Link to={"/artists/login/publish"} className="btn btn-primary w-100">Publish Album</Link>
                    </form>
                </div>
                <div className={"col-4"}>
                    <form className="form-inline my-2 my-lg-0">
                        <Link to={"/artists/login/raiseAlbumTier"} className="btn btn-info w-100">Raise Album
                            Tier</Link>
                    </form>
                </div>
                <div className={"col-4"}>
                    <form className="form-inline my-2 my-lg-0">
                        <Link to={"/artists/login/unPublish"} className="btn btn-outline-danger w-100">
                            UnPublish Album
                        </Link>
                    </form>
                </div>
            </div>
            <br/>
            <div className={"row"}>
                <div className={"col-12"}>
                    <Link to={"/artists/login/album"} className={"btn btn-success"}>
                        Add Album
                    </Link>
                </div>
            </div>
            <br/>
            <div className={"row"}>
                <div className={"table-responsive"}>
                    <table className={"table table-striped"}>
                        <thead>
                        <tr>
                            <th scope={"col"}>Name</th>
                            <th scope={"col"}>Length</th>
                            <th scope={"col"}>Published</th>
                            <th scope={"col"}>Genre</th>
                            <th scope={"col"}>Creator</th>
                        </tr>
                        </thead>
                        <tbody>
                        {props.albums.map((term) => {
                            let yesNo = term.isPublished === true ? "Yes" : "No";
                            let creator = term.creator != null ? term.creator.artistPersonalInfo.firstName + " " + term.creator.artistPersonalInfo.lastName : "None";
                            let length = term.totalLength.lengthSeconds > 0 ? (term.totalLength.lengthMinutes) + "m : " + (term.totalLength.lengthSeconds % 60) + "s" : "No songs yet";

                            return (
                                <tr>
                                    <td>{term.albumName}</td>
                                    <td>{length}</td>
                                    <td>{yesNo}</td>
                                    <td>{term.genre}</td>
                                    <td>{creator}</td>
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

export default albums;