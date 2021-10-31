import React from 'react';
import {Link} from 'react-router-dom';

const publishedAlbums = (props) => {
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
                        <Link to={"/artists/login/unPublish"} className="btn btn-outline-danger w-100">
                            Unpublish Album
                        </Link>
                    </form>
                </div>
            </div>
            <br />
            <div className={"row"}>
                <div className={"table-responsive"}>
                    <table className={"table table-striped"}>
                        <thead>
                        <tr>
                            <th scope={"col"}>Album</th>
                            <th scope={"col"}>Artist</th>
                            <th scope={"col"}>Publisher</th>
                            <th scope={"col"}>Published On</th>
                            <th scope={"col"}>Tier</th>
                            <th scope={"col"}>Cost</th>
                        </tr>
                        </thead>
                        <tbody>
                        {props.publishedAlbums.map((term) => {
                            return (
                                <tr>
                                    <td>{term.albumName}</td>
                                    <td>{term.artistInformation}</td>
                                    <td>{term.musicPublisherInfo}</td>
                                    <td>{term.publishedOn}</td>
                                    <td>{term.albumTier}</td>
                                    <td>{term.totalCost.amount + " " + term.totalCost.currency}</td>
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

export default publishedAlbums;