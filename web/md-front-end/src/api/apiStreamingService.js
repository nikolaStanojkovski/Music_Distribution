import axios from "axios";
import {API_BASE_URL} from "../constants/endpoint";
import RequestUtil from "../util/requestUtil";

const instance = axios.create({
    baseURL: API_BASE_URL,
    headers: RequestUtil.constructRequestHeaders()
});

export default instance;