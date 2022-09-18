import React from 'react';
import {Link} from "react-router-dom";
import logo from '../logo.png';

const Home = () => {
    return (
        <div className={"container"}>

            <div className={"row"}>
                <div className="position-relative overflow-hidden p-3 p-md-5 m-md-3 text-center">
                    <img src={logo} alt={""} className="display-3 font-weight-normal mb-3" />
                </div>

                <div className={"row"}>
                    <div className={"col-6"}>
                        <div className="card">
                            <img className="card-img-top"
                                 src={"https://haulixdaily.com/wp-content/uploads/2019/10/Music-Distribution-Explained.png"}
                                 alt={"Music distributors image"}
                            />
                            <Link to={"/distributors"} className={"btn w-100 h-100 btn-outline-dark"}>
                                Music Distributors
                            </Link>
                        </div>
                    </div>
                    <div className={"col-6"}>
                        <div className="card">
                            <img className="card-img-top"
                                 height={380}
                                 src={"https://townsquare.media/site/295/files/2014/12/titles-630x4201.jpg?w=980&q=75"}
                                 alt={"Published albums image"}/>
                            <Link to={"/publishedAlbums"} className={"btn w-100 h-100 btn-outline-dark"}>
                                Published Albums
                            </Link>
                        </div>
                    </div>
                </div>
                <div className={"row mt-5 mb-5"}>
                    <div className={"col-4"}>
                        <div className="card">
                            <img className="card-img-top"
                                 src={"https://static.billboard.com/files/2021/04/record-store-billboard-1548-1617823166-compressed.jpg"}
                                 alt={"Albums image"}/>
                            <Link to={"/albums"} className={"btn w-100 btn-outline-secondary"}>
                                Albums
                            </Link>
                        </div>
                    </div>
                    <div className={"col-4"}>
                        <div className="card">
                            <img className="card-img-top"
                                 src={"https://images.unsplash.com/photo-1576747249421-0c693525b538?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1000&q=80"}
                                 alt={"Artists image"}
                            />
                            <Link to={"/artists"} className={"btn w-100 btn-outline-secondary"}>
                                Artists
                            </Link>
                        </div>
                    </div>
                    <div className={"col-4"}>
                        <div className="card">
                            <img className="card-img-top"
                                 src={"https://www.kveller.com/wp-content/uploads/2016/05/shabbat-songs-1200x800.jpg"}
                                 alt={"Songs image"}
                            />
                            <Link to={"/songs"} className={"btn w-100 btn-outline-secondary"}>
                                Songs
                            </Link>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default Home;