import React from 'react';
import {Link} from 'react-router-dom';
import logoSymbol from "../../../../assets/img/logo/logo-white-symbol.png";

const AuthHeader = (props) => {
    return (
        <header>
            <nav className="navbar navbar-expand-lg navbar-dark bg-dark">
                <div className={"container"}>
                    <Link className="navbar-brand" to={"/"}>
                        <img className={"navbar-img"} src={logoSymbol} alt={""} height={40} width={40}/>
                    </Link>

                    <button className="navbar-toggler" type="button">
                        <span className="navbar-toggler-icon"/>
                    </button>

                    <div className="collapse navbar-collapse" id={"navbarSupportedContent"}>
                        <ul className="navbar-nav mr-auto">
                            <li className="nav-item dropdown active">
                                <a className="nav-link dropdown-toggle" href={"#"}
                                   id={"albumsToggleDropdown"}>Albums</a>
                                <div className="dropdown-menu" aria-labelledby="albumsToggleDropdown">
                                    <Link to={"/albums"} className="dropdown-item">View</Link>
                                    <div className="dropdown-divider"/>
                                    <Link to={"/albums/publish"} className="dropdown-item">Publish</Link>
                                    <div className="dropdown-divider"/>
                                    <Link to={"/albums/raise-tier"} className="dropdown-item">Raise Tier</Link>
                                </div>
                            </li>
                            <li className="nav-item dropdown">
                                <a className="nav-link dropdown-toggle" href={"#"} id={"songsToggleDropdown"}>Songs</a>
                                <div className="dropdown-menu" aria-labelledby="songsToggleDropdown">
                                    <Link to={"/songs"} className="dropdown-item">View</Link>
                                    <div className="dropdown-divider"/>
                                    <Link to={"/songs/publish"} className="dropdown-item">Publish</Link>
                                    <div className="dropdown-divider"/>
                                    <Link to={"/songs/raise-tier"} className="dropdown-item">Raise Tier</Link>
                                </div>
                            </li>
                            <li className="nav-item">
                                <Link className={"nav-link"} to={"/artists"}>Artists</Link>
                            </li>
                            <li className="nav-item">
                                <Link className="nav-link" to={"/songs/create"}>
                                    <svg xmlns="http://www.w3.org/2000/svg" width="23" height="23" fill="currentColor"
                                         className="bi bi-plus-circle" viewBox="0 0 16 16">
                                        <path
                                            d="M8 15A7 7 0 1 1 8 1a7 7 0 0 1 0 14zm0 1A8 8 0 1 0 8 0a8 8 0 0 0 0 16z"/>
                                        <path
                                            d="M8 4a.5.5 0 0 1 .5.5v3h3a.5.5 0 0 1 0 1h-3v3a.5.5 0 0 1-1 0v-3h-3a.5.5 0 0 1 0-1h3v-3A.5.5 0 0 1 8 4z"/>
                                    </svg>
                                </Link>
                            </li>
                        </ul>

                        <form id={"authContainerInside"} className="form-inline text-right my-2 my-lg-0" hidden>
                            <Link className="btn btn-outline-light my-2 my-sm-0"
                                  to={"/"}>{props.loggedArtist['email']}</Link>
                            &nbsp;&nbsp;
                            <Link to={"/"} className="btn btn-outline-light my-2 my-sm-0" onClick={props.logoutArtist}>Logout
                            </Link>
                        </form>
                    </div>

                    <form id={"authContainerOutside"} className="form-inline text-right my-2 my-lg-0">
                        <Link className="btn btn-outline-light my-2 my-sm-0"
                              to={"/"}>{props.loggedArtist['email']}</Link>
                        &nbsp;&nbsp;
                        <Link to={"/"} className="btn btn-outline-light my-2 my-sm-0" onClick={props.logoutArtist}>Logout
                        </Link>
                    </form>
                </div>
            </nav>
        </header>
    )
}

export default AuthHeader;