import React from 'react';
import Carousel from "react-bootstrap/Carousel";
import logo from "../../assets/logo.png";
import albumsWallpaper from "../../assets/img/albums-wallpaper.jpg";
import songsWallpaper from "../../assets/img/songs-wallpaper.jpg";
import artistsWallpaper from "../../assets/img/artists-wallpaper.jpg";
import {Link} from "react-router-dom";

const Home = () => {
    return (
        <div>
            <Carousel className={"carousel my-4"}>
                <Carousel.Item>
                    <img className={"carousel-image d-block w-100"} src={logo} alt={"Website Logo"}/>
                </Carousel.Item>
                <Carousel.Item>
                    <Link to={"/albums"}>
                        <img className={"carousel-image d-block w-100"} src={albumsWallpaper} alt={"Albums Wallpaper"}/>
                        <Carousel.Caption>
                            <h3>Albums</h3>
                            <p>Upload your albums and distribute them to the appropriate audience.</p>
                        </Carousel.Caption>
                    </Link>
                </Carousel.Item>
                <Carousel.Item>
                    <Link to={"/songs"}>
                        <img className={"carousel-image d-block w-100"} src={songsWallpaper} alt={"Songs Wallpaper"}/>
                        <Carousel.Caption>
                            <h3>Songs</h3>
                            <p>Upload your favourite tracks and distribute them to the appropriate audience.</p>
                        </Carousel.Caption>
                    </Link>
                </Carousel.Item>
                <Carousel.Item>
                    <Link to={"/artists"}>
                        <img className={"carousel-image d-block w-100"} src={artistsWallpaper}
                             alt={"Artists Wallpaper"}/>
                        <Carousel.Caption>
                            <h3>Artists</h3>
                            <p>View the registered artists and discover their music.</p>
                        </Carousel.Caption>
                    </Link>
                </Carousel.Item>
            </Carousel>
        </div>
    )
}

export default Home;