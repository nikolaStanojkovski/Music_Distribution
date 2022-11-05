import React from 'react';
import {useHistory} from 'react-router-dom';
import StringUtil from "../../../util/string-util";

const AlbumRaiseTier = (props) => {

    const History = useHistory();
    const [album, updateAlbum] = React.useState({});
    const [albumTier, updateAlbumTier] = React.useState("");
    const [subscriptionFee, updateSubscriptionFee] = React.useState({});

    const handleCalculatedFee = (existingSubscriptionFee, currentSubscriptionFee,
                                 tier, albumTier) => {
        if (existingSubscriptionFee.amount > currentSubscriptionFee.amount) {
            updateAlbumTier(albumTier);
            return {amount: 0.0, currency: existingSubscriptionFee.currency};
        } else {
            updateAlbumTier(tier);
            return {
                amount: currentSubscriptionFee.amount - existingSubscriptionFee.amount,
                currency: existingSubscriptionFee.currency
            };
        }
    }

    const handleSubscriptionFee = (tier, albumTier) => {
        props.subscriptionFee(tier).then((data) => {
            const currentSubscriptionFee = data.data;
            console.log(currentSubscriptionFee);
            props.subscriptionFee(albumTier).then((data) => {
                const existingSubscriptionFee = data.data;

                console.log(existingSubscriptionFee);
                const calculatedFee = handleCalculatedFee(existingSubscriptionFee,
                    currentSubscriptionFee, tier, albumTier);
                updateSubscriptionFee(calculatedFee);
            });
        });
    }

    const handleAlbumChange = (e) => {
        const albumId = e.target.value;
        if (albumId) {
            const filteredAlbums = props.albums.filter(album => album.id === albumId);
            if (filteredAlbums && filteredAlbums.length > 0) {
                updateAlbum(filteredAlbums[0]);
                handleAlbumTier(albumTier);
            }
        }
    }

    const handleAlbumTier = (tier) => {
        const paymentInfo = album['paymentInfo'];
        if(paymentInfo) {
            const albumTier = paymentInfo['tier'];
            if (tier && paymentInfo) {
                handleSubscriptionFee(tier, albumTier);
            }
        }
    }

    const onFormSubmit = (e) => {
        e.preventDefault();

        const albumId = album.id;
        if (albumId && albumTier && subscriptionFee &&
            subscriptionFee.amount > 0 && props.transactionFee) {
            props.raiseTierAlbum(albumId, albumTier, subscriptionFee, props.transactionFee);
            History.push("/checkout/success");
        }
    }

    return (
        <div className="container mm-4 my-5">
            <div className={"row mb-5"}>
                <div className={"col-md"}>
                    <h1 className="display-5">Raise an album's tier</h1>
                    <p className="text-muted">Increase the popularity of the music in your album by raising its tier.</p>
                </div>
            </div>

            <div className={"row"}>
                <div className="col-md">
                    <form onSubmit={onFormSubmit}>
                        <div className="form-group">
                            <select onChange={handleAlbumChange}
                                    name="album" className="form-control">
                                <option className={"text-muted"} value={null} disabled={true} selected={true}>
                                    -- Choose album --
                                </option>
                                {props.albums.map((term) => {
                                        return <option key={term.id} value={term.id}>{term.albumName}</option>;
                                    }
                                )}
                            </select>
                        </div>
                        <br/>

                        <div className="form-group">
                            <select onChange={(e) => handleAlbumTier(e.target.value)}
                                    name="albumTier" className="form-control" required={true}>
                                <option className={"text-muted"} value={null} disabled={true} selected={true}>
                                    -- Choose tier --
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

                        <br/>

                        <button id="submit" type="submit" className="btn btn-dark w-100">Submit</button>
                        <br/>
                    </form>

                </div>
            </div>
        </div>
    );
};

export default AlbumRaiseTier;