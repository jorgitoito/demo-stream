package com.jmr.stream.demostream.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jmr.stream.demostream.model.dto.UserDTO;
import com.jmr.stream.demostream.model.entity.UserEntity;
import com.jmr.stream.demostream.stream.dto.LongIdMessage;
import com.jmr.stream.demostream.util.MessageUtil;
import com.jmr.stream.demostream.util.NullChecker;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.modelmapper.ModelMapper;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class UserServiceBusiness {

    private final UserService service;

    /**
     * application processor
     */
    private final Processor applicationProcessor;


    public List<UserEntity> getUsers() {
        log.info("getUsers");
        return service.getUsers();
    }


    public UserEntity getUserByDni(@NotEmpty String dni) {
        log.info("getUserByDni: dni [{}] ", dni);
        UserEntity result = service.getUserByDni(dni);
        NullChecker.checkNull_NOT_FOUND(result, "User not found with dni: " + dni);
        return result;
    }

    /**
     * Create user if dni is new one and send message
     *
     * @param payload User data
     * @return User created
     */
    public UserDTO createUser(@NotNull UserDTO payload) {
        log.info("createUser: payload [{}] ", payload);
        // Hide Entity details using a DTO.
        ModelMapper modelMapper = new ModelMapper();
        UserEntity payloadUser = modelMapper.map(payload, UserEntity.class);
        log.info("createUser: payloadUser [{}] ", payloadUser);
        UserEntity user = service.getUserByDni(payloadUser.getDni());
        log.debug("createUser: user [{}] ", user);
        // user getUserByDni is not null: error. Must not exist with this DNI.
        NullChecker.checkNoNull_BAD_REQUEST(user, "User´s DNI exist already: " + payload.getDni());
        // It is a new user/dni. lets to create it.
        UserEntity response = service.createUser(payloadUser);
        log.debug("createUser: user created [{}] ", response);
        // record this event sending a message
        this.sendMessage(payloadUser, response, "CREATE_USER");
        return modelMapper.map(response, UserDTO.class);
    }


    // PRIVATE

    private void sendMessage(UserEntity payload, UserEntity response, String type) {
        try {
            //Send message.
            LongIdMessage message = LongIdMessage.builder()
                    .id(1l)
                    .idS(payload.getDni())
                    .objectJson(this.getJson(response))
                    .workflow(false).build();
            this.applicationProcessor.output().send
                    (
                            MessageUtil.message(message, type)
                    );
        } catch (Exception e) {
            log.error("createUser: error [{}] ", e.getMessage());
        }
    }


    private String getJson(@NotNull Object obj) {
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
