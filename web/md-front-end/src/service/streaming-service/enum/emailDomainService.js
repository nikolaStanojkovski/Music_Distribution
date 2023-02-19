import EmailDomainRepository from "../../../repository/streaming-service/enum/emailDomainRepository";
import React from "react";
import {toast} from "react-toastify";
import {EMAIL_DOMAIN_FETCH_FAILED} from "../../../constants/exception";

const useEmailDomainService = () => {

    const [emailDomains, setEmailDomains] = React.useState([]);
    React.useEffect(() => {
        loadEmailDomains();
    }, []);

    const loadEmailDomains = () => {
        EmailDomainRepository.fetchEmailDomains()
            .then((data) => {
                setEmailDomains(data.data);
            }).catch(() => {
            toast.error(EMAIL_DOMAIN_FETCH_FAILED);
        });
    }

    return {emailDomains};
}

export default useEmailDomainService;