import {toast} from "react-toastify";
import {SUBSCRIPTION_FEE_FETCH_FAILED} from "../constants/exception";
import {EUR} from "../constants/alphabet";

const PaymentUtil = {
    async getSubscriptionFeeWithoutCalculation(props, tier) {
        return await props.subscriptionFee(tier).then((data) => {
            return data.data;
        }).catch(() => {
            toast.error(SUBSCRIPTION_FEE_FETCH_FAILED);
            return {amount: 0.0, currency: EUR};
        });
    },
    async getSubscriptionFeeWithCalculation(props, tier, newTier, updateTierFn, exceptionMessage) {
        return await props.subscriptionFee(tier).then(async (data) => {
            const currentSubscriptionFee = data.data;
            return await props.subscriptionFee(newTier).then((data) => {
                const existingSubscriptionFee = data.data;
                return this.getCalculatedSubscriptionFee(existingSubscriptionFee,
                    currentSubscriptionFee, tier, newTier, updateTierFn, exceptionMessage);
            }).catch(() => {
                toast.error(SUBSCRIPTION_FEE_FETCH_FAILED);
                return {amount: 0.0, currency: EUR};
            });
        }).catch(() => {
            toast.error(SUBSCRIPTION_FEE_FETCH_FAILED);
            return {amount: 0.0, currency: EUR};
        });
    },
    getCalculatedSubscriptionFee(existingSubscriptionFee,
                                 currentSubscriptionFee,
                                 tier,
                                 newTier,
                                 updateTierFn,
                                 exceptionMessage) {
        if (existingSubscriptionFee.amount >= currentSubscriptionFee.amount) {
            updateTierFn(newTier);
            toast.warn(exceptionMessage);
            return {amount: 0.0, currency: existingSubscriptionFee.currency};
        } else {
            updateTierFn(tier);
            return {
                amount: currentSubscriptionFee.amount - existingSubscriptionFee.amount,
                currency: existingSubscriptionFee.currency
            };
        }
    }
}

export default PaymentUtil;