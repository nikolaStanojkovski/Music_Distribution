import React from 'react';
import {useHistory} from 'react-router-dom';
import Select from 'react-select';
import makeAnimated from 'react-select/animated';
import ScreenElementsUtil from "../../../util/screenElementsUtil";
import StringUtil from "../../../util/stringUtil";

const PublishAlbum = (props) => {

    const History = useHistory();
    const [cover, updateCover] = React.useState(null);
    const [subscriptionFee, updateSubscriptionFee] = React.useState({});
    const [songIdList, updateSongIdList] = React.useState([]);
    const [formData, updateFormData] = React.useState({
        albumName: "",
        albumGenre: "",
        albumTier: "",
        artistName: "",
        producerName: "",
        composerName: ""
    });

    const handleSubscriptionFee = (tier) => {
        props.subscriptionFee(tier).then((data) => {
            updateSubscriptionFee(data.data);
        });
    }

    const handleChange = (e) => {
        updateFormData({
            ...formData,
            [e.target.name]: e.target.value.trim()
        });

        if (e.target.name === "albumTier") {
            handleSubscriptionFee(e.target.value);
        }
    }

    const onFormSubmit = (e) => {
        e.preventDefault();

        const albumName = formData.albumName;
        const albumGenre = formData.albumGenre;
        const albumTier = formData.albumTier;

        if (songIdList && songIdList.length > 0
            && albumName && albumGenre && albumTier
            && subscriptionFee && props.transactionFee) {
            props.publishAlbum(cover, songIdList.map(song => song.value)
                    .filter(term => term !== undefined),
                albumName, albumGenre, albumTier,
                subscriptionFee, props.transactionFee,
                formData.artistName, formData.producerName, formData.composerName);

            History.push("/checkout/success");
        }
    }

    return (
        <div className="container mm-4 my-5">
            <div className={"row mb-5"}>
                <div className={"col-md"}>
                    <h1 className="display-5">Share your new album</h1>
                    <p className="text-muted">Inform your fans and listeners about the emotional experience you
                        documented.</p>
                </div>
            </div>
            <div className={"row"}>
                <div className="col-md">
                    <form onSubmit={onFormSubmit}>
                        <div className="form-group">
                            <label className="upload-drop-container">
                                <span className="upload-drop-title">Album cover picture</span>
                                <input type="file" id="albumUpload" accept="image/png, image/jpeg" required
                                       onChange={(e) => updateCover(e.target.files[0])}/>
                                <span
                                    className={"text-muted"}><b>png</b> and <b>jpeg</b> file formats accepted</span>
                            </label>
                        </div>
                        <br/>

                        <div className="form-group">
                            <input type="text"
                                   className="form-control"
                                   id="albumName"
                                   name="albumName"
                                   required
                                   placeholder="Enter the album name"
                                   onChange={handleChange}/>
                        </div>
                        <br/>

                        <div className="form-group">
                            <select onChange={handleChange} name="albumGenre" required={true} className="form-control">
                                <option className={"text-muted"} value={null} disabled={true} selected={true}>
                                    -- Choose album genre --
                                </option>
                                {(props.genres) ? props.genres.map((term) => {
                                        return <option key={term} value={term}>{term}</option>;
                                    }
                                ) : undefined}
                            </select>
                        </div>
                        <br/>

                        <div className={"form-group"}>
                            <Select
                                closeMenuOnSelect={false}
                                components={makeAnimated()}
                                isMulti
                                isSearchable
                                defaultValue={"-- Select songs --"}
                                onChange={(choice) => updateSongIdList(choice)}
                                options={(props.songs && props.songs.content) ? props.songs.content.map((term) => {
                                    return (term && term['creator'].id === props.selectedArtist.id
                                        && !term['album'] && !term['isPublished'])
                                        ? {value: term.id, label: term.songName} : undefined;
                                }).filter(term => term !== undefined) : undefined}/>
                        </div>
                        <br/>

                        <div className="form-group">
                            <select onChange={handleChange}
                                    name="albumTier" className="form-control" required={true}>
                                <option className={"text-muted"} value={null} disabled={true} selected={true}>
                                    -- Choose tier --
                                </option>
                                {(props.tiers) ? props.tiers.map((term) => {
                                        return <option key={term} value={term}>{term}</option>;
                                    }
                                ) : undefined}
                            </select>
                        </div>
                        <br/>

                        <div className="form-group">
                            <span>
                                <input name="subscriptionFee" disabled={true}
                                       id="subscriptionFee"
                                       value={StringUtil.formatCurrency(subscriptionFee.amount,
                                           subscriptionFee.currency)}
                                       className="form-control disabled"/>&nbsp;
                            </span>
                            <span className={"text-muted"}>Subscription fee is based on the tier our platform offers for distribution</span>
                        </div>
                        <br/>

                        <div className="form-group">
                            <input name="transactionFee" disabled={true}
                                   id={"transactionFee"}
                                   value={StringUtil.formatCurrency(props.transactionFee.amount,
                                       props.transactionFee.currency)}
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

                        <div className="accordion">
                            <div className="accordion-item">
                                <h2 className="accordion-header">
                                    <button className="accordion-button collapsed" type="button"
                                            onClick={e => ScreenElementsUtil.toggleAccordionItems(e)}>
                                        Additional information
                                    </button>
                                </h2>
                                <div id="collapseOne" className="accordion-collapse collapse">
                                    <div className={"row"}>
                                        <div className="form-group form-group-inner mt-4">
                                            <input type="tel"
                                                   className="form-control"
                                                   id="artistName"
                                                   name="artistName"
                                                   placeholder="Enter the artist name"
                                                   onChange={handleChange}/>
                                        </div>
                                    </div>
                                    <br/>
                                    <div className={"row"}>
                                        <div className="form-group form-group-inner">
                                            <input type="text"
                                                   className="form-control"
                                                   id="producerName"
                                                   name="producerName"
                                                   placeholder="Enter the producer name"
                                                   onChange={handleChange}/>
                                        </div>
                                    </div>
                                    <br/>
                                    <div className={"row"}>
                                        <div className="form-group form-group-inner">
                                            <input type="text"
                                                   className="form-control"
                                                   id="composerName"
                                                   name="composerName"
                                                   placeholder="Enter the composer name"
                                                   onChange={handleChange}/>
                                        </div>
                                    </div>
                                    <br/>
                                </div>
                            </div>
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

export default PublishAlbum;