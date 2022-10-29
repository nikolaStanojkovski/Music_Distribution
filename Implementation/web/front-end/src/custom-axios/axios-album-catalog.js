import axios from "axios";
import jwt from 'jsonwebtoken';
import {API_BASE_URL} from "../constants/constants";

function isExpired(token) {
    let decodedToken = jwt.decode(token, {complete: true});
    let dateNow = new Date();
    return (decodedToken) ? decodedToken.exp < dateNow.getTime() : false;
}

const token = localStorage.getItem('accessToken');
const instance = axios.create({
    baseURL: API_BASE_URL,
    headers: {
        'Access-Control-Allow-Origin': '*',
        'Authorization': `Bearer ${(!isExpired(token)) ? token : ''}`
    }
})

export default instance;