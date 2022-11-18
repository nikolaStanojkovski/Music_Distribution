package com.musicdistribution.sharedkernel.constant;

/**
 * Helper class used for storing exception constants.
 */
public final class ExceptionConstants {

    public static final String ENTITY_CREATION_FAILURE = "The entity ID must not be null.";
    public static final String DOMAIN_OBJECT_CREATION_FAILURE = "The domain UUID must not be null.";
    public static final String MONEY_ADDITION_FAILURE = "Cannot add two Money objects with different currencies.";
    public static final String MONEY_SUBTRACTION_FAILURE = "Cannot subtract two Money objects with different currencies.";
    public static final String ID_CLASS_CREATION_FAILURE = "idClass must not be null.";
    public static final String CLASS_CREATION_FAILURE = "Could not create new instance of %s.";

    /**
     * Private constructor which throws an exception because the class should not be instantiated.
     */
    private ExceptionConstants() {
        throw new UnsupportedOperationException();
    }
}
