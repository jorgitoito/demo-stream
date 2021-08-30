package com.jmr.stream.demostream.controller;


import com.jmr.stream.demostream.model.dto.UserDTO;
import com.jmr.stream.demostream.model.entity.EventEntity;
import com.jmr.stream.demostream.model.entity.UserEntity;
import com.jmr.stream.demostream.service.EvenService;
import com.jmr.stream.demostream.service.UserServiceBusiness;
import com.jmr.stream.demostream.util.Constants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.jmr.stream.demostream.util.Constants.APP_JSON;

/**
 * Demo controller.
 * <p>
 * Calls to Demo
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = Constants.API_VERSION)
@CrossOrigin(origins = "*", allowCredentials = "true", methods = {
        RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.OPTIONS})
@Validated
public class DemoController {

    /**
     * cache. Avoid double / triple clicks or multiple calls with the same parameters very closely
     */
    public static final int MAX_AGE = 5;

    /**
     * UserService
     */
    private final UserServiceBusiness userService;

    /**
     * UserService
     */
    private final EvenService eventsService;

    /**
     * Get Users
     *
     * @return Users list
     */
    @Operation(summary = "Get Users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get Users",
                    content = @Content(
                            mediaType = APP_JSON,
                            array = @ArraySchema(
                                    schema = @Schema(
                                            implementation = UserEntity.class
                                    )))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
    })
    @GetMapping("/users")
    @Tag(name = "Users")
    public ResponseEntity<List<UserEntity>> getUsers() {
        List<UserEntity> responseEntity = userService.getUsers();
        log.info("response getUsers: {}", responseEntity);

        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(MAX_AGE, TimeUnit.SECONDS))
                .body(responseEntity);
    }


    /**
     * Get User By DNI
     *
     * @param dni serie de 7 u 8 numeros seguidos de una letra de control = modulo 23
     * @return User
     */
    @Operation(summary = "Get User By DNI",
            description = "Get User By DNI ; dni will be checked")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get User By DNI",
                    content = @Content(
                            mediaType = APP_JSON,
                            schema = @Schema(
                                    implementation = UserEntity.class
                            ))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
    })
    @GetMapping(value = "/users/{user_dni}", produces = {"application/json"})
    @Tag(name = "Users")
    public ResponseEntity<UserEntity> getUserByDni
    (
            @Parameter(description = "User DNI") @PathVariable(name = "user_dni") @NotBlank @Size(min = 8, max = 9) final String dni
    ) {
        log.info("response getUserByDni: {}", dni);
        UserEntity responseEntity = userService.getUserByDni(dni);
        log.info("response getUserByDni: {}", responseEntity);

        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(MAX_AGE, TimeUnit.SECONDS))
                .body(responseEntity);
    }


    /**
     * Create User
     *
     * @param payload User data
     * @return User created
     */
    @Operation(summary = "Create User")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created",
                    content = @Content(
                            mediaType = APP_JSON,
                            schema = @Schema(
                                    implementation = UserEntity.class
                            ))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
    })
    @PostMapping(value = "/users", consumes = {"application/json"})
    @Tag(name = "Users")
    public ResponseEntity<UserDTO> createUser
    (
            @Parameter(description = "User data", required = true, schema = @Schema(implementation = UserEntity.class))
            @Valid @NotNull @RequestBody UserDTO payload
    ) {
        log.info("createUser payload: {}", payload);
        UserDTO responseEntity = userService.createUser(payload);
        log.info("createUser responseEntity: {}", responseEntity);
        return new ResponseEntity<>(responseEntity, HttpStatus.CREATED);
    }


    /**
     * Get Events
     *
     * @return Events list
     */
    @Operation(summary = "Get Events")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get Events",
                    content = @Content(
                            mediaType = APP_JSON,
                            array = @ArraySchema(
                                    schema = @Schema(
                                            implementation = EventEntity.class
                                    )))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
    })
    @GetMapping("/events")
    @Tag(name = "Events")
    public ResponseEntity<List<EventEntity>> getEvents() {
        List<EventEntity> responseEntity = eventsService.getEvents();
        log.info("response getEvents: {}", responseEntity);

        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(MAX_AGE, TimeUnit.SECONDS))
                .body(responseEntity);
    }


    /**
     * Get Events by type
     *
     * @return Events list
     */
    @Operation(summary = "Get Events by type")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get Events by type",
                    content = @Content(
                            mediaType = APP_JSON,
                            array = @ArraySchema(
                                    schema = @Schema(
                                            implementation = EventEntity.class
                                    )))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
    })
    @GetMapping("/events/bytype")
    @Tag(name = "Events")
    public ResponseEntity<List<EventEntity>> getEventsByType
    (
            @Parameter(description = "type") @Valid @NotBlank @Size(min = 2, max = 50) @RequestParam final String type
    ) {
        log.info("getEventsByType type: {}", type);
        List<EventEntity> responseEntity = eventsService.getEventsByType(type);
        log.debug("getEventsByType response: {}", responseEntity);

        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(MAX_AGE, TimeUnit.SECONDS))
                .body(responseEntity);
    }


}
