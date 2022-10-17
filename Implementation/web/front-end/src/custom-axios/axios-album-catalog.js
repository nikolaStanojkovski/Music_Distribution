import axios from "axios";

const token = localStorage.getItem('accessToken');
const instance = axios.create({
    baseURL: 'http://localhost:8082/api',
    headers: {
        'Access-Control-Allow-Origin' : '*',
        'Authorization': `Bearer ${token}`
    }
})

export default instance;