import ArtistRepository from "../../../repository/streaming-service/artistRepository";
import TierRepository from "../../../repository/streaming-service/enum/tierRepository";
import React from "react";
import {toast} from "react-toastify";
import {TIER_FETCH_FAILED} from "../../../constants/exception";

const useTierService = () => {

    const [tiers, setTiers] = React.useState([]);
    React.useEffect(() => {
        loadTiers();
    }, []);

    const loadTiers = () => {
        if (ArtistRepository.fetchArtistLocal()) {
            TierRepository.fetchTiers()
                .then((data) => {
                    setTiers(data.data);
                }).catch(() => {
                toast.error(TIER_FETCH_FAILED);
            });
        }
    }

    return {tiers};
}

export default useTierService;