package com.jmr.stream.demostream.controller;


import com.jmr.stream.demostream.service.TrackerService;
import com.jmr.stream.demostream.util.Constants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

import static com.jmr.stream.demostream.util.Constants.APP_JSON;

/**
 * Tracker controller.
 * <p>
 * Calls to Consumer Tracker micro-service
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = Constants.API_VERSION)
@CrossOrigin(origins = "*", allowCredentials = "true", methods = {
        RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.OPTIONS})
public class TrackerController {


    /**
     * Tracker Service
     */
    private final TrackerService service;
    /**
     * Post Data
     * Send a string to another micro-service, using Open Feign
     * @return String
     */
    @Operation(summary = "Post DATA")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Post DATA",
                    content = @Content(
                            mediaType = APP_JSON,
                            schema = @Schema(
                                    implementation = String.class
                            ))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
    })
    @PostMapping("/data")
    @Tag(name = "Tracker")
    public ResponseEntity<String> createData
    (
            @Parameter(description = "data", required = true)
            @NotNull @RequestBody String payload
    ) {
        String responseEntity = service.createData(payload);
        log.info("response createData: {}", responseEntity);

        return ResponseEntity.ok()
                .body(responseEntity);
    }
}
