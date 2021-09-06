package com.jmr.stream.demostream.client;


import feign.Headers;
import feign.RequestLine;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

/**
 * Client for calling to consumerTracker using open feign
 *
 */
public interface TrackerAPI {


    @RequestLine("POST /data")
    @Headers("Content-Type: application/json")
    Mono<ResponseEntity<Mono<String>>> create(String payload);
}
