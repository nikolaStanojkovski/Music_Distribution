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
                        <Link to={"/artists/login/raiseAlbumTier"} className="btn btn-info w-100">Raise Album Tier</Link>
                    </form>
                </div>
                <div className={"col-4"}>
                    <form className="form-inline my-2 my-lg-0">
                        <Link to={"/artists/login/unpublish"} className="btn btn-outline-danger w-100">
                            Unpublish Album
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
                            var yesNo = "";
                            if (term.isPublished === true)
                                yesNo = "Yes";
                            else
                                yesNo = "No";

                            var creator = "None";
                            if (term.creator != null)
                                creator = term.creator.artistPersonalInfo.firstName + " " + term.creator.artistPersonalInfo.lastName;

                            var length = "No songs yet";
                            if (term.totalLength.lengthSeconds > 0)
                                length = (term.totalLength.lengthMinutes) + "m : " + (term.totalLength.lengthSeconds % 60) + "s";

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