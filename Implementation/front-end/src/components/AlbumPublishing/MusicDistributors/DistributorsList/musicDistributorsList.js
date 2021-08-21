import React from 'react';
import {Link} from 'react-router-dom';

const musicDistributors = (props) => {
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
            <br />
            <div className={"row"}>
                <div className={"table-responsive"}>
                    <table className={"table table-striped"}>
                        <thead>
                        <tr>
                            <th scope={"col"}>Company Name</th>
                            <th scope={"col"}>Distributor Name</th>
                            <th scope={"col"}>Total Earned</th>
                            <th scope={"col"}>No. Albums Published</th>
                        </tr>
                        </thead>
                        <tbody>
                        {props.musicDistributors.map((term) => {
                            return (
                                <tr>
                                    <td>{term.distributorInfoFull.companyName}</td>
                                    <td>{term.distributorInfoFull.distributorName}</td>
                                    <td>{term.totalEarned.amount + " " + term.totalEarned.currency}</td>
                                    <td>{term.noAlbumsPublished}</td>
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

export default musicDistributors;