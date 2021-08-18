package com.jmr.stream.demostream.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jmr.stream.demostream.model.entity.UserEntity;
import com.jmr.stream.demostream.service.EvenService;
import com.jmr.stream.demostream.service.UserServiceBusiness;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * DemoControllerTest_2 help us to test REST API and validation:
 *
 * @Valid and @Validated annotation
 */
@RunWith(SpringRunner.class)
@WebMvcTest(DemoController.class)
public class DemoControllerTest_2 {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserServiceBusiness userService;

    @MockBean
    private EvenService evenService;

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    public void getUsers() throws Exception {
        // given
        List<UserEntity> listaUsers = new ArrayList<>();
        UserEntity user1 = new UserEntity();
        user1.setDni("03514454P");
        user1.setName("Pepe");
        listaUsers.add(user1);
        // when
        given(userService.getUsers()).willReturn(listaUsers);
        // test
        String res = mockMvc.perform(get("/users")
                .content(mapper.writeValueAsString(listaUsers))
                .contentType(APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        // check
        ArrayList responseUser = mapper.readValue(res, ArrayList.class);
        assertEquals("{dni=03514454P, name=Pepe}", responseUser.get(0).toString());
    }

    @Test
    public void createUser() throws Exception {
        // given
        List<UserEntity> listaUsers = new ArrayList<>();
        UserEntity user1 = new UserEntity();
        user1.setDni("03514454P");
        user1.setName("Pepe");
        listaUsers.add(user1);
        // when
        given(userService.createUser(user1)).willReturn(user1);
        // test
        String res = mockMvc.perform(post("/users")
                .content(mapper.writeValueAsString(user1))
                .contentType(APPLICATION_JSON)
        )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();
        // check
        UserEntity responseUser = mapper.readValue(res, UserEntity.class);
        assertEquals(user1, responseUser);
    }

    @Test
    public void createUser_wrong_dni_number() throws Exception {
        // given
        List<UserEntity> listaUsers = new ArrayList<>();
        UserEntity user1 = new UserEntity();
        // wrong dni number
        user1.setDni("03514459P");
        user1.setName("Pepe");
        listaUsers.add(user1);
        // when
        given(userService.createUser(user1)).willReturn(user1);
        // test
        String res = mockMvc.perform(post("/users")
                .content(mapper.writeValueAsString(user1))
                .contentType(APPLICATION_JSON)
        )
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();
        // check
        assertTrue(res.contains("Dni erroneo"));
    }


    @Test
    public void createUser_name_is_Blank() throws Exception {
        // given
        List<UserEntity> listaUsers = new ArrayList<>();
        UserEntity user1 = new UserEntity();
        user1.setDni("03514454P");
        // name is blank: error
        user1.setName("");
        listaUsers.add(user1);
        // when
        given(userService.createUser(user1)).willReturn(user1);

        // test
        String res = mockMvc.perform(post("/users")
                .content(mapper.writeValueAsString(user1))
                .contentType(APPLICATION_JSON)
        )
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();
        // check
        assertTrue(res.contains("name: must not be blank"));
    }
}
