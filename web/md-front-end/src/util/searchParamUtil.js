import {EMPTY_STRING} from "../constants/alphabet";

const SearchParamUtil = {
    getSearchParams() {
        const searchParams = {key: EMPTY_STRING, value: EMPTY_STRING};
        new URLSearchParams(window.location.search)
            .forEach((value, key) => {
                searchParams.key += `,${key}`;
                searchParams.value += `,${value}`;
            });
        searchParams.key = searchParams.key.substring(1);
        searchParams.value = searchParams.value.substring(1);
        return searchParams;
    }
}

export default SearchParamUtil;