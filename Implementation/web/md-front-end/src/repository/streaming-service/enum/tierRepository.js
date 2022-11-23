import axios from "../../../custom-axios/axiosStorageService";

const TierRepository = {
    fetchTiers: () => {
        return axios.get("/tiers");
    },
}

export default TierRepository;