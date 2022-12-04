import React, {useEffect} from 'react';
import {Link} from "react-router-dom";

const SuccessfulCheckout = () => {

    const [displayCheckmark, setDisplayCheckmark] = React.useState(false);

    useEffect(() => {
        setTimeout(() => {
            setDisplayCheckmark(true);
        },5000);
    })

    return (
        <div>
            <div className="checkout-wrapper">
                <div className={"text-center"} hidden={displayCheckmark}>
                    <progress className="pure-material-progress-circular mt-lg-5"/>
                    <br />
                    <br />
                    <p className="text-muted">The transaction is being processed &#46; &#46; &#46;</p>
                </div>

                <div className={"checkmark-wrapper"}  hidden={!displayCheckmark}>
                    <div className={"row text-center"}>
                        <div className={"col text-center"}>
                            <div className="success-animation text-center">
                                <Link to={"/"}>
                                    <svg className="checkmark" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 52 52">
                                        <circle className="checkmark__circle" cx="26" cy="26" r="25" fill="none"/>
                                        <path className="checkmark__check" fill="none" d="M14.1 27.2l7.1 7.2 16.7-16.8"/>
                                    </svg>
                                </Link>
                            </div>
                            <br />
                        </div>
                    </div>
                    <div className={"row"}>
                        <div className={"col"}>
                            <p className="text-success">
                                <b>The transaction has been successful</b>
                            </p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default SuccessfulCheckout;