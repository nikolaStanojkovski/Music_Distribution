import React from "react";
import ArtistRepository from "../../repository/streaming-service/artistRepository";
import PaymentRepository from "../../repository/streaming-service/paymentRepository";
import {toast} from "react-toastify";
import {TRANSACTION_FEE_FETCH_FAILED} from "../../constants/exception";

const usePaymentService = () => {

    const [transactionFee, setTransactionFee] = React.useState();
    React.useEffect(() => {
        getTransactionFee();
    }, []);

    const getTransactionFee = () => {
        if (ArtistRepository.fetchArtistLocal()) {
            const locale = navigator.language;
            PaymentRepository.getTransactionFee(locale)
                .then((data) => {
                    setTransactionFee(data.data);
                }).catch(() => {
                toast.error(TRANSACTION_FEE_FETCH_FAILED);
            });
        }
    }

    const getSubscriptionFee = (tier) => {
        return PaymentRepository.getSubscriptionFee(tier);
    }

    return {transactionFee, getTransactionFee, getSubscriptionFee};
}

export default usePaymentService;