package com.epam.util;

/**
 * @author jdmon on 15/08/2025
 * @project springbasegymcrm
 */
public class Constants {
    public static final String INVALID_FIELD = "The field is required and must not be null or empty";
    public static final String INVALID_ID_VALUE = "The id field must be greater than or equal to 1";
    public static final String INVALID_MIN_VALUE = "The minimum value of the field is 20";
    public static final String INVALID_MAX_VALUE = "The maximum value of the field is 60";
    public static final String INVALID_EMAIL = "The email is not valid";
    public static final String INVALID_PASSWORD = "Password must be exactly 10 characters long " +
            "and contain only letters and digits";

    public final static String REGEX_PASSWORD = "^[a-zA-Z0-9]{10}$";


}
