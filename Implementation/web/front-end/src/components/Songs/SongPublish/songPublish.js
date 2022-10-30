import React from 'react';
import {useHistory} from 'react-router-dom';

const PublishSong = (props) => {

    const History = useHistory();
    const [cover, updateCover] = React.useState(null);
    const [subscriptionFee, updateSubscriptionFee] = React.useState("");
    const [formData, updateFormData] = React.useState({
        songId: "",
        songTier: "",
    });

    const getSubscriptionFee = (tier) => {
        if (tier && tier !== "") {
            props.subscriptionFee(tier).then((data) => {
                const formattedFee = `${data.data.amount}.00 ${data.data.currency}`;
                updateSubscriptionFee(formattedFee);
            })
        }
    }

    const handleChange = (e) => {
        updateFormData({
            ...formData,
            [e.target.name]: e.target.value.trim()
        });

        if (e.target.name === "songTier") {
            getSubscriptionFee(e.target.value);
        }
    }

    const onFormSubmit = (e) => {
        e.preventDefault();
        const songId = formData.songId;
        const songTier = formData.songTier;

        if (songId && songTier && subscriptionFee && props.transactionFee) {
            props.publishSong(cover, songId, songTier, subscriptionFee, props.transactionFee);

            History.push("/checkout/success");
        }
    }

    return (
        <div className="container mm-4 my-5">
            <div className={"row mb-5"}>
                <div className={"col-md"}>
                    <h1 className="display-5">Share your new song with the public</h1>
                    <p className="text-muted">Make your new music known to the general audience by sharing it.</p>
                </div>
            </div>

            <div className={"row"}>
                <div className="col-md">
                    <form onSubmit={onFormSubmit}>
                        <div className="form-group">
                            <div className="form-group">
                                <label className="upload-drop-container">
                                    <span className="upload-drop-title">Song cover picture</span>
                                    <input type="file" id="songUpload" accept="image/png, image/jpeg" required
                                           onChange={(e) => updateCover(e.target.files[0])}/>
                                    <span
                                        className={"text-muted"}><b>png</b> and <b>jpeg</b> file formats accepted</span>
                                </label>
                            </div>
                            <br/>

                            <select onChange={handleChange} name="songId" className="form-control" required={true}>
                                <option className={"text-muted"} value={null} disabled={true} selected={true}>
                                    -- Choose a song --
                                </option>
                                {props.songs.map((term) => {
                                        if (term['creator'].id === props.selectedArtist.id
                                            && !term['album'] && !term['isPublished']) {
                                            return <option key={term.id} value={term.id}>{term.songName}</option>;
                                        }
                                    }
                                )}
                            </select>
                        </div>
                        <br/>

                        <div className="form-group">
                            <select onChange={handleChange} name="songTier" className="form-control" required={true}>
                                <option className={"text-muted"} value={null} disabled={true} selected={true}>
                                    -- Choose a tier --
                                </option>
                                {props.tiers.map((term) => {
                                        return <option key={term} value={term}>{term}</option>;
                                    }
                                )}
                            </select>
                        </div>
                        <br/>

                        <div className="form-group">
                            <span>
                                <input name="subscriptionFee" disabled={true}
                                       id="subscriptionFee" value={subscriptionFee}
                                       className="form-control disabled"/>&nbsp;
                            </span>
                            <span className={"text-muted"}>Subscription fee is based on the tier our platform offers for distribution</span>
                        </div>
                        <br/>

                        <div className="form-group">
                            <input name="transactionFee" disabled={true}
                                   id={"transactionFee"} value={props.transactionFee}
                                   className="form-control disabled"/>
                            <span className={"text-muted"}>Transaction fee is fixed and based on your location and country</span>
                        </div>
                        <br/>

                        <div className="form-group">
                            <input name="artistName" disabled={true}
                                   value={props.selectedArtist['artistPersonalInfo'].fullName}
                                   className="form-control disabled"/>
                        </div>
                        <br/>

                        <br/>

                        <button id="submit" type="submit" className="btn btn-dark w-100">Submit</button>
                        <br/>
                    </form>

                </div>
            </div>
        </div>
    );
};

export default PublishSong;