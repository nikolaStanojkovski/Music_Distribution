import ArtistRepository from "../../repository/streaming-service/artistRepository";
import TierRepository from "../../repository/streaming-service/enum/tierRepository";
import React from "react";

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
                });
        }
    }

    return {tiers};
}

export default useTierService;