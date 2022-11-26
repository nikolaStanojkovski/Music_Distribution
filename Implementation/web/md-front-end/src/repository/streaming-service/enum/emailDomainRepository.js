import axios from "../../../custom-axios/axiosStreamingService";

const EmailDomainRepository = {
    fetchEmailDomains: () => {
        return axios.get("/email-domains");
    },
}

export default EmailDomainRepository;