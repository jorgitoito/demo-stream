package com.jmr.stream.demostream.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jmr.stream.demostream.client.TrackerAPI;
import com.jmr.stream.demostream.model.entity.UserEntity;
import com.jmr.stream.demostream.stream.dto.LongIdMessage;
import com.jmr.stream.demostream.util.MessageUtil;
import com.jmr.stream.demostream.util.ServiceUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Slf4j
@Service
@AllArgsConstructor
public class TrackerService {

    /**
     * Tracker API
     */
    private final TrackerAPI trackerAPI;

    /**
     * Util class
     */
    private final ServiceUtil serviceUtil;

    /**
     * application processor
     */
    private final Processor applicationProcessor;


    public String createData(String payload) {
        log.info("Init createData");
        ResponseEntity<Mono<String>> response;
        try {
            response = trackerAPI.create(payload).block();
            log.debug("response createData: {}", response);
        } catch (Exception e) {
            //Log error, create ResponseStatusException and throw it
            throw serviceUtil.getResponseStatusException(e, "Error creating data");
        }
        // response here can not be null
        if (serviceUtil.checkResponse(response)) {
            return Objects.requireNonNull(response.getBody()).block();
        }
        log.warn("createData: response is NOT is2xxSuccessful, response: {}", response);
        return null;
    }


    public void createDataMessage(String payload)
    {
        this.sendMessage(payload, payload, "SEND_MESSAGE", false);
    }


    // PRIVATE

    private void sendMessage(String dni, Object response, String type, boolean workflow) {
        try {
            //Build message.
            LongIdMessage message = LongIdMessage.builder()
                    .id(1l)
                    .idS(dni)
                    .objectJson(this.getJson(response))
                    .workflow(workflow).build();
            //Send message.
            if (workflow) {
                this.applicationProcessor.output().send
                        (
                                MessageUtil.message(message, type, true)
                        );
            } else {
                this.applicationProcessor.output().send
                        (
                                MessageUtil.message(message, type, false)
                        );
            }

        } catch (Exception e) {
            log.error("createUser: error [{}] ", e.getMessage());
        }
    }


    /**
     * Get json string from Object
     *
     * @param obj Object to get json string
     * @return json string from Object
     */
    private String getJson(Object obj) {
        if (obj == null) {
            return "";
        }
        String json = null;
        try {
            json = new ObjectMapper().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.error("getJson: error [{}] ", e.getMessage());
        }
        log.info("getJson: json [{}] ", json);
        return json;
    }
}
