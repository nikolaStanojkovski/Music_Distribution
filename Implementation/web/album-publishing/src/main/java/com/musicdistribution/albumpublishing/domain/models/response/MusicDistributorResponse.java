package com.musicdistribution.albumpublishing.domain.models.response;

import com.musicdistribution.albumpublishing.domain.models.entity.MusicDistributor;
import com.musicdistribution.albumpublishing.domain.valueobjects.DistributorInfo;
import com.musicdistribution.sharedkernel.domain.valueobjects.Money;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Response object for a music distributor.
 */
@Data
@NoArgsConstructor
public class MusicDistributorResponse {

    private String id;
    private String distributorInfo;
    private DistributorInfo distributorInfoFull;
    private Money totalEarned;
    private Integer noAlbumsPublished;

    /**
     * Static method used for creation of a music distributor response object.
     *
     * @param musicDistributor - the music distributor's information from which the response object is created.
     * @return the created music distributor response object.
     */
    public static MusicDistributorResponse from(MusicDistributor musicDistributor) {
        MusicDistributorResponse musicDistributorResponse = new MusicDistributorResponse();
        musicDistributorResponse.setId(musicDistributor.getId().getId());
        musicDistributorResponse.setDistributorInfo(musicDistributor.getDistributorInfo().getDistributorInformation());
        musicDistributorResponse.setDistributorInfoFull(musicDistributor.getDistributorInfo());
        musicDistributorResponse.setTotalEarned(musicDistributor.totalEarnings());
        musicDistributorResponse.setNoAlbumsPublished(musicDistributor.getPublishedAlbums().size());

        return musicDistributorResponse;
    }
}
