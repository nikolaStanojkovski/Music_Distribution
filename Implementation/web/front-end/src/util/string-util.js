import React from 'react';

const StringUtil = {
    formatCurrency(amount, currency) {
        return `${(amount) ? amount : 0}.00 ${(currency) ? currency : 'EUR'}`;
    }
}

export default StringUtil;