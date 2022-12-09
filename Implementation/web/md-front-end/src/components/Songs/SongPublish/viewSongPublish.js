import React from "react";
import {ALBUM, CREATOR, IS_PUBLISHED} from "../../../constants/model";
import StringUtil from "../../../util/stringUtil";

const viewSongPublish = (props) => {
    return (
        <form onSubmit={props.onFormSubmit}>
            <div className="form-group">
                <label className="upload-drop-container">
                    <span className="upload-drop-title">Song cover picture</span>
                    <input type="file" id="songUpload" accept="image/png, image/jpeg" required
                           onChange={(e) => props.updateCover(e.target.files[0])}/>
                    <span
                        className={"text-muted"}><b>png</b> and <b>jpeg</b> file formats accepted</span>
                </label>
            </div>
            <br/>

            <div className={"form-group"}>
                <select onChange={props.handleChange} name="songId"
                        defaultValue={"-- Choose song --"}
                        className="form-control" required={true}>
                    <option className={"text-muted"} value={"-- Choose song --"} disabled={true}>
                        -- Choose song --
                    </option>
                    {(props.songs && props.songs.content) ? props.songs.content.map((term) => {
                            return (term && term[CREATOR]
                                && term[CREATOR].id === props.selectedArtist.id
                                && !term[ALBUM] && !term[IS_PUBLISHED]) ?
                                <option key={term.id} value={term.id}>{term.songName}</option> : undefined;
                        }
                    ) : undefined}
                </select>
            </div>
            <br/>

            <div className="form-group">
                <select onChange={props.handleChange} name="songTier"
                        defaultValue={"-- Choose tier --"}
                        className="form-control" required={true}>
                    <option className={"text-muted"} value={"-- Choose tier --"} disabled={true}>
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
                       value={props.selectedArtist['userPersonalInfo'].fullName}
                       className="form-control disabled"/>
            </div>
            <br/>
            <br/>

            <button id="submit" type="submit" className="btn btn-dark w-100">Submit</button>
            <br/>
        </form>
    );
}

export default viewSongPublish;