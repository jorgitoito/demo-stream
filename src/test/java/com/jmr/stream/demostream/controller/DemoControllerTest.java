package com.jmr.stream.demostream.controller;

import com.jmr.stream.demostream.model.dto.UserDTO;
import com.jmr.stream.demostream.model.entity.UserEntity;
import com.jmr.stream.demostream.service.EvenService;
import com.jmr.stream.demostream.service.UserServiceBusiness;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DemoControllerTest {

    @Mock
    private UserServiceBusiness userService;

    @Mock
    private EvenService eventsService;

    @InjectMocks
    private DemoController controller;


    @Test
    void getUsers() {
        // given
        List<UserDTO> listaUsers = new ArrayList<>();
        UserDTO user1 = new UserDTO();
        user1.setDni("12345T");
        user1.setName("Pepe");
        listaUsers.add(user1);
        // when
        when(userService.getUsers()).thenReturn(listaUsers);
        // test
        ResponseEntity<List<UserDTO>> response = controller.getUsers();
        // Check
        assertNotNull(response);
    }

    @Test
    void createUser() {
        UserDTO user = new UserDTO();
        user.setDni("1234V");
        user.setName("PEPE");
        ResponseEntity<UserDTO> response = controller.createUser(user);
        // Check
        assertNotNull(response);
    }

    @Test
    void createUser_2() {
        UserDTO user = new UserDTO();
        user.setDni("1234V");
        user.setName("PEPE");
        // when
        when(userService.createUser(any(UserDTO.class))).thenReturn(user);
        // test
        ResponseEntity<UserDTO> response = controller.createUser(user);
        // Check
        assertNotNull(response);
    }
}