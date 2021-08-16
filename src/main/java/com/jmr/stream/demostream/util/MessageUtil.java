package com.jmr.stream.demostream.util;

import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

/**
 * Message util
 */
public final class MessageUtil {
    /**
     * Message type header
     */
    public static final String TYPE_HEADER = "type";
    /**
     * Message finished header
     */
    public static final String FINISHED_HEADER = "finished";

    /**
     * Private constructor
     */
    private MessageUtil() {
        throw new UnsupportedOperationException("Do not instantiate MessageUtil library.");
    }

    /**
     * Generate message
     *
     * @param val  object
     * @param type type header
     * @param <T>  object type
     * @return message
     */
    public static <T> Message<T> message(T val, String type) {
        return MessageBuilder.withPayload(val).setHeader(TYPE_HEADER, type).build();
    }

    /**
     * Generate message
     *
     * @param val      object
     * @param type     type header
     * @param finished whether it is a finished state or not
     * @param <T>      object type
     * @return message
     */
    public static <T> Message<T> message(T val, String type, Boolean finished) {
        return MessageBuilder.withPayload(val).setHeader(TYPE_HEADER, type)
                .setHeader(FINISHED_HEADER, finished).build();
    }

}
