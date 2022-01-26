import React from 'react';
import {Link, useHistory} from 'react-router-dom';
import AlbumFormUtils from "../albumFormUtils";

const RaiseAlbumTier = (props) => {

    const History = useHistory();
    const [formData, updateFormData] = React.useState({
        albumId: 0,
        albumTier: 0
    });

    const handleChange = (e) => {
        updateFormData({
            ...formData,
            [e.target.name]: e.target.value.trim()
        });

        if (e.target.name === "albumTier") {
            let subFee = document.getElementById("subscriptionFee");

            subFee.value = AlbumFormUtils.getAlbumTier(e.target.value);
        }
    }

    const onFormSubmit = (e) => {
        e.preventDefault();

        const publishedAlbumId = formData.albumId;
        const albumTier = formData.albumTier;
        const subscriptionFee = document.getElementById("subscriptionFee").value.split(" ")[0];
        const transactionFee = 5.00;

        props.raiseAlbumTier(publishedAlbumId, albumTier, subscriptionFee, transactionFee);
        History.push("/albums");
    }

    return (
        <div className="container">
            <h1 className={"text-center mt-4"}>Raise album tier</h1>
            <br/>
            <div className={""}>
                <div className="col-md-6">
                    <br/>
                    <form onSubmit={onFormSubmit}>
                        <div className="form-group">
                            <label>Published Albums</label>
                            <select onChange={handleChange} name="albumId" className="form-control">
                                <option value={null}>Select the published album to raise the tier to</option>

                                {props.publishedAlbums.map((term) => {
                                        if (term.artistId === props.selectedArtist.id) {
                                            return <option value={term.publishedAlbumId}>{term.albumName}</option>;
                                        }
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

export default RaiseAlbumTier;