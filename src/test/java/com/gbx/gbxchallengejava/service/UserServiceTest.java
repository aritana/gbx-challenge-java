package com.gbx.gbxchallengejava.service;

import com.gbx.gbxchallengejava.dto.UserDto;
import com.gbx.gbxchallengejava.exception.UserNotFoundException;
import com.gbx.gbxchallengejava.orm.User;
import com.gbx.gbxchallengejava.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.EmptyResultDataAccessException;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

class CrudUserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CrudUserService crudUserService;

    private User user1;
    private User user2;
    private UserDto userDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        createUsers();
        createUserDto();
    }

    @Test
    void shoudUserBeCreatedTest() {
        assertDoesNotThrow(() -> crudUserService.createUser(userDto));
        Mockito.verify(userRepository, Mockito.times(1)).save(any(User.class));
    }

    @Test
    void shouldThrowsExceptionIfErrorCreateUserTest() {
        doThrow(RuntimeException.class).when(userRepository).save(any(User.class));
        assertThrows(UserNotFoundException.class, () -> crudUserService.createUser(userDto));
    }

    @Test
    void shouldUserBeUpdatedTest() {
        UserDto userDto = new UserDto();
        userDto.setId("1");
        userDto.setName("Updated User");
        userDto.setAccountNumber("654321");
        userDto.setBalance("200");

        when(userRepository.save(any(User.class))).thenReturn(new User());
        assertDoesNotThrow(() -> crudUserService.updateUser(userDto));
        Mockito.verify(userRepository, Mockito.times(1)).save(any(User.class));
    }

    @Test
    void shouldThrowsExceptionIfErrorUpdateUserTest() {
        doThrow(RuntimeException.class).when(userRepository).save(any(User.class));
        assertThrows(UserNotFoundException.class, () -> crudUserService.updateUser(userDto));
    }

    @Test
    void shouldUsersBeListedTest() {
        when(userRepository.findAll()).thenReturn(Arrays.asList(
                user1,
                user2
        ));
        List<UserDto> userDtoList = crudUserService.listUsers();
        assertNotNull(userDtoList);
        assertEquals(2, userDtoList.size());
    }

    @Test
    void shouldThrowsExceptionIfUserNotListedTest() {
        doThrow(RuntimeException.class).when(userRepository).findAll();
        assertThrows(UserNotFoundException.class, () -> crudUserService.updateUser(userDto));
    }

    @Test
    void shouldFindUserByIdTest() {
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.of(user1));
        User foundUser = crudUserService.findUserById(userId);
        assertNotNull(foundUser);
        assertEquals(userId, foundUser.getId());
    }

    @Test
    void shouldThrowsExceptionIfFindUserByIdNotFoundTest() {
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> crudUserService.findUserById(userId));
    }

    @Test
    void shoudDeleteUserTest() {
        Long userId = 1L;
        assertDoesNotThrow(() -> crudUserService.deleteUser(userId));
        Mockito.verify(userRepository, Mockito.times(1)).deleteById(userId);
    }

    @Test
    void shouldThrowsExceptionIfDeleteUserNotFoundTest() {
        Long userId = 1L;
        doThrow(new EmptyResultDataAccessException(1)).when(userRepository).deleteById(userId);
        assertThrows(UserNotFoundException.class, () -> crudUserService.deleteUser(userId));
    }

    public void createUsers() {
        user1 = User.builder()
                .id(1L)
                .name("Machado de Assis")
                .accountNumber(123456)
                .balance(new BigDecimal(100))
                .build();
        user2 = User.builder()
                .id(2L)
                .name("Diadorim")
                .accountNumber(123457)
                .balance(new BigDecimal(100))
                .build();
        userRepository.saveAll(List.of(user1, user2));
    }

    public void createUserDto() {
        userDto = new UserDto();
        userDto.setName("John Doe");
        userDto.setAccountNumber("123456");
        userDto.setBalance("100");
    }
}