import './App.css';
import React, {Component} from "react";
import {BrowserRouter, Switch} from 'react-router-dom'
import Home from '../Design/Home/home';

import AlbumCatalogService from "../../repository/albumCatalogRepository";

import Artists from '../Artists/ArtistsList/artists';
import Albums from '../Albums/AlbumsList/albums';

import AlbumPublish from '../Albums/AlbumPublish/albumPublish';
import AlbumRaiseTier from '../Albums/AlbumRaiseTier/albumRaiseTier';

import Songs from '../Songs/SongList/songs';
import SongCreate from '../Songs/SongCreate/songCreate';
import SongPublish from '../Songs/SongPublish/songPublish';

import AlbumPublishingService from "../../repository/albumStreamingRepository";
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
                                                   component={Artists}/>
                                <NonProtectedRoute path={"/songs"} exact
                                                   songs={this.state.songs}
                                                   fetchSong={this.fetchSong}
                                                   component={Songs}/>
                                <NonProtectedRoute path={"/albums"} exact
                                                   albums={this.state.albums}
                                                   component={Albums}/>

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

                                <ProtectedRoute path={"/albums/publish"} exact
                                                songs={this.state.songs}
                                                genres={this.state.genres}
                                                tiers={this.state.tiers}
                                                selectedArtist={this.getCurrentArtist()}
                                                transactionFee={this.state.transactionFee}
                                                subscriptionFee={this.getSubscriptionFee}
                                                publishAlbum={this.publishAlbum}
                                                component={AlbumPublish}/>
                                <ProtectedRoute path={"/albums/raiseAlbumTier"} exact
                                                selectedArtist={this.getCurrentArtist()}
                                                publishedAlbums={this.state.publishedAlbums}
                                                tiers={this.state.tiers}
                                                raiseAlbumTier={this.raiseAlbumTier}
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

    publishAlbum = (cover, songIdList, albumTier, subscriptionFee, transactionFee) => {
        AlbumCatalogService.publishAlbum(cover, songIdList, albumTier, subscriptionFee, transactionFee)
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
