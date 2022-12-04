import StringUtil from "../../../util/stringUtil";
import React from "react";

const viewAlbumRaiseTier = (props) => {
    return (
        <form onSubmit={props.onFormSubmit}>
            <div className="form-group">
                <select onChange={props.handleAlbumChange}
                        name="album" className="form-control">
                    <option className={"text-muted"} value={null} disabled={true} selected={true}>
                        -- Choose album --
                    </option>
                    {(props.albums && props.albums.content) ? props.albums.content.map((term) => {
                            return <option key={term.id} value={term.id}>{term.albumName}</option>;
                        }
                    ) : undefined}
                </select>
            </div>
            <br/>

            <div className="form-group">
                <select onChange={(e) => props.handleAlbumTier(e.target.value)}
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
                                       value={StringUtil.formatCurrency(props.subscriptionFee.amount,
                                           props.subscriptionFee.currency)}
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

            <br/>

            <button id="submit" type="submit" className="btn btn-dark w-100">Submit</button>
            <br/>
        </form>
    );
}

export default viewAlbumRaiseTier;