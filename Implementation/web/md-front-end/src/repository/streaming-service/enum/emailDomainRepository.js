import axios from "../../../custom-axios/axiosStorageService";

const EmailDomainRepository = {
    fetchEmailDomains: () => {
        return axios.get("/email-domains");
    },
}

export default EmailDomainRepository;