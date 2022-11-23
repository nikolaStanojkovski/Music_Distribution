import axios from "../../custom-axios/axiosStorageService";

const PaymentRepository = {
    getTransactionFee: (locale) => {
        return axios.get(`/payment/transaction?locale=${locale}`);
    },
    getSubscriptionFee: (tier) => {
        return axios.get(`/payment/subscription?tier=${tier}`);
    },
}

export default PaymentRepository;