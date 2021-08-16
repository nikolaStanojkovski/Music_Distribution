package finki.ukim.mk.emtproject.albumpublishing.domain.models.dto;

import finki.ukim.mk.emtproject.albumpublishing.domain.valueobjects.DistributorInfo;
import finki.ukim.mk.emtproject.sharedkernel.domain.valueobjects.Money;
import lombok.Data;

@Data
public class MusicDistributorDto {

    private String id;

    private String distributorInfo;

    private DistributorInfo distributorInfoFull;

    private Money totalEarned;

    private Integer noAlbumsPublished;

    public MusicDistributorDto(String id, String distributorInfo, DistributorInfo distributorInfoFull, Money totalEarned, Integer noAlbumsPublished) {
        this.id = id;
        this.distributorInfo = distributorInfo;
        this.totalEarned = totalEarned;
        this.distributorInfoFull = distributorInfoFull;
        this.noAlbumsPublished = noAlbumsPublished;
    }
}
