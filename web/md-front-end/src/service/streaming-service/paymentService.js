import React from "react";
import ArtistRepository from "../../repository/streaming-service/artistRepository";
import PaymentRepository from "../../repository/streaming-service/paymentRepository";
import {toast} from "react-toastify";
import {TRANSACTION_FAILURE, TRANSACTION_FEE_FETCH_FAILED} from "../../constants/exception";
import {APPROVAL_LINK} from "../../constants/model";

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

    const createPayment = (totalAmount) => {
        if (ArtistRepository.fetchArtistLocal()) {
            return PaymentRepository.createOrder(totalAmount)
                .then((data) => {
                    const approvalLink = data.data[APPROVAL_LINK];
                    if (approvalLink) {
                        window.location.replace(approvalLink);
                    } else {
                        toast.error(TRANSACTION_FAILURE);
                    }
                }).catch(() => {
                    toast.error(TRANSACTION_FAILURE);
                });
        } else {
            toast.error(TRANSACTION_FAILURE);
            return false;
        }
    }

    return {transactionFee, getTransactionFee, getSubscriptionFee, createPayment};
}

export default usePaymentService;