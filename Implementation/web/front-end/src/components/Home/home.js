import React from 'react';
import Carousel from "react-bootstrap/Carousel";
import logo from "../../assets/logo.png";
import logoSymbol from "../../assets/logo-symbol.png";
import albumsWallpaper from "../../assets/img/albums-wallpaper.jpg";
import songsWallpaper from "../../assets/img/songs-wallpaper.jpg";
import artistsWallpaper from "../../assets/img/artists-wallpaper.jpg";
import albumsTeaser from "../../assets/img/albums-teaser.jpg";
import artistsTeaser from "../../assets/img/artists-teaser.jpg";
import songsTeaser from "../../assets/img/songs-teaser.jpg";

const Home = () => {
    return (
        <div className={"container"}>

            <div className={"row"}>
                <div className="position-relative overflow-hidden pb-3 pt-5 text-center">
                    <img src={logo} alt={""} className="display-3 font-weight-normal" height={400} width={420} />
                </div>
            </div>

            <Carousel className={"my-5"}>
                <Carousel.Item>
                    <img className={"carousel-image d-block w-100"} src={albumsWallpaper} alt={"Albums Wallpaper"}/>
                    <Carousel.Caption>
                        <h3>Albums</h3>
                        <p>Upload your albums and distribute them to the appropriate audience.</p>
                    </Carousel.Caption>
                </Carousel.Item>
                <Carousel.Item>
                    <img className={"carousel-image d-block w-100"} src={songsWallpaper} alt={"Songs Wallpaper"}/>
                    <Carousel.Caption>
                        <h3>Songs</h3>
                        <p>Upload your favourite tracks and distribute them to the appropriate audience.</p>
                    </Carousel.Caption>
                </Carousel.Item>
                <Carousel.Item>
                    <img className={"carousel-image d-block w-100"} src={artistsWallpaper} alt={"Artists Wallpaper"}/>
                    <Carousel.Caption>
                        <h3>Artists</h3>
                        <p>View the registered artists and discover their music.</p>
                    </Carousel.Caption>
                </Carousel.Item>
            </Carousel>

            <div className={"row mt-5"}>
                <div className={"col-1"}></div>
                <div className={"col-3"}>
                    <div className="spin-animation position-relative overflow-hidden text-center">
                        <img src={logoSymbol} alt={"Site Logo"} className="display-3 font-weight-normal" height={220}
                             width={200}/>
                    </div>
                </div>
                <div className={"col-4"}>
                    <div className="spin-animation position-relative overflow-hidden text-center">
                        <img src={logoSymbol} alt={"Site Logo"} className="display-3 font-weight-normal mb-3"/>
                    </div>
                </div>
                <div className={"col-3"}>
                    <div className="spin-animation position-relative overflow-hidden text-center">
                        <img src={logoSymbol} alt={"Site Logo"} className="display-3 font-weight-normal" height={220}
                             width={200}/>
                    </div>
                </div>
                <div className={"col-1"}></div>
            </div>
            <div className={"row mb-5"}></div>

        </div>
    )
}

export default Home;