import React from 'react';
import {Link, useHistory} from 'react-router-dom';

const PublishAlbum = (props) => {

    const History = useHistory();
    const [formData, updateFormData] = React.useState({
        albumId: 0,
        musicPublisherId: 0,
        albumTier: 0
    });

    const getAlbumTier = (inputValue) => {
        switch (inputValue) {
            case "Bronze":
                return "10.00 EUR";
            case "Silver":
                return "20.00 EUR";
            case "Gold":
                return "50.00 EUR";
            case "Platinum":
                return "100.00 EUR";
            case "Diamond":
                return "500.00 EUR";
            default:
                return "0.00 EUR";
        }
    }

    const handleChange = (e) => {
        updateFormData({
            ...formData,
            [e.target.name]: e.target.value.trim()
        });

        if (e.target.name === "albumTier") {
            let subFee = document.getElementById("subscriptionFee");

            subFee.value = getAlbumTier(e.target.value);
        }
    }

    const onFormSubmit = (e) => {
        e.preventDefault();
        const album = formData.albumId.split(" ");
        const albumId = album[0];
        let albumName = "";
        for (let i = 1; i < album.length; i++)
            albumName = albumName + " " + album[i];

        const artistId = props.selectedArtist.id;
        const artistInformation = props.selectedArtist.artistPersonalInfo.fullName;
        const musicPublisherId = formData.musicPublisherId;
        const albumTier = formData.albumTier;
        const subscriptionFee = document.getElementById("subscriptionFee").value.split(" ")[0];
        const transactionFee = 5.00;

        props.publishAlbum(albumId, albumName, artistId, artistInformation, musicPublisherId, albumTier, subscriptionFee, transactionFee);
        History.push("/albums");
    }

    return (
        <div className="container">
            <h1 className={"text-center mt-4"}>Publish an album</h1>
            <br/>
            <div className={""}>
                <div className="col-md-6">
                    <br/>
                    <form onSubmit={onFormSubmit}>
                        <div className="form-group">
                            <label>Album</label>
                            <select onChange={handleChange} name="albumId" className="form-control">
                                <option value={null}>Select the album</option>

                                {props.albums.map((term) => {
                                        if (term.artistId === props.selectedArtist.id && term.isPublished === false) {
                                            return <option value={term.id + " " + term.albumName}>{term.albumName}</option>;
                                        }
                                    }
                                )}
                            </select>
                        </div>
                        <br/>

                        <div className="form-group">
                            <label>Artist</label>
                            <input name="artistName" disabled={true}
                                   value={props.selectedArtist.artistPersonalInfo.fullName}
                                   className="form-control disabled"/>
                        </div>
                        <br/>

                        <div className="form-group">
                            <label>Music Publisher</label>
                            <select onChange={handleChange} name="musicPublisherId" className="form-control">
                                <option value={null}>Select the music distributor</option>
                                {props.musicDistributors.map((term) => {
                                        return <option value={term.id}>{term.distributorInfo}</option>;
                                    }
                                )}
                            </select>
                        </div>
                        <br/>

                        <div className="form-group">
                            <label>Album Tier</label>
                            <select onChange={handleChange} name="albumTier" className="form-control">
                                <option value={null}>Select the album tier</option>
                                {props.albumTiers.map((term) => {
                                        return <option value={term}>{term}</option>;
                                    }
                                )}
                            </select>
                        </div>
                        <br/>

                        <div className="form-group">
                            <label htmlFor={"subscriptionFee"}>Subscription fee</label>
                            <span>
                                <input name="subscriptionFee" disabled={true}
                                       id="subscriptionFee"
                                       className="form-control disabled"/>&nbsp;
                            </span>
                            <span className={"text-muted"}>Subscription fee is based on the album tier you choose</span>
                        </div>
                        <br/>

                        <div className="form-group">
                            <label>Transaction fee</label>
                            <input name="transactionFee" disabled={true}
                                   value={"5.00 EUR"}
                                   className="form-control disabled"/>
                            <span className={"text-muted"}>Transaction fee is fixed and based on your location</span>
                        </div>
                        <br/>

                        <button id="submit" type="submit" className="btn btn-primary">Submit</button>

                        <br/>
                    </form>

                    <Link type="button" className="btn btn-link" to={"/albums"}>Back to albums list</Link>
                </div>
            </div>
        </div>
    );
};

export default PublishAlbum;