import axios from "../../custom-axios/axiosStreamingService";
import {PAYMENT, SUBSCRIPTION, TRANSACTION} from "../../constants/endpoint";
import {LOCALE, TIER} from "../../constants/model";

const PaymentRepository = {
    getTransactionFee: (locale) => {
        return axios.get(`${PAYMENT}${TRANSACTION}?${LOCALE}=${locale}`);
    },
    getSubscriptionFee: (tier) => {
        return axios.get(`${PAYMENT}${SUBSCRIPTION}?${TIER}=${tier}`);
    },
}

export default PaymentRepository;