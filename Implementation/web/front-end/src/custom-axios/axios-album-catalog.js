import axios from "axios";
import jwt from 'jsonwebtoken';

function isExpired(token) {
    let decodedToken = jwt.decode(token, {complete: true});
    let dateNow = new Date();
    return (decodedToken) ? decodedToken.exp < dateNow.getTime() : false;
}

const token = localStorage.getItem('accessToken');
const instance = axios.create({
    baseURL: 'http://localhost:8082/api',
    headers: {
        'Access-Control-Allow-Origin': '*',
        'Authorization': `Bearer ${(!isExpired(token)) ? token : ''}`
    }
})

export default instance;