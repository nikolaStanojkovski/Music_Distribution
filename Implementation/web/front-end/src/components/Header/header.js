import React from 'react';
import {Link} from 'react-router-dom';
import logoSymbol from '../logo-symbol.png';

const Header = () => {
    return (
        <header>
            <nav className="navbar navbar-expand-md navbar-dark navbar-fixed bg-dark">
                <div className={"container"}>
                    <Link className="navbar-brand" to={"/"}>
                        <img src={logoSymbol} alt={""} height={40} width={40} />
                    </Link>
                    <div className="collapse navbar-collapse" id="navbarCollapse">
                        <ul className="navbar-nav mr-auto">
                            <li className="nav-item active">
                                <Link className="nav-link" to={"/albums"}>ALBUMS</Link>
                            </li>
                            <li className="nav-item active">
                                <Link className="nav-link" to={"/songs"}>SONGS</Link>
                            </li>
                            <li className="nav-item active">
                                <Link className={"nav-link"} to={"/artists"}>ARTISTS</Link>
                            </li>
                        </ul>
                    </div>

                    <form className="form-inline my-2 my-lg-0">
                        <Link className="btn btn-outline-light my-2 my-sm-0" to={"/login"}>LOGIN</Link>
                    </form>
                    &nbsp; &nbsp;
                    <form className="form-inline my-2 my-lg-0">
                        <Link className="btn btn-outline-light my-2 my-sm-0" to={"/register"}>REGISTER</Link>
                    </form>
                </div>
            </nav>
        </header>
    )
}

export default Header;