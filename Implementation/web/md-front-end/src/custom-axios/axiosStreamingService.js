import axios from "axios";
import {API_BASE_URL} from "../constants/endpoint";
import {ACCESS_TOKEN, ARTIST_ROLE} from "../constants/auth";
import {ASTERISK, EMPTY_STRING} from "../constants/alphabet";
import AuthUtil from "../util/authUtil";

const token = localStorage.getItem(ACCESS_TOKEN);
const instance = axios.create({
    baseURL: API_BASE_URL,
    headers: {
        'Access-Control-Allow-Origin': ASTERISK,
        'Authorization': `Bearer ${(!AuthUtil.isExpired(token)) ? token : EMPTY_STRING}`,
        'Auth-Role': ARTIST_ROLE
    }
})

export default instance;