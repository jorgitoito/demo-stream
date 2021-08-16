package com.jmr.stream.demostream.service;

import com.jmr.stream.demostream.model.entity.UserEntity;
import com.jmr.stream.demostream.model.repository.UserEntityRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * User Service
 */
@Slf4j
@Service
@AllArgsConstructor
public class UserService {

    private final UserEntityRepository repository;

    public List<UserEntity> getUsers() {
        log.info("getUsers");
        return repository.findAll();

    }

    public UserEntity getUserByDni(String dni) {
        log.info("getUserByDni: dni [{}] ", dni);
        UserEntity result = repository.findFirstByDni(dni);
        log.info("getUserByDni: result [{}] ", result);
        return result;
    }

    public UserEntity createUser(UserEntity payload) {
        log.info("createUser: payload [{}] ", payload);
        UserEntity result = repository.save(payload);
        log.info("createUser: result [{}] ", result);
        return result;
    }

    // PRIVATE


}
