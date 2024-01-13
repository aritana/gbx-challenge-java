package com.gbx.gbxchallengejava.controller;

import com.gbx.gbxchallengejava.dto.UserDto;
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
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto user) {
        UserDto userDto = UserDto.convertToDto(userService.createUser(user));
        return new ResponseEntity<>(userDto, HttpStatus.CREATED);
    }

    @PutMapping(value = "/atualizarUsuario")
    @Transactional
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto user) {
        UserDto userDto = UserDto.convertToDto(userService.updateUser(user));
        return new ResponseEntity(userDto, HttpStatus.OK);
    }

    @GetMapping(value = "/listarUsuarios")
    public ResponseEntity<List<UserDto>> listUsers() {
        List<UserDto> usersDto = userService.listUsers();
        return new ResponseEntity(usersDto, HttpStatus.OK);
    }

    @GetMapping(value = "/encontrarUsuario/{id}")
    public ResponseEntity<UserDto> findUserById(@PathVariable("id") String id) {
        UserDto userDto = UserDto.convertToDto(userService.findUserById(Long.parseLong(id)));
        return new ResponseEntity(userDto, HttpStatus.OK);
    }

    @GetMapping(value = "/deletarUsuario/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") String id) {
        userService.deleteUser(Long.parseLong(id));
        return ResponseEntity.noContent().build();
    }
}