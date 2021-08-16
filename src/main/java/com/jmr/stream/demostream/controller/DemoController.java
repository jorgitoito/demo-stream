package com.jmr.stream.demostream.controller;


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
import org.springframework.http.CacheControl;
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
    @Operation(summary = "Get User By DNI")
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
    @GetMapping("/users/{user_dni}")
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
    @Operation(summary = "Create User ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Create User",
                    content = @Content(
                            mediaType = APP_JSON,
                            schema = @Schema(
                                    implementation = UserEntity.class
                            ))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
    })
    @PostMapping("/users")
    @Tag(name = "Users")
    public ResponseEntity<UserEntity> createUser
    (
            @Parameter(description = "User data") @Valid @NotNull @RequestBody UserEntity payload
    ) {
        log.info("response payload: {}", payload);
        UserEntity responseEntity = userService.createUser(payload);
        log.info("response createUser: {}", responseEntity);

        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(MAX_AGE, TimeUnit.SECONDS))
                .body(responseEntity);
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


}
