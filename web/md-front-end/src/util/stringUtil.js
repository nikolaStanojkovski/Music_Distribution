import {DOUBLE_ZERO, EUR, ZERO} from "../constants/alphabet";
import {AMOUNT, CURRENCY} from "../constants/model";

const StringUtil = {
    formatCurrency(money) {
        if (!money || money.length === 0) {
            return `${ZERO}.${DOUBLE_ZERO} ${EUR}`;
        }
        const amount = money[AMOUNT];
        const currency = money[CURRENCY];
        return `${(amount) ? amount : 0}.${DOUBLE_ZERO} ${(currency) ? currency : EUR}`;
    },
    formatMinutes(lengthInSeconds) {
        if (!lengthInSeconds) {
            return ZERO;
        }
        const minutes = parseInt((lengthInSeconds / 60).toString());
        return `${(minutes >= 10) ? minutes : ZERO + minutes}`;
    },
    formatSeconds(lengthInSeconds) {
        if (!lengthInSeconds) {
            return ZERO;
        }
        const seconds = parseInt((lengthInSeconds % 60).toString());
        return `${(seconds >= 10) ? seconds : ZERO + seconds}`;
    }
}

export default StringUtil;