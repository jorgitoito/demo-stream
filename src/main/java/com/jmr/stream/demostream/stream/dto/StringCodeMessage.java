package com.jmr.stream.demostream.stream.dto;

import lombok.*;

/**
 * Message with a string id
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StringCodeMessage {
    /**
     * Id
     */
    @NonNull
    private String code;
    /**
     * Requester user code
     */
    private String requestedBy;
    /**
     * Whether to continue workflow or not
     */
    private Boolean workflow;
}
