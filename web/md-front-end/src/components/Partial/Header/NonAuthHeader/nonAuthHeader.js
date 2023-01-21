import React from 'react';
import {Link} from 'react-router-dom';
import logoSymbol from "../../../../assets/img/logo/logo-white-symbol.png";
import {ALBUMS, ARTISTS, LOGIN, REGISTER, SONGS} from "../../../../constants/endpoint";

const NonAuthHeader = () => {
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
                            <li className="nav-item">
                                <Link className={"nav-link"} to={ALBUMS}>Albums</Link>
                            </li>
                            <li className="nav-item">
                                <Link className={"nav-link"} to={SONGS}>Songs</Link>
                            </li>
                            <li className="nav-item">
                                <Link className={"nav-link"} to={ARTISTS}>Artists</Link>
                            </li>
                        </ul>

                        <form id={"authContainerInside"} className="form-inline text-right my-2 my-lg-0" hidden>
                            <Link className="btn btn-outline-light my-2 my-sm-0" to={LOGIN}>Login</Link>
                            &nbsp;&nbsp;
                            <Link className="btn btn-outline-light my-2 my-sm-0" to={REGISTER}>Register</Link>
                        </form>
                    </div>

                    <form id={"authContainerOutside"} className="form-inline text-right my-2 my-lg-0">
                        <Link className="btn btn-outline-light my-2 my-sm-0" to={LOGIN}>Login</Link>
                        &nbsp;&nbsp;
                        <Link className="btn btn-outline-light my-2 my-sm-0" to={REGISTER}>Register</Link>
                    </form>
                </div>
            </nav>
        </header>
    )
}

export default NonAuthHeader;