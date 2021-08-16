import './App.css';
import React, {Component} from "react";
import {BrowserRouter as Router, Redirect, Route} from 'react-router-dom'
import Header from '../Header/header';

import AlbumCatalogService from "../../repository/albumCatalogRepository";

import Artists from '../AlbumCatalog/Artists/ArtistsList/artists';
import ArtistRegister from '../AlbumCatalog/Artists/ArtistAuthenticate/artistRegister';
import Albums from '../AlbumCatalog/Albums/AlbumsList/albums';
import AlbumCreate from '../AlbumCatalog/Albums/AlbumCreate/createAlbum';
import AlbumPublish from '../AlbumCatalog/Albums/AlbumPublish/albumPublish';
import Songs from '../AlbumCatalog/Songs/SongsList/songs';
import SongCreate from '../AlbumCatalog/Songs/SongCreate/songCreate';

import ArtistLoginSong from '../AlbumCatalog/Artists/ArtistAuthenticate/Login/artistSongCreate';
import ArtistLoginAlbum from '../AlbumCatalog/Artists/ArtistAuthenticate/Login/artistAlbumCreate';
import ArtistLoginPublish from '../AlbumCatalog/Artists/ArtistAuthenticate/Login/artistAlbumPublish';

import AlbumPublishingService from "../../repository/albumPublishingRepository";
import MusicDistributors from '../AlbumPublishing/MusicDistributors/DistributorsList/musicDistributorsList';
import PublishedAlbums from '../AlbumPublishing/PublishedAlbums/PublishedAlbumsList/publishedAlbumsList';

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

            selectedArtist: {},

            musicDistributors: [],
            publishedAlbums: [],
            albumTiers: []
        }

    }

    render() {
        return (
            <Router>
                <Header/>
                <main>

                    <div className="container">

                        {/* Album Publishing App */}

                        <Route path={"/distributors"} exact render={() =>
                            <MusicDistributors musicDistributors={this.state.musicDistributors}/>}/>

                        <Route path={"/publishedAlbums"} exact render={() =>
                            <PublishedAlbums publishedAlbums={this.state.publishedAlbums}/>}/>

                        {/* Album Catalog App */}

                        <Route path={"/artists"} exact render={() =>
                            <Artists artists={this.state.artists}/>}/>
                        <Route path={"/artists/login/song"} exact render={() =>
                            <ArtistLoginSong emailDomains={this.state.emailDomains}
                                             loginArtistSongs={this.loginArtistSongs}/>}/>
                        <Route path={"/artists/login/album"} exact render={() =>
                            <ArtistLoginAlbum emailDomains={this.state.emailDomains}
                                              loginArtistAlbums={this.loginArtistAlbums}/>}/>
                        <Route path={"/artists/login/publish"} exact render={() =>
                            <ArtistLoginPublish emailDomains={this.state.emailDomains}
                                                loginArtistPublish={this.loginArtistPublish}/>}/>
                        <Route path={"/artists/register"} exact render={() =>
                            <ArtistRegister emailDomains={this.state.emailDomains}
                                            registerArtist={this.registerArtist}/>}/>

                        <Route path={"/songs"} exact render={() =>
                            <Songs songs={this.state.songs}/>}/>
                        <Route path={"/songs/create"} exact render={() =>
                            <SongCreate albums={this.state.albums}
                                        selectedArtist={this.state.selectedArtist}
                                        createSong={this.createSong}/>}/>

                        <Route path={"/albums"} exact render={() =>
                            <Albums albums={this.state.albums}/>}/>
                        <Route path={"/albums/create"} exact render={() =>
                            <AlbumCreate genres={this.state.genres}
                                         selectedArtist={this.state.selectedArtist}
                                         createAlbum={this.createAlbum}/>}/>

                        {/* Album Publish */}

                        <Route path={"/albums/publish"} exact render={() =>
                            <AlbumPublish albums={this.state.albums}
                                          selectedArtist={this.state.selectedArtist}
                                          albumTiers={this.state.albumTiers}
                                          musicDistributors={this.state.musicDistributors}
                                          publishAlbum={this.publishAlbum}/>}/>

                        {/* Default */}

                        <Redirect to={"/albums"}/>
                    </div>
                </main>
            </Router>
        );
    }

    componentDidMount() {

        // album catalog
        this.loadArtists();
        this.loadAlbums();
        this.loadSongs();
        this.loadEmailDomains();
        this.loadGenres();

        // album publishing
        this.loadDistributors();
        this.loadPublishedAlbums();
        this.loadAlbumTiers();
    }

    loadDistributors = () => {
        AlbumPublishingService.fetchPublishedAlbums()
            .then((data) => {
                this.setState({
                    publishedAlbums: data.data
                })
            });
    }

    loadPublishedAlbums = () => {
        AlbumPublishingService.fetchDistributors()
            .then((data) => {
                this.setState({
                    musicDistributors: data.data
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

    publishAlbum = (albumId, artistId, musicPublisherId, albumTier, subscriptionFee, transactionFee) => {
        AlbumCatalogService.publishAlbum(albumId, artistId, musicPublisherId, albumTier, subscriptionFee, transactionFee)
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

    loginArtistPublish = async (username, domainName, password) => {
        await AlbumCatalogService.loginArtist(username, domainName, password)
            .then((data) => {
                this.setState({
                    selectedArtist: data.data
                });
            });

        return Object.keys(this.state.selectedArtist).length !== 0;
    }

    loginArtistSongs = async (username, domainName, password) => {
        await AlbumCatalogService.loginArtist(username, domainName, password)
            .then((data) => {
                this.setState({
                    selectedArtist: data.data
                });
            });

        return Object.keys(this.state.selectedArtist).length !== 0;
    }

    loginArtistAlbums = async (username, domainName, password) => {
        await AlbumCatalogService.loginArtist(username, domainName, password)
            .then((data) => {
                this.setState({
                    selectedArtist: data.data
                });
            });

        return Object.keys(this.state.selectedArtist).length !== 0;
    }


    registerArtist = (username, emailDomain, telephoneNumber, firstName, lastName, artName, password) => {
        AlbumCatalogService.registerArtist(username, emailDomain, telephoneNumber, firstName, lastName, artName, password)
            .then((data) => {
                this.loadAlbums();
            });
    }

}

export default App;
