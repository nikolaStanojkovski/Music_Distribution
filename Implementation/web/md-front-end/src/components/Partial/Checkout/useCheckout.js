import {useHistory} from "react-router-dom";
import React, {useEffect} from "react";
import {EMPTY_STRING} from "../../../constants/alphabet";
import {TRANSACTION_FAILURE, TRANSACTION_SUCCESSFUL} from "../../../constants/exception";
import {DEFAULT} from "../../../constants/endpoint";

const useCheckout = (props) => {

    const history = useHistory();

    const [message, setMessage] = React.useState(EMPTY_STRING);
    const [displayCheckmark, setDisplayCheckmark] = React.useState(false);
    const [resultWatermark, setResultWatermark] = React.useState(false);

    const isCheckoutValid = () => {
        return (props && props.location
            && props.location.state
            && props.location.state.result !== undefined);
    }

    const getResult = () => {
        return props.location.state.result;
    }

    useEffect(() => {
        if (isCheckoutValid()) {
            setTimeout(() => {
                const result = getResult();
                setMessage(result ? TRANSACTION_SUCCESSFUL : TRANSACTION_FAILURE);
                setDisplayCheckmark(true);
                setResultWatermark(result);
            }, 4000);
        } else {
            history.push(DEFAULT);
        }
    })

    return {message, displayCheckmark, resultWatermark};
}

export default useCheckout;