import axios from "axios";
import jwt from 'jsonwebtoken';
import {API_BASE_URL} from "../constants/endpoint";
import {ACCESS_TOKEN, ARTIST_ROLE} from "../constants/auth";
import {EMPTY_STRING} from "../constants/alphabet";

function isExpired(token) {
    let decodedToken = jwt.decode(token, {complete: true});
    let dateNow = new Date();
    return (decodedToken) ? decodedToken.exp < dateNow.getTime() : false;
}

const token = localStorage.getItem(ACCESS_TOKEN);
const instance = axios.create({
    baseURL: API_BASE_URL,
    headers: {
        'Access-Control-Allow-Origin': '*',
        'Authorization': `Bearer ${(!isExpired(token)) ? token : EMPTY_STRING}`,
        'Auth-Role': ARTIST_ROLE
    }
})

export default instance;