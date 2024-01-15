package com.gbx.gbxchallengejava.controller;

import com.gbx.gbxchallengejava.dto.UserRequestDto;
import com.gbx.gbxchallengejava.dto.UserResponseDto;
import com.gbx.gbxchallengejava.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping(value = "/adicionarUsuario")
    @Transactional
    public ResponseEntity<UserResponseDto> createUser(@RequestBody UserRequestDto user) {
        UserResponseDto userResponseDto = UserResponseDto.convertToDto(userService.createUser(user));
        return new ResponseEntity<>(userResponseDto, HttpStatus.CREATED);
    }

    @PutMapping(value = "/atualizarUsuario")
    @Transactional
    public ResponseEntity<UserResponseDto> updateUser(@RequestBody UserResponseDto user) {
        UserResponseDto userResponseDto = UserResponseDto.convertToDto(userService.updateUser(user));
        return new ResponseEntity(userResponseDto, HttpStatus.OK);
    }

    @GetMapping(value = "/listarUsuarios")
    public ResponseEntity<List<UserResponseDto>> listUsers() {
        List<UserResponseDto> usersDto = userService.listUsers();
        return new ResponseEntity(usersDto, HttpStatus.OK);
    }

    @GetMapping(value = "/encontrarUsuario/{id}")
    public ResponseEntity<UserResponseDto> findUserById(@PathVariable("id") String id) {
        UserResponseDto userResponseDto = UserResponseDto.convertToDto(userService.findUserById(Long.parseLong(id)));
        return new ResponseEntity(userResponseDto, HttpStatus.OK);
    }

    @GetMapping(value = "/deletarUsuario/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") String id) {
        userService.deleteUser(Long.parseLong(id));
        return ResponseEntity.noContent().build();
    }
}