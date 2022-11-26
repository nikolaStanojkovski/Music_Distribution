import axios from "../../../custom-axios/axiosStreamingService";

const TierRepository = {
    fetchTiers: () => {
        return axios.get("/tiers");
    },
}

export default TierRepository;