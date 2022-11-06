package com.musicdistribution.albumcatalog.domain.constants;

public final class EntityConstants {

    public static String ID = "id";

    public static String ALBUM_ID = ID;
    public static String ALBUM_NAME = "albumName";
    public static String ALBUM_SONG_LENGTH = "totalLength.lengthInSeconds";
    public static String ALBUM_GENRE = "genre";
    public static String ALBUM_TIER = "paymentInfo.tier";
    public static String ALBUM_SUBSCRIPTION_FEE = "paymentInfo.subscriptionFee.amount";
    public static String ALBUM_TRANSACTION_FEE = "paymentInfo.transactionFee.amount";
    public static String ALBUM_CREATOR = "creator." + ID;
}
