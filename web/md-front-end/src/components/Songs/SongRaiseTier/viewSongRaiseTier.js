import StringUtil from "../../../util/stringUtil";
import React from "react";
import {DIAMOND, IS_A_SINGLE, IS_PUBLISHED, NONE, PAYMENT_INFO, TIER} from "../../../constants/model";

const viewSongRaiseTier = (props) => {
    return (
        <form onSubmit={props.onFormSubmit}>
            <div className="form-group">
                <select onChange={props.handleSongChange} defaultValue={"-- Choose song --"}
                        name="song" className="form-control">
                    <option className={"text-muted"} value={"-- Choose song --"} disabled={true}>
                        -- Choose song --
                    </option>
                    {(props.songs && props.songs.content) ? props.songs.content
                        .filter((term) => (term && term[PAYMENT_INFO] && term[PAYMENT_INFO][TIER])
                            ? term[PAYMENT_INFO][TIER] !== DIAMOND : true)
                        .map((term) => {
                                return (term[IS_PUBLISHED] && term[IS_A_SINGLE])
                                    ? <option key={term.id} value={term.id}>{term.songName}</option>
                                    : undefined;
                            }
                        ) : undefined}
                </select>
            </div>
            <br/>

            <div className="form-group">
                <select onChange={(e) => props.handleSongTier(e.target.value)}
                        name="songTier" className="form-control" required={true} defaultValue="-- Choose tier --">
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

            <br/>

            <button id="submit" type="submit" className="btn btn-dark w-100">Proceed to Checkout</button>
            <br/>
        </form>
    );
}

export default viewSongRaiseTier;