import Select from "react-select";
import makeAnimated from "react-select/animated";
import StringUtil from "../../../util/stringUtil";
import ScreenElementsUtil from "../../../util/screenElementsUtil";
import React from "react";
import {ALBUM, FULL_NAME, IS_PUBLISHED, NONE, USER_PERSONAL_INFO} from "../../../constants/model";

const viewAlbumPublish = (props) => {
    return (
        <form onSubmit={props.onFormSubmit}>
            <div className="form-group">
                <label className="upload-drop-container">
                    <span className="upload-drop-title">Album cover picture</span>
                    <input type="file" id="albumUpload" accept="image/png, image/jpeg" required={true}
                           onChange={(e) => props.updateCover(e.target.files[0])}/>
                    <span
                        className={"text-muted"}><b>png</b> and <b>jpeg</b> file formats accepted
                        with <b>2 MB</b> max file size
                    </span>
                </label>
            </div>
            <br/>

            <div className="form-group">
                <input type="text"
                       className="form-control"
                       id="albumName"
                       name="albumName"
                       required={true}
                       placeholder="Enter the album name"
                       onChange={props.handleChange}/>
            </div>
            <br/>

            <div className="form-group">
                <select onChange={props.handleChange} name="albumGenre"
                        defaultValue={"-- Choose album genre --"}
                        required={true} className="form-control">
                    <option className={"text-muted"} value={"-- Choose album genre --"} disabled={true}>
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
                    onChange={(choice) => props.updateSongIdList(choice)}
                    options={(props.songs && props.songs.content) ? props.songs.content.map((term) => {
                        return (term[ALBUM] == null && !term[IS_PUBLISHED])
                            ? {value: term.id, label: term.songName} : undefined;
                    }).filter(term => term !== undefined) : undefined}/>
            </div>
            <br/>

            <div className="form-group">
                <select onChange={props.handleChange}
                        name="albumTier" className="form-control"
                        defaultValue={"-- Choose tier --"}
                        required={true}>
                    <option className={"text-muted"} value={"-- Choose tier --"} disabled={true}>
                        -- Choose tier --
                    </option>
                    {(props.tiers) ? props.tiers
                        .filter(term => term !== NONE)
                        .map((term) => {
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
                                       value={StringUtil.formatCurrency(props.subscriptionFee)}
                                       className="form-control disabled"/>&nbsp;
                            </span>
                <span className={"text-muted"}>Subscription fee is based on the tier our platform offers for distribution</span>
            </div>
            <br/>

            <div className="form-group">
                <input name="transactionFee" disabled={true}
                       id={"transactionFee"}
                       value={StringUtil.formatCurrency(props.transactionFee)}
                       className="form-control disabled"/>
                <span className={"text-muted"}>Transaction fee is fixed and based on your location and country</span>
            </div>
            <br/>

            <div className="form-group">
                <input name="artistName" disabled={true}
                       value={props.selectedArtist[USER_PERSONAL_INFO][FULL_NAME]}
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
                                       onChange={props.handleChange}/>
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
                                       onChange={props.handleChange}/>
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
                                       onChange={props.handleChange}/>
                            </div>
                        </div>
                        <br/>
                    </div>
                </div>
            </div>
            <br/>

            <br/>

            <button id="submit" type="submit" className="btn btn-dark w-100">Proceed to Checkout</button>
            <br/>
        </form>
    );
}

export default viewAlbumPublish;