package com.jmr.stream.demostream.stream.dto;

import lombok.*;

/**
 * Message with a long id
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LongIdMessage {
    /**
     * Id
     */
    @NonNull
    private Long id;
    /**
     * Requester user code
     */
    private String requestedBy;
    /**
     * Whether to continue workflow or not
     */
    private Boolean workflow;

    /**
     * idS
     */
    private String idS;

    private Object object;

    private String objectJson;
}
