import axios from "../../../custom-axios/axiosStreamingService";
import {EMAIL_DOMAINS} from "../../../constants/endpoint";

const EmailDomainRepository = {
    fetchEmailDomains: () => {
        return axios.get(EMAIL_DOMAINS);
    },
}

export default EmailDomainRepository;