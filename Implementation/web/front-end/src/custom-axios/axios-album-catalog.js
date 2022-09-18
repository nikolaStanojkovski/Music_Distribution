import axios from "axios";

const instance = axios.create({
    baseURL: 'http://localhost:8082/api',
    headers: {
        'Access-Control-Allow-Origin' : '*'
    }
})

export default instance;