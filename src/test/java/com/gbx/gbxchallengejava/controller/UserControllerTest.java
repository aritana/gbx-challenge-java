package com.gbx.gbxchallengejava.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gbx.gbxchallengejava.dto.UserDto;
import com.gbx.gbxchallengejava.orm.User;
import com.gbx.gbxchallengejava.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class UserControllerTest {
    @Mock
    private UserService userService;
    @InjectMocks
    private UserController userController;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private User user1, user2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        this.objectMapper = new ObjectMapper();
        createUser();
    }
    @Test
    void shouldCreateUserTest() throws Exception {
        UserDto userDto = new UserDto("1", "Created User", "123", "100");
        doReturn(user1).when(userService).createUser(any(UserDto.class));

        mockMvc.perform(post("/adicionarUsuario")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value("Created User"))
                .andExpect(jsonPath("$.accountNumber").value("123"))
                .andExpect(jsonPath("$.balance").value("100"));
    }
    @Test
    void shouldUpdateUserTest() throws Exception {
        UserDto userDto = new UserDto("2", "Updated User", "124", "150");
        doReturn(user2).when(userService).updateUser(any(UserDto.class));

        mockMvc.perform(put("/atualizarUsuario")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("2"))
                .andExpect(jsonPath("$.name").value("Updated User"))
                .andExpect(jsonPath("$.accountNumber").value("124"))
                .andExpect(jsonPath("$.balance").value("150"));
    }
    @Test
    void shouldListUsersTest() throws Exception {
        UserDto userDto = new UserDto("3", "Listed User", "125", "100.00");
        doReturn(Collections.singletonList(userDto)).when(userService).listUsers();

        mockMvc.perform(get("/listarUsuarios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("3"))
                .andExpect(jsonPath("$[0].name").value("Listed User"))
                .andExpect(jsonPath("$[0].accountNumber").value("125"))
                .andExpect(jsonPath("$[0].balance").value("100.00"));
    }
    @Test
    void shouldFindUserByIdTest() throws Exception {
        doReturn(user1).when(userService).findUserById(1L);

        mockMvc.perform(get("/encontrarUsuario/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value("Created User"))
                .andExpect(jsonPath("$.accountNumber").value("123"))
                .andExpect(jsonPath("$.balance").value("100"));
    }
    @Test
    void shouldDeleteUserTest() throws Exception {
        mockMvc.perform(get("/deletarUsuario/1"))
                .andExpect(status().isNoContent());
    }
    private void createUser() {
        user1 = User.builder()
                .id(1l)
                .name("Created User")
                .accountNumber(123)
                .balance(new BigDecimal(100))
                .build();

        user2 = User.builder()
                .id(2l)
                .name("Updated User")
                .accountNumber(124)
                .balance(new BigDecimal(150))
                .build();
    }
}