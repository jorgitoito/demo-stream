package com.jmr.stream.demostream.service;

import com.jmr.stream.demostream.client.TrackerAPI;
import com.jmr.stream.demostream.util.ServiceUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    private final ServiceUtil serviceUtil;


    public String createData(String payload) {
        log.info("Init createData");
        ResponseEntity<Mono<String>> response;
        try {
            response = trackerAPI.create(payload).block();
            log.debug("response createData: {}", response);
        } catch (Exception e) {
            //Log error, create ResponseStatusException and throw it
            throw serviceUtil.getResponseStatusException(e, "Error getting GlobalTasks list");
        }
        // response here can not be null
        if (serviceUtil.checkResponse(response)) {
            return Objects.requireNonNull(response.getBody()).block();
        }
        log.warn("createData: response is NOT is2xxSuccessful, response: {}", response);
        return null;
    }
}
