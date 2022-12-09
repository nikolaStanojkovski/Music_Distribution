import axios from "../../../custom-axios/axiosStreamingService";
import {TIERS} from "../../../constants/endpoint";

const TierRepository = {
    fetchTiers: () => {
        return axios.get(TIERS);
    },
}

export default TierRepository;