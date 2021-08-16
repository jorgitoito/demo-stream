package com.jmr.stream.demostream.util;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * Utility class for checking null objects or not null objects:
 * If object must be No null AND is null, throws an exception
 * If object must be null AND is NOT null, throws an exception
 *
 * Using this class, code is cleaner.
 */
public final class NullChecker {
    /**
     * Application not found message
     */
    public static final String APPLICATION_NOT_FOUND = "Application not found: ";
    /**
     * Component not found message
     */
    public static final String COMPONENT_NOT_FOUND = "Component not found: ";


    /**
     * Private constructor
     */
    private NullChecker() {
        throw new UnsupportedOperationException("Do not instantiate NullChecker library.");
    }

    /**
     * If object is null throw a bad request exception with given message
     *
     * @param obj object to check
     * @param msg exception message
     */
    public static void checkNull(Object obj, String msg) {
        if (obj == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, msg);
        }
    }

    public static void checkNull_BAD_REQUEST(Object obj, String msg) {
        if (obj == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, msg);
        }
    }

    /**
     * Check obj must be null
     * @param obj obj to check
     * @param msg error text
     */
    public static void checkNoNull_BAD_REQUEST(Object obj, String msg) {
        if (obj != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, msg);
        }
    }

    public static void checkNull_NOT_FOUND(Object obj, String msg) {
        if (obj == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, msg);
        }
    }

    /**
     * If string is null throw a bad request exception with given message
     *
     * @param str string to check
     * @param msg exception message
     */
    public static void checkEmptyStr(String str, String msg) {

        if (!org.apache.commons.lang3.StringUtils.isNotEmpty(str)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, msg);
        }
    }
}
