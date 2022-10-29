import './App.css';
import React, {Component} from "react";
import {BrowserRouter as Router, Redirect, Route} from 'react-router-dom'
import Header from '../Header/header';
import Home from '../Home/home';

import AlbumCatalogService from "../../repository/albumCatalogRepository";

import Artists from '../Artists/ArtistsList/artists';
import Albums from '../Albums/AlbumsList/albums';
import AlbumCreate from '../Albums/AlbumCreate/createAlbum';

import AlbumPublish from '../Albums/AlbumPublish/albumPublish';
import AlbumRaiseTier from '../Albums/AlbumRaiseTier/albumRaiseTier';

import Songs from '../Songs/SongList/songs';
import SongCreate from '../Songs/SongCreate/songCreate';
import SongPublish from '../Songs/SongPublish/songPublish';

import AlbumPublishingService from "../../repository/albumStreamingRepository";
import Footer from "../Footer/footer";
import Register from "../Authentication/register";
import Login from "../Authentication/login";
import ScreenElementsUtil from "./screen-elements";

class App extends Component {

    constructor(props) {
        super(props);
        this.state = {
            albums: [],
            artists: [],
            songs: [],
            selectedAlbum: {},

            transactionFee: "",

            genres: [],
            emailDomains: [],
            tiers: []
        }
    }

    render() {
        return (
            <Router>
                <Header logoutArtist={this.logoutArtist}/>
                <main id={"main"}>
                    <div className="container">

                        {/*Home Page / Index Page*/}

                        <Route path={["/index", "home", "/"]} exact render={() =>
                            <Home/>}/>

                        {/* Authentication */}

                        <Route path={"/register"} exact render={() =>
                            <Register emailDomains={this.state.emailDomains}
                                      registerArtist={this.registerArtist}/>}/>
                        <Route path={"/login"} exact render={() =>
                            <Login emailDomains={this.state.emailDomains}
                                   loginArtist={this.loginArtist}/>}/>

                        {/* Album Catalog App */}

                        <Route path={"/artists"} exact render={() =>
                            <Artists artists={this.state.artists}/>}/>

                        <Route path={"/songs"} exact render={() =>
                            <Songs songs={this.state.songs} fetchSong={this.fetchSong}/>}/>
                        <Route path={"/songs/create"} exact render={() =>
                            <SongCreate albums={this.state.albums}
                                        genres={this.state.genres}
                                        selectedArtist={this.getCurrentArtist()}
                                        createSong={this.createSong}/>}/>
                        <Route path={"/songs/publish"} exact render={() =>
                            <SongPublish songs={this.state.songs}
                                         tiers={this.state.tiers}
                                         selectedArtist={this.getCurrentArtist()}
                                         transactionFee={this.state.transactionFee}
                                         subscriptionFee={this.getSubscriptionFee}
                                         publishSong={this.publishSong}/>}/>

                        <Route path={"/albums"} exact render={() =>
                            <Albums albums={this.state.albums}/>}/>
                        <Route path={"/albums/create"} exact render={() =>
                            <AlbumCreate genres={this.state.genres}
                                         selectedArtist={this.getCurrentArtist()}
                                         createAlbum={this.createAlbum}/>}/>

                        {/* Album Publish Events */}

                        <Route path={"/albums/publish"} exact render={() =>
                            <AlbumPublish albums={this.state.albums}
                                          selectedArtist={this.getCurrentArtist()}
                                          tiers={this.state.tiers}
                                          musicDistributors={this.state.musicDistributors}
                                          publishAlbum={this.publishAlbum}/>}/>
                        <Route path={"/albums/raiseAlbumTier"} exact render={() =>
                            <AlbumRaiseTier selectedArtist={this.getCurrentArtist()}
                                            publishedAlbums={this.state.publishedAlbums}
                                            tiers={this.state.tiers}
                                            raiseAlbumTier={this.raiseAlbumTier}/>}/>

                        {/* Default */}

                        <Redirect to={"/"}/>
                    </div>
                </main>
                <Footer/>
            </Router>
        );
    }

    componentDidMount() {
        this.getTransactionFee();

        this.loadArtists();
        this.loadAlbums();
        this.loadSongs();

        this.loadEmailDomains();
        this.loadGenres();
        this.loadTiers();

        ScreenElementsUtil.toggleNavbarItems();
    }

    loadEmailDomains = () => {
        AlbumCatalogService.fetchEmailDomains()
            .then((data) => {
                this.setState({
                    emailDomains: data.data
                })
            });
    }

    loadGenres = () => {
        AlbumCatalogService.fetchGenres()
            .then((data) => {
                this.setState({
                    genres: data.data
                })
            });
    }

    loadTiers = () => {
        AlbumCatalogService.fetchTiers()
            .then((data) => {
                this.setState({
                    tiers: data.data
                })
            });
    }

    loadAlbums = () => {
        AlbumCatalogService.fetchAlbums()
            .then((data) => {
                this.setState({
                    albums: data.data
                })
            });
    }

    loadSongs = () => {
        AlbumCatalogService.fetchSongs()
            .then((data) => {
                this.setState({
                    songs: data.data
                })
            });
    }

    fetchSong = (id) => {
        return AlbumCatalogService.fetchSong(id);
    }

    createSong = (file, songName, lengthInSeconds, songGenre) => {
        AlbumCatalogService.createSong(file, songName, lengthInSeconds, songGenre)
            .then(() => {
                this.loadSongs();
            });
    }

    publishSong = (cover, songId, songTier, subscriptionFee, transactionFee) => {
        AlbumCatalogService.publishSong(cover, songId, songTier, subscriptionFee, transactionFee)
            .then(() => {
                this.loadSongs();
            })
    }

    loadArtists = () => {
        AlbumCatalogService.fetchArtists()
            .then((data) => {
                this.setState({
                    artists: data.data
                })
            });
    }

    publishAlbum = (albumId, albumName, artistId, artistInformation, musicPublisherId, albumTier, subscriptionFee, transactionFee) => {
        AlbumPublishingService.publishAlbum(albumId, albumName, artistId, artistInformation, musicPublisherId, albumTier, subscriptionFee, transactionFee)
            .then(() => {
                this.loadAlbums();
            });
    }

    raiseAlbumTier = (publishedAlbumId, albumTier, subscriptionFee, transactionFee) => {
        AlbumPublishingService.raiseAlbumTier(publishedAlbumId, albumTier, subscriptionFee, transactionFee)
            .then(() => {
                this.loadAlbums();
            });
    }



    createAlbum = (albumName, genre, totalLength, isPublished, artistName, producerName, composerName, creatorId) => {
        AlbumCatalogService.createAlbum(albumName, genre, totalLength, isPublished, artistName, producerName, composerName, creatorId)
            .then(() => {
                this.loadAlbums();
            });
    }

    loginArtist = async (username, domainName, password) => {
        await AlbumCatalogService.loginArtist(username, domainName, password)
            .then((data) => {
                localStorage.setItem('loggedArtist', JSON.stringify(data.data['artistResponse']));
                localStorage.setItem('accessToken', data.data['jwtToken']);
            });

        window.location.reload();
    }


    registerArtist = (profilePicture, username, emailDomain, telephoneNumber, firstName, lastName, artName, password) => {
        AlbumCatalogService.registerArtist(profilePicture, username, emailDomain, telephoneNumber, firstName, lastName, artName, password)
            .then(() => {
                this.loadArtists();
            });
    }

    logoutArtist = () => {
        AlbumCatalogService.logoutArtist();
        window.location.reload();
    }

    getCurrentArtist() {
        return JSON.parse(localStorage.getItem('loggedArtist'));
    }

    getTransactionFee = () => {
        const locale = navigator.language;
        AlbumCatalogService.getTransactionFee(locale).then((data) => {
            this.state.transactionFee = `${data.data.amount}.00 ${data.data.currency}`;
        });
    }

    getSubscriptionFee = (tier) => {
        return AlbumCatalogService.getSubscriptionFee(tier);
    }
}

export default App;
