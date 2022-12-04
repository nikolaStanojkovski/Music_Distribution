import EmailDomainRepository from "../../repository/streaming-service/enum/emailDomainRepository";
import React from "react";

const useEmailDomainService = () => {

    const [emailDomains, setEmailDomains] = React.useState([]);
    React.useEffect(() => {
        loadEmailDomains();
    }, []);

    const loadEmailDomains = () => {
        EmailDomainRepository.fetchEmailDomains()
            .then((data) => {
                setEmailDomains(data.data);
            });
    }

    return {emailDomains};
}

export default useEmailDomainService;