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
import SuccessfulCheckout from "../components/Partial/Checkout/successfulCheckout";
import ScreenElementsUtil from "../util/screenElementsUtil";
import {ProtectedRoute} from "../components/Partial/Route/Protected/protectedRoute";
import Unauthorized from "../components/Partial/Error/Unauthorized/unauthorized";
import NotFound from "../components/Partial/Error/NotFound/notFound";
import NonProtectedRoute from "../components/Partial/Route/NonProtected/nonProtectedRoute";
import SongRaiseTier from "../components/Songs/SongRaiseTier/songRaiseTier";
import ArtistRepository from "../repository/streaming-service/artistRepository";
import useAlbumService from "../service/albumService";
import useArtistService from "../service/artistService";
import useSongService from "../service/songService";
import usePaymentService from "../service/paymentService";
import useEmailDomainService from "../service/enum/emailDomainService";
import useTierService from "../service/enum/tierService";
import useGenreService from "../service/enum/genreService";

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

                            <NonProtectedRoute path={["/index", "/home", "/"]} exact component={Home}/>

                            {/* Authentication */}

                            <NonProtectedRoute path={"/register"} exact
                                               emailDomains={emailDomainService.emailDomains}
                                               registerArtist={artistService.registerArtist}
                                               component={Register}/>
                            <NonProtectedRoute path={"/login"} exact
                                               emailDomains={emailDomainService.emailDomains}
                                               loginArtist={artistService.loginArtist}
                                               component={Login}/>

                            {/* Main */}

                            <NonProtectedRoute path={"/artists"} exact
                                               artists={artistService.artists}
                                               loadArtists={artistService.loadArtists}
                                               component={Artists}/>

                            <NonProtectedRoute path={"/songs"} exact
                                               songs={songService.songs}
                                               filterSongs={songService.filterSongs}
                                               fetchSong={songService.fetchSong}
                                               loadSongs={songService.loadSongs}
                                               component={Songs}/>
                            <ProtectedRoute path={"/songs/create"} exact albums={albumService.albums}
                                            genres={genreService.genres}
                                            selectedArtist={ArtistRepository.fetchArtistLocal()}
                                            createSong={songService.createSong}
                                            component={SongCreate}/>
                            <ProtectedRoute path={"/songs/publish"}
                                            songs={songService.songs}
                                            tiers={tierService.tiers}
                                            selectedArtist={ArtistRepository.fetchArtistLocal()}
                                            transactionFee={paymentService.transactionFee}
                                            subscriptionFee={paymentService.getSubscriptionFee}
                                            publishSong={songService.publishSong}
                                            component={SongPublish}/>
                            <ProtectedRoute path={"/songs/raise-tier"}
                                            songs={songService.songs}
                                            tiers={tierService.tiers}
                                            transactionFee={paymentService.transactionFee}
                                            subscriptionFee={paymentService.getSubscriptionFee}
                                            raiseTierSong={songService.raiseTierSong}
                                            component={SongRaiseTier}/>

                            <NonProtectedRoute path={"/albums"} exact
                                               albums={albumService.albums}
                                               filterAlbums={albumService.filterAlbums}
                                               loadAlbums={albumService.loadAlbums}
                                               component={Albums}/>
                            <ProtectedRoute path={"/albums/publish"} exact
                                            songs={songService.songs}
                                            genres={genreService.genres}
                                            tiers={tierService.tiers}
                                            selectedArtist={ArtistRepository.fetchArtistLocal()}
                                            transactionFee={paymentService.transactionFee}
                                            subscriptionFee={paymentService.getSubscriptionFee}
                                            publishAlbum={albumService.publishAlbum}
                                            component={AlbumPublish}/>
                            <ProtectedRoute path={"/albums/raise-tier"} exact
                                            albums={albumService.albums}
                                            tiers={tierService.tiers}
                                            transactionFee={paymentService.transactionFee}
                                            subscriptionFee={paymentService.getSubscriptionFee}
                                            raiseTierAlbum={albumService.raiseTierAlbum}
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

export default App;