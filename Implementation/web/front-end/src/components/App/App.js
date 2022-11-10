import './App.css';
import React, {Component} from "react";
import {BrowserRouter, Switch} from 'react-router-dom'
import Home from '../Design/Home/home';

import AlbumCatalogService from "../../repository/musicStorageRepository";

import Artists from '../Artists/ArtistsList/artists';
import Albums from '../Albums/AlbumsList/albums';

import AlbumPublish from '../Albums/AlbumPublish/albumPublish';
import AlbumRaiseTier from '../Albums/AlbumRaiseTier/albumRaiseTier';

import Songs from '../Songs/SongList/songs';
import SongCreate from '../Songs/SongCreate/songCreate';
import SongPublish from '../Songs/SongPublish/songPublish';

import Register from "../Authentication/Register/register";
import Login from "../Authentication/Login/login";
import Header from "../Design/Header/header";
import Footer from "../Design/Footer/footer";
import SuccessfulCheckout from "../Design/Checkout/successful-checkout";
import ScreenElementsUtil from "../../util/screen-elements-util";
import {ProtectedRoute} from "../Design/Route/Protected/protectedRoute";
import Unauthorized from "../Design/Error/Unauthorized/unauthorized";
import NotFound from "../Design/Error/NotFound/notFound";
import NonProtectedRoute from "../Design/Route/NonProtected/nonProtectedRoute";
import SongRaiseTier from "../Songs/SongRaiseTier/songRaiseTier";
import {DEFAULT_PAGE_NUMBER, DEFAULT_PAGE_SIZE} from "../../constants/pagination";

class App extends Component {

    constructor(props) {
        super(props);
        this.state = {
            albums: [],
            artists: [],
            songs: [],

            selectedAlbum: {},

            genres: [],
            emailDomains: [],
            tiers: [],

            transactionFee: {}
        }
    }

    render() {
        return (
            <div className={"app"}>
                <BrowserRouter>
                    <Header logoutArtist={this.logoutArtist}/>
                    <main id={"main"}>
                        <div className="container">
                            <Switch>
                                {/* Home */}

                                <NonProtectedRoute path={["/index", "/home", "/"]} exact component={Home}/>

                                {/* Authentication */}

                                <NonProtectedRoute path={"/register"} exact
                                                   emailDomains={this.state.emailDomains}
                                                   registerArtist={this.registerArtist}
                                                   component={Register}/>
                                <NonProtectedRoute path={"/login"} exact
                                                   emailDomains={this.state.emailDomains}
                                                   loginArtist={this.loginArtist}
                                                   component={Login}/>

                                {/* Main */}

                                <NonProtectedRoute path={"/artists"} exact
                                                   artists={this.state.artists}
                                                   loadArtists={this.loadArtists}
                                                   component={Artists}/>

                                <NonProtectedRoute path={"/songs"} exact
                                                   songs={this.state.songs}
                                                   filterSongs={this.filterSongs}
                                                   fetchSong={this.fetchSong}
                                                   loadSongs={this.loadSongs}
                                                   component={Songs}/>
                                <ProtectedRoute path={"/songs/create"} exact albums={this.state.albums}
                                                genres={this.state.genres}
                                                selectedArtist={this.getCurrentArtist()}
                                                createSong={this.createSong}
                                                component={SongCreate}/>
                                <ProtectedRoute path={"/songs/publish"}
                                                songs={this.state.songs}
                                                tiers={this.state.tiers}
                                                selectedArtist={this.getCurrentArtist()}
                                                transactionFee={this.state.transactionFee}
                                                subscriptionFee={this.getSubscriptionFee}
                                                publishSong={this.publishSong}
                                                component={SongPublish}/>
                                <ProtectedRoute path={"/songs/raise-tier"}
                                                songs={this.state.songs}
                                                tiers={this.state.tiers}
                                                transactionFee={this.state.transactionFee}
                                                subscriptionFee={this.getSubscriptionFee}
                                                raiseTierSong={this.raiseTierSong}
                                                component={SongRaiseTier}/>

                                <NonProtectedRoute path={"/albums"} exact
                                                   albums={this.state.albums}
                                                   filterAlbums={this.filterAlbums}
                                                   loadAlbums={this.loadAlbums}
                                                   component={Albums}/>
                                <ProtectedRoute path={"/albums/publish"} exact
                                                songs={this.state.songs}
                                                genres={this.state.genres}
                                                tiers={this.state.tiers}
                                                selectedArtist={this.getCurrentArtist()}
                                                transactionFee={this.state.transactionFee}
                                                subscriptionFee={this.getSubscriptionFee}
                                                publishAlbum={this.publishAlbum}
                                                component={AlbumPublish}/>
                                <ProtectedRoute path={"/albums/raise-tier"} exact
                                                albums={this.state.albums}
                                                tiers={this.state.tiers}
                                                transactionFee={this.state.transactionFee}
                                                subscriptionFee={this.getSubscriptionFee}
                                                raiseTierAlbum={this.raiseTierAlbum}
                                                component={AlbumRaiseTier}/>

                                <ProtectedRoute path={"/checkout/success"} exact component={SuccessfulCheckout}/>

                                {/* Default */}

                                <NonProtectedRoute path={"/unauthorized"} exact component={Unauthorized}/>
                                <NonProtectedRoute path={"*"} exact component={NotFound}/>
                            </Switch>
                        </div>
                    </main>
                    <Footer/>
                </BrowserRouter>
            </div>
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
        if (this.getCurrentArtist()) {
            AlbumCatalogService.fetchGenres()
                .then((data) => {
                    this.setState({
                        genres: data.data
                    })
                });
        }
    }

    loadTiers = () => {
        if (this.getCurrentArtist()) {
            AlbumCatalogService.fetchTiers()
                .then((data) => {
                    this.setState({
                        tiers: data.data
                    })
                });
        }
    }

    loadArtists = (pageNumber) => {
        AlbumCatalogService.fetchArtists(pageNumber)
            .then((data) => {
                this.setState({
                    artists: data.data,
                })
            });
    }

    loadAlbums = (pageNumber) => {
        AlbumCatalogService.fetchAlbums(pageNumber)
            .then((data) => {
                this.setState({
                    albums: data.data,
                })
            });
    }

    loadSongs = (pageNumber) => {
        AlbumCatalogService.fetchSongs(pageNumber)
            .then((data) => {
                this.setState({
                    songs: data.data,
                })
            });
    }

    filterAlbums = (key, value) => {
        AlbumCatalogService.filterAlbums(key, value)
            .then((data) => {
                this.setState({
                    albums: data.data,
                })
            });
    }

    filterSongs = (key, value) => {
        AlbumCatalogService.filterSongs(key, value)
            .then((data) => {
                this.setState({
                    songs: data.data,
                })
            });
    }

    fetchSong = (id) => {
        return AlbumCatalogService.fetchSong(id);
    }

    createSong = (file, songName, lengthInSeconds, songGenre) => {
        AlbumCatalogService.createSong(file, songName, lengthInSeconds, songGenre)
            .then(() => {
                this.loadSongs(this.state.songs['pageable'].pageNumber);
            });
    }

    publishSong = (cover, songId, songTier, subscriptionFee, transactionFee) => {
        AlbumCatalogService.publishSong(cover, songId, songTier, subscriptionFee, transactionFee)
            .then(() => {
                this.loadSongs(this.state.songs['pageable'].pageNumber);
            })
    }

    raiseTierSong = (songId, songTier, subscriptionFee, transactionFee) => {
        AlbumCatalogService.raiseTierSong(songId, songTier, subscriptionFee, transactionFee)
            .then(() => {
                this.loadSongs(this.state.songs['pageable'].pageNumber);
            });
    }

    publishAlbum = (cover, songIdList,
                    albumName, albumGenre, albumTier,
                    subscriptionFee, transactionFee,
                    artistName, producerName, composerName) => {
        AlbumCatalogService.publishAlbum(cover, songIdList,
            albumName, albumGenre, albumTier,
            subscriptionFee, transactionFee,
            artistName, producerName, composerName)
            .then(() => {
                this.loadAlbums(this.state.albums['pageable'].pageNumber);
            });
    }

    raiseTierAlbum = (albumId, albumTier, subscriptionFee, transactionFee) => {
        AlbumCatalogService.raiseTierAlbum(albumId, albumTier, subscriptionFee, transactionFee)
            .then(() => {
                this.loadAlbums(this.state.albums['pageable'].pageNumber);
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
                this.loadArtists(this.state.artists['pageable'].pageNumber);
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
        if (this.getCurrentArtist()) {
            const locale = navigator.language;
            AlbumCatalogService.getTransactionFee(locale).then((data) => {
                this.state.transactionFee = data.data;
            });
        }
    }

    getSubscriptionFee = (tier) => {
        return AlbumCatalogService.getSubscriptionFee(tier);
    }
}

export default App;