package com.epam.util;

/**
 * @author jdmon on 29/07/2025
 * @project springbasegymcrm
 */
public final class ValidationUtil {
    private ValidationUtil() {
    }

    public static void requireNonNull(Object obj, String fieldName) {
        if (obj == null) {
            throw new IllegalArgumentException(fieldName + " can't be null");
        }
    }

    public static void requireNonBlank(String str, String fieldName) {
        if (str == null || str.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " is null or empty");
        }
    }

    public static void requirePositive(long number, String fieldName) {
        if (number <= 0) {
            throw new IllegalArgumentException(fieldName + " can be higher than 0");
        }
    }

    public static void requireExisting(Object obj, String fieldName) {
        if (obj == null) {
            throw new IllegalArgumentException(fieldName + " doesn't exist");
        }
    }

}
