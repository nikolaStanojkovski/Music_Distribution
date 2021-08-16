import React from 'react';
import {Link} from 'react-router-dom';

const header = (props) => {
    return (
        <header>
            <nav className="navbar navbar-expand-md navbar-dark navbar-fixed bg-dark">
                <div className={"container"}>
                    <Link className="navbar-brand" to={"/albums"}>Album Distribution Application</Link>
                    <div className="collapse navbar-collapse" id="navbarCollapse">
                        <ul className="navbar-nav mr-auto">
                            <li className="nav-item active">
                                <Link className="nav-link" to={"/albums"}>Albums</Link>
                            </li>
                            <li className="nav-item active">
                                <Link className="nav-link" to={"/songs"}>Songs</Link>
                            </li>
                            <li className="nav-item active">
                                <Link className={"nav-link"} to={"/artists"}>Artists</Link>
                            </li>
                        </ul>
                    </div>

                    <form className="form-inline my-2 my-lg-0">
                        <Link className="btn btn-outline-light my-2 my-sm-0" to={"/publishedAlbums"}>Published Albums</Link>
                    </form>
                    &nbsp; &nbsp;
                    <form className="form-inline my-2 my-lg-0">
                        <Link className="btn btn-outline-light my-2 my-sm-0" to={"/distributors"}>Music Distributors</Link>
                    </form>
                </div>
            </nav>
        </header>
    )
}

export default header;