const StringUtil = {
    formatCurrency(money) {
        if(!money || money.length === 0) {
            return '0.00 EUR';
        }
        const amount = money['amount'];
        const currency = money['currency'];
        return `${(amount) ? amount : 0}.00 ${(currency) ? currency : 'EUR'}`;
    }
}

export default StringUtil;