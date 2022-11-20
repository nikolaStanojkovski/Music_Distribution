package com.musicdistribution.storageservice.constant;

/**
 * Helper class used for storing constants related to database entities.
 */
public final class EntityConstants {

    public static final String ID = "id";
    public static final String ARTIST_NAME = "artistName";
    public static final String PRODUCER_NAME = "producerName";
    public static final String COMPOSER_NAME = "composerName";
    public static final String SUBSCRIPTION_FEE_AMOUNT = "subscriptionFee.amount";
    public static final String SUBSCRIPTION_FEE_CURRENCY = "subscriptionFee.currency";
    public static final String TRANSACTION_FEE_AMOUNT = "transactionFee.amount";
    public static final String TRANSACTION_FEE_CURRENCY = "transactionFee.currency";
    public static final String TIER = "tier";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String EMAIL_DOMAIN_USERNAME = "email.domainUsername";
    public static final String EMAIL_DOMAIN_NAME = "email.domainName";
    public static final String TELEPHONE_NUMBER = "telephoneNumber";
    public static final String FIRST_NAME = "firstName";
    public static final String LAST_NAME = "lastName";
    public static final String ART_NAME = "artName";
    public static final String IS_PUBLISHED = "isPublished";
    public static final String LENGTH_IN_SECONDS = "lengthInSeconds";

    public static final String ALBUM = "album";
    public static final String ALBUM_ARTIST_NAME = "album_artist_name";
    public static final String ALBUM_PRODUCER_NAME = "album_producer_name";
    public static final String ALBUM_COMPOSER_NAME = "album_composer_name";
    public static final String ALBUM_SUBSCRIPTION_FEE_AMOUNT = "album_payment_subscription_fee_amount";
    public static final String ALBUM_SUBSCRIPTION_FEE_CURRENCY = "album_payment_subscription_fee_currency";
    public static final String ALBUM_TRANSACTION_FEE_AMOUNT = "album_payment_transaction_fee_amount";
    public static final String ALBUM_TRANSACTION_FEE_CURRENCY = "album_payment_transaction_fee_currency";
    public static final String ALBUM_PAYMENT_TIER = "album_payment_tier";

    public static final String ARTIST = "artist";
    public static final String ARTIST_USERNAME = "artist_username";
    public static final String ARTIST_PASSWORD = "artist_password";
    public static final String ARTIST_EMAIL_DOMAIN_USERNAME = "artist_email_domain_username";
    public static final String ARTIST_EMAIL_DOMAIN_NAME = "artist_email_domain_name";
    public static final String ARTIST_TELEPHONE_NUMBER = "artist_telephone_number";
    public static final String ARTIST_FIRST_NAME = "artist_first_name";
    public static final String ARTIST_LAST_NAME = "artist_last_name";
    public static final String ARTIST_ART_NAME = "artist_art_name";

    public static final String SONG = "song";
    public static final String SONG_SUBSCRIPTION_FEE_AMOUNT = "song_payment_subscription_fee_amount";
    public static final String SONG_SUBSCRIPTION_FEE_CURRENCY = "song_payment_subscription_fee_currency";
    public static final String SONG_TRANSACTION_FEE_AMOUNT = "song_payment_transaction_fee_amount";
    public static final String SONG_TRANSACTION_FEE_CURRENCY = "song_payment_transaction_fee_currency";
    public static final String SONG_PAYMENT_TIER = "song_payment_tier";
    public static final String SONG_LENGTH_IN_SECONDS = "song_length_in_seconds";

    /**
     * Private constructor which throws an exception because the class should not be instantiated.
     */
    private EntityConstants() {
        throw new UnsupportedOperationException(ExceptionConstants.UNSUPPORTED_CLASS_INSTANTIATION);
    }
}
