package com.jmr.stream.demostream.service;


import com.jmr.stream.demostream.model.dto.UserDTO;
import com.jmr.stream.demostream.model.entity.UserEntity;
import com.jmr.stream.demostream.util.NullChecker;
import com.jmr.stream.demostream.util.ServiceUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class UserServiceBusiness {

    private final UserService service;

    /**
     * application processor
     */
    private final Processor applicationProcessor;

    /**
     * Model mapper
     */
    private final ModelMapper modelMapper;

    /**
     * Util class
     */
    private final ServiceUtil serviceUtil;

    /**
     * Get Users
     *
     * @return User list
     */
    public List<UserDTO> getUsers() {
        log.info("getUsers");
        // return UserDTO objects
        return service.getUsers().stream()
                .map(r -> modelMapper.map(r, UserDTO.class))
                .collect(Collectors.toList());

    }

    /**
     * Get User by DNI
     *
     * @param dni dni
     * @return User
     */
    public UserDTO getUserByDni(@NotEmpty String dni) {
        log.info("getUserByDni: dni [{}] ", dni);
        UserEntity result = service.getUserByDni(dni);
        NullChecker.checkNull_NOT_FOUND(result, "User not found with dni: " + dni);
        return modelMapper.map(result, UserDTO.class);
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
        serviceUtil.sendMessage(payloadUser.getDni(), response, "CREATE_USER", false, applicationProcessor);
        return modelMapper.map(response, UserDTO.class);
    }

    /**
     * Delete User by DNI
     *
     * @param dni DNI
     */
    public void deleteUserByDni(String dni) {
        UserEntity result = service.getUserByDni(dni);
        NullChecker.checkNull_NOT_FOUND(result, "User not found with dni: " + dni);
        service.deleteUser(result);
        // once delete is done, send message: workflow : 2 steps: 1 DELETE_USER event and 2 Send Notification
        serviceUtil.sendMessage(dni, null, "DELETE_USER_FINISHED", true, applicationProcessor);
        log.debug("deleteUserByDni: deleted");
    }

    // PRIVATE

}
