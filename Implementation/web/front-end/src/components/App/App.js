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
import AlbumUnpublish from '../Albums/AlbumUnpublish/albumUnpublish';
import AlbumRaiseTier from '../Albums/AlbumRaiseTier/albumRaiseTier';

import Songs from '../Songs/SongsList/songs';
import SongCreate from '../Songs/SongCreate/songCreate';

import AlbumPublishingService from "../../repository/albumStreamingRepository";
import Footer from "../Footer/footer";
import Register from "../Authentication/register";
import Login from "../Authentication/login";

class App extends Component {

    constructor(props) {
        super(props);
        this.state = {
            albums: [],
            artists: [],
            songs: [],
            genres: [],
            emailDomains: [],
            selectedAlbum: {},

            musicDistributors: [],
            publishedAlbums: [],
            albumTiers: []
        }
    }

    resetNavbarItems(itemToCompare) {
        Array.from(document.getElementsByClassName("nav-item dropdown")).forEach((item) => {
            if (itemToCompare && item.isEqualNode(itemToCompare)) {
                return;
            }
            item.classList.remove('show');
            const itemDropdownMenu = item.querySelector(".dropdown-menu");
            if (itemDropdownMenu && itemDropdownMenu instanceof HTMLElement) {
                itemDropdownMenu.classList.remove('show');
            }
        });
    }

    setNavbarMobileMode() {
        const togglerElement = document.querySelector('.navbar-toggler');
        if (togglerElement && togglerElement instanceof HTMLElement) {
            togglerElement.addEventListener('click', (e) => {
                if (togglerElement && togglerElement instanceof HTMLElement) {
                    togglerElement.classList.toggle('collapsed');
                    const toggleNavContainer = togglerElement.parentElement.querySelector('.navbar-collapse.collapse');
                    if (toggleNavContainer && toggleNavContainer instanceof HTMLElement) {
                        toggleNavContainer.classList.toggle('show');
                    }
                }
            });
        }

        const container = document.body;
        const outsideNavContainer = container.querySelector('#authContainerOutside');
        const insideNavContainer = container.querySelector('#authContainerInside');
        if (outsideNavContainer && outsideNavContainer instanceof HTMLElement
            && insideNavContainer && insideNavContainer instanceof HTMLElement
            && container && container instanceof HTMLElement) {
            window.addEventListener('resize', () => {
                if (container.clientWidth < 992) {
                    outsideNavContainer.hidden = true;
                    insideNavContainer.hidden = false;
                } else {
                    outsideNavContainer.hidden = false;
                    insideNavContainer.hidden = true;
                }
            });
        }
    }

    toggleNavbarItems() {
        document.addEventListener('click', (e) => {
            const clickedElement = e.target;
            if (clickedElement && clickedElement instanceof HTMLElement
                && !clickedElement.classList.contains('dropdown-toggle')) {
                this.resetNavbarItems();
            }
        });
        Array.from(document.getElementsByClassName("nav-item dropdown")).forEach((item) => {
            item.addEventListener('click', () => {
                this.resetNavbarItems(item);
                item.classList.toggle('show');
                const itemDropdownMenu = item.querySelector(".dropdown-menu");
                if (itemDropdownMenu && itemDropdownMenu instanceof HTMLElement) {
                    itemDropdownMenu.classList.toggle('show');
                }
            });
        });
        this.setNavbarMobileMode();
    }

    getCurrentArtist() {
        return JSON.parse(localStorage.getItem('loggedArtist'));
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
                            <Songs songs={this.state.songs}/>}/>
                        <Route path={"/songs/create"} exact render={() =>
                            <SongCreate albums={this.state.albums}
                                        selectedArtist={this.getCurrentArtist()}
                                        createSong={this.createSong}/>}/>

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
                                          albumTiers={this.state.albumTiers}
                                          musicDistributors={this.state.musicDistributors}
                                          publishAlbum={this.publishAlbum}/>}/>
                        <Route path={"/albums/unPublish"} exact render={() =>
                            <AlbumUnpublish selectedArtist={this.getCurrentArtist()}
                                            publishedAlbums={this.state.publishedAlbums}
                                            unPublishAlbum={this.unPublishAlbum}/>}/>
                        <Route path={"/albums/raiseAlbumTier"} exact render={() =>
                            <AlbumRaiseTier selectedArtist={this.getCurrentArtist()}
                                            publishedAlbums={this.state.publishedAlbums}
                                            albumTiers={this.state.albumTiers}
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
        this.loadArtists();
        this.loadAlbums();
        this.loadSongs();
        this.loadEmailDomains();
        this.loadGenres();

        this.loadDistributors();
        this.loadPublishedAlbums();
        this.loadAlbumTiers();

        this.toggleNavbarItems();
    }

    loadDistributors = () => {
        AlbumPublishingService.fetchDistributors()
            .then((data) => {
                this.setState({
                    musicDistributors: data.data
                })
            });
    }

    loadPublishedAlbums = () => {
        AlbumPublishingService.fetchPublishedAlbums()
            .then((data) => {
                this.setState({
                    publishedAlbums: data.data
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

    loadAlbumTiers = () => {
        AlbumPublishingService.fetchAlbumTiers()
            .then((data) => {
                this.setState({
                    albumTiers: data.data
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

    unPublishAlbum = (publishedAlbumId) => {
        AlbumPublishingService.unPublishAlbum(publishedAlbumId)
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

    createSong = (songName, lengthInSeconds, creatorId, albumId) => {
        AlbumCatalogService.createSong(songName, lengthInSeconds, creatorId, albumId)
            .then(() => {
                this.loadSongs();
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


    registerArtist = (username, emailDomain, telephoneNumber, firstName, lastName, artName, password) => {
        AlbumCatalogService.registerArtist(username, emailDomain, telephoneNumber, firstName, lastName, artName, password)
            .then(() => {
                this.loadAlbums();
            });
    }

    logoutArtist = () => {
        AlbumCatalogService.logoutArtist();
        window.location.reload();
    }
}

export default App;
