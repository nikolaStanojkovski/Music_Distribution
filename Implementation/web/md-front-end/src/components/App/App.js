import './App.css';
import React, {Component} from "react";
import {BrowserRouter, Switch} from 'react-router-dom'
import Home from '../Home/home';

import Artists from '../Artists/ArtistList/artists';
import Albums from '../Albums/AlbumList/albums';

import AlbumPublish from '../Albums/AlbumPublish/albumPublish';
import AlbumRaiseTier from '../Albums/AlbumRaiseTier/albumRaiseTier';

import Songs from '../Songs/SongList/songs';
import SongCreate from '../Songs/SongCreate/songCreate';
import SongPublish from '../Songs/SongPublish/songPublish';

import Register from "../Authentication/Register/register";
import Login from "../Authentication/Login/login";
import Header from "../Partial/Header/header";
import Footer from "../Partial/Footer/footer";
import SuccessfulCheckout from "../Checkout/successfulCheckout";
import ScreenElementsUtil from "../../util/screenElementsUtil";
import {ProtectedRoute} from "../Partial/Route/Protected/protectedRoute";
import Unauthorized from "../Partial/Error/Unauthorized/unauthorized";
import NotFound from "../Partial/Error/NotFound/notFound";
import NonProtectedRoute from "../Partial/Route/NonProtected/nonProtectedRoute";
import SongRaiseTier from "../Songs/SongRaiseTier/songRaiseTier";
import AlbumRepository from "../../repository/streaming-service/albumRepository";
import EmailDomainRepository from "../../repository/streaming-service/enum/emailDomainRepository";
import GenreRepository from "../../repository/streaming-service/enum/genreRepository";
import TierRepository from "../../repository/streaming-service/enum/tierRepository";
import ArtistRepository from "../../repository/streaming-service/artistRepository";
import SongRepository from "../../repository/streaming-service/songRepository";
import PaymentRepository from "../../repository/streaming-service/paymentRepository";
import AuthRepository from "../../repository/streaming-service/authRepository";

class App extends Component {

    constructor(props) {
        super(props);
        this.state = {
            albums: [],
            artists: [],
            songs: [],

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
                                                selectedArtist={ArtistRepository.fetchArtistLocal()}
                                                createSong={this.createSong}
                                                component={SongCreate}/>
                                <ProtectedRoute path={"/songs/publish"}
                                                songs={this.state.songs}
                                                tiers={this.state.tiers}
                                                selectedArtist={ArtistRepository.fetchArtistLocal()}
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
                                                selectedArtist={ArtistRepository.fetchArtistLocal()}
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
        EmailDomainRepository.fetchEmailDomains()
            .then((data) => {
                this.setState({
                    emailDomains: data.data
                })
            });
    }

    loadGenres = () => {
        if (ArtistRepository.fetchArtistLocal()) {
            GenreRepository.fetchGenres()
                .then((data) => {
                    this.setState({
                        genres: data.data
                    })
                });
        }
    }

    loadTiers = () => {
        if (ArtistRepository.fetchArtistLocal()) {
            TierRepository.fetchTiers()
                .then((data) => {
                    this.setState({
                        tiers: data.data
                    })
                });
        }
    }

    loadArtists = (pageNumber) => {
        ArtistRepository.fetchArtists(pageNumber)
            .then((data) => {
                this.setState({
                    artists: data.data,
                })
            });
    }

    loadAlbums = (pageNumber) => {
        AlbumRepository.fetchAlbums(pageNumber)
            .then((data) => {
                this.setState({
                    albums: data.data,
                })
            });
    }

    loadSongs = (pageNumber) => {
        SongRepository.fetchSongs(pageNumber)
            .then((data) => {
                this.setState({
                    songs: data.data,
                })
            });
    }

    filterAlbums = (pageNumber, key, value) => {
        AlbumRepository.filterAlbums(pageNumber, key, value)
            .then((data) => {
                this.setState({
                    albums: data.data,
                })
            });
    }

    filterSongs = (pageNumber, key, value) => {
        SongRepository.filterSongs(pageNumber, key, value)
            .then((data) => {
                this.setState({
                    songs: data.data,
                })
            });
    }

    fetchSong = (id) => {
        return SongRepository.fetchSong(id);
    }

    createSong = (file, songName, lengthInSeconds, songGenre) => {
        SongRepository.createSong(file, songName, lengthInSeconds, songGenre)
            .then(() => {
                this.loadSongs(this.state.songs['pageable'].pageNumber);
            });
    }

    publishSong = (cover, songId, songTier, subscriptionFee, transactionFee) => {
        SongRepository.publishSong(cover, songId, songTier, subscriptionFee, transactionFee)
            .then(() => {
                this.loadSongs(this.state.songs['pageable'].pageNumber);
            })
    }

    raiseTierSong = (songId, songTier, subscriptionFee, transactionFee) => {
        SongRepository.raiseTierSong(songId, songTier, subscriptionFee, transactionFee)
            .then(() => {
                this.loadSongs(this.state.songs['pageable'].pageNumber);
            });
    }

    publishAlbum = (cover, songIdList,
                    albumName, albumGenre, albumTier,
                    subscriptionFee, transactionFee,
                    artistName, producerName, composerName) => {
        AlbumRepository.publishAlbum(cover, songIdList,
            albumName, albumGenre, albumTier,
            subscriptionFee, transactionFee,
            artistName, producerName, composerName)
            .then(() => {
                this.loadAlbums(this.state.albums['pageable'].pageNumber);
            });
    }

    raiseTierAlbum = (albumId, albumTier, subscriptionFee, transactionFee) => {
        AlbumRepository.raiseTierAlbum(albumId, albumTier, subscriptionFee, transactionFee)
            .then(() => {
                this.loadAlbums(this.state.albums['pageable'].pageNumber);
            });
    }

    loginArtist = async (username, domainName, password) => {
        await AuthRepository.loginArtist(username, domainName, password)
            .then((data) => {
                localStorage.setItem('loggedArtist', JSON.stringify(data.data['artistResponse']));
                localStorage.setItem('accessToken', data.data['jwtToken']);
            });

        window.location.reload();
    }


    registerArtist = (profilePicture, username, emailDomain, telephoneNumber, firstName, lastName, artName, password) => {
        AuthRepository.registerArtist(profilePicture, username, emailDomain, telephoneNumber, firstName, lastName, artName, password)
            .then(() => {
                this.loadArtists(this.state.artists['pageable'].pageNumber);
            });
    }

    logoutArtist = () => {
        AuthRepository.logoutArtist();
        const baseUrl = window.location.origin;
        window.location.replace(baseUrl);
    }

    getTransactionFee = () => {
        if (ArtistRepository.fetchArtistLocal()) {
            const locale = navigator.language;
            PaymentRepository.getTransactionFee(locale).then((data) => {
                this.state.transactionFee = data.data;
            });
        }
    }

    getSubscriptionFee = (tier) => {
        return PaymentRepository.getSubscriptionFee(tier);
    }
}

export default App;