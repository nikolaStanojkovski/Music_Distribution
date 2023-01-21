import axios from "../../api/apiStreamingService";
import {CREATE, PAYMENT, SUBSCRIPTION, TRANSACTION} from "../../constants/endpoint";
import {LOCALE, TIER} from "../../constants/model";

const PaymentRepository = {
    getTransactionFee: (locale) => {
        return axios.get(`${PAYMENT}${TRANSACTION}?${LOCALE}=${locale}`);
    },
    getSubscriptionFee: (tier) => {
        return axios.get(`${PAYMENT}${SUBSCRIPTION}?${TIER}=${tier}`);
    },
    createOrder: (totalAmount) => {
        return axios.post(`${PAYMENT}${CREATE}`, totalAmount);
    }
}

export default PaymentRepository;