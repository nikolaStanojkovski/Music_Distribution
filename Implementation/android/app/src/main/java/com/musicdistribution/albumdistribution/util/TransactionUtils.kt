package com.musicdistribution.albumdistribution.util

import com.musicdistribution.albumdistribution.model.Tier

class TransactionUtils {
    companion object {
        fun calculateCost(tier: Tier): Double {
            when (tier) {
                Tier.Bronze -> {
                    return 10.00
                }
                Tier.Silver -> {
                    return 20.00
                }
                Tier.Gold -> {
                    return 50.00
                }
                Tier.Platinum -> {
                    return 100.00
                }
                Tier.Diamond -> {
                    return 500.00
                }
            }
        }

        fun calculateAlbumCost(tier: Tier): String {
            return (calculateCost(tier) + 5.00).toString() + " €"
        }
    }
}