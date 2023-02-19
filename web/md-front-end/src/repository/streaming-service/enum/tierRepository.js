import axios from "../../../api/apiStreamingService";
import {TIERS} from "../../../constants/endpoint";

const TierRepository = {
    fetchTiers: () => {
        return axios.get(TIERS);
    },
}

export default TierRepository;