import './App.css';
import React from "react";
import {BrowserRouter, Switch} from 'react-router-dom'
import Home from '../components/Partial/Home/home';

import Artists from '../components/Artists/ArtistList/artists';
import Albums from '../components/Albums/AlbumList/albums';

import AlbumPublish from '../components/Albums/AlbumPublish/albumPublish';
import AlbumRaiseTier from '../components/Albums/AlbumRaiseTier/albumRaiseTier';

import Songs from '../components/Songs/SongList/songs';
import SongCreate from '../components/Songs/SongCreate/songCreate';
import SongPublish from '../components/Songs/SongPublish/songPublish';

import Register from "../components/Authentication/Register/register";
import Login from "../components/Authentication/Login/login";
import Header from "../components/Partial/Header/header";
import Footer from "../components/Partial/Footer/footer";
import Checkout from "../components/Partial/Checkout/checkout";
import ScreenElementsUtil from "../util/screenElementsUtil";
import {ProtectedRoute} from "../components/Partial/Route/Protected/protectedRoute";
import Unauthorized from "../components/Partial/Error/Unauthorized/unauthorized";
import NotFound from "../components/Partial/Error/NotFound/notFound";
import NonProtectedRoute from "../components/Partial/Route/NonProtected/nonProtectedRoute";
import SongRaiseTier from "../components/Songs/SongRaiseTier/songRaiseTier";
import ArtistRepository from "../repository/streaming-service/artistRepository";
import useAlbumService from "../service/streaming-service/albumService";
import useArtistService from "../service/streaming-service/artistService";
import useSongService from "../service/streaming-service/songService";
import usePaymentService from "../service/streaming-service/paymentService";
import useEmailDomainService from "../service/streaming-service/enum/emailDomainService";
import useTierService from "../service/streaming-service/enum/tierService";
import useGenreService from "../service/streaming-service/enum/genreService";
import {
    ALBUMS,
    ALBUMS_PUBLISH,
    ALBUMS_RAISE_TIER,
    ARTISTS,
    CHECKOUT, DEFAULT,
    HOME,
    LOGIN,
    REGISTER,
    SONGS,
    SONGS_CREATE,
    SONGS_PUBLISH,
    SONGS_RAISE_TIER,
    UNAUTHORIZED
} from "../constants/endpoint";
import {ASTERISK} from "../constants/alphabet";

const App = () => {

    const albumService = useAlbumService();
    const artistService = useArtistService();
    const songService = useSongService();
    const paymentService = usePaymentService();

    const emailDomainService = useEmailDomainService();
    const tierService = useTierService();
    const genreService = useGenreService();

    React.useEffect(() => {
        ScreenElementsUtil.toggleNavbarItems();
    }, []);

    return (
        <div className={"app"}>
            <BrowserRouter>
                <Header logoutArtist={artistService.logoutArtist}/>
                <main id={"main"}>
                    <div className="container">
                        <Switch>
                            {/* Home */}

                            <NonProtectedRoute path={[HOME, DEFAULT]} exact component={Home}/>

                            {/* Authentication */}

                            <NonProtectedRoute path={REGISTER} exact
                                               emailDomains={emailDomainService.emailDomains}
                                               registerArtist={artistService.registerArtist}
                                               component={Register}/>
                            <NonProtectedRoute path={LOGIN} exact
                                               emailDomains={emailDomainService.emailDomains}
                                               loginArtist={artistService.loginArtist}
                                               component={Login}/>

                            {/* Main */}

                            <NonProtectedRoute path={ARTISTS} exact
                                               artists={artistService.artists}
                                               loadArtists={artistService.loadArtists}
                                               component={Artists}/>

                            <NonProtectedRoute path={SONGS} exact
                                               songs={songService.songs}
                                               filterSongs={songService.filterSongs}
                                               fetchSong={songService.fetchSong}
                                               loadSongs={songService.loadSongs}
                                               component={Songs}/>
                            <ProtectedRoute path={SONGS_CREATE} exact albums={albumService.albums}
                                            genres={genreService.genres}
                                            selectedArtist={ArtistRepository.fetchArtistLocal()}
                                            createSong={songService.createSong}
                                            component={SongCreate}/>
                            <ProtectedRoute path={SONGS_PUBLISH}
                                            songs={songService.songs}
                                            tiers={tierService.tiers}
                                            selectedArtist={ArtistRepository.fetchArtistLocal()}
                                            transactionFee={paymentService.transactionFee}
                                            subscriptionFee={paymentService.getSubscriptionFee}
                                            publishSong={songService.publishSong}
                                            component={SongPublish}/>
                            <ProtectedRoute path={SONGS_RAISE_TIER}
                                            songs={songService.songs}
                                            tiers={tierService.tiers}
                                            transactionFee={paymentService.transactionFee}
                                            subscriptionFee={paymentService.getSubscriptionFee}
                                            raiseTierSong={songService.raiseTierSong}
                                            component={SongRaiseTier}/>

                            <NonProtectedRoute path={ALBUMS} exact
                                               albums={albumService.albums}
                                               filterAlbums={albumService.filterAlbums}
                                               loadAlbums={albumService.loadAlbums}
                                               component={Albums}/>
                            <ProtectedRoute path={ALBUMS_PUBLISH} exact
                                            songs={songService.songs}
                                            genres={genreService.genres}
                                            tiers={tierService.tiers}
                                            selectedArtist={ArtistRepository.fetchArtistLocal()}
                                            transactionFee={paymentService.transactionFee}
                                            subscriptionFee={paymentService.getSubscriptionFee}
                                            publishAlbum={albumService.publishAlbum}
                                            component={AlbumPublish}/>
                            <ProtectedRoute path={ALBUMS_RAISE_TIER} exact
                                            albums={albumService.albums}
                                            tiers={tierService.tiers}
                                            transactionFee={paymentService.transactionFee}
                                            subscriptionFee={paymentService.getSubscriptionFee}
                                            raiseTierAlbum={albumService.raiseTierAlbum}
                                            component={AlbumRaiseTier}/>

                            <ProtectedRoute path={CHECKOUT} exact component={Checkout}/>

                            {/* Errors */}

                            <NonProtectedRoute path={UNAUTHORIZED} exact component={Unauthorized}/>
                            <NonProtectedRoute path={ASTERISK} exact component={NotFound}/>
                        </Switch>
                    </div>
                </main>
                <Footer/>
            </BrowserRouter>
        </div>
    );
}

export default App;