package com.gbx.gbxchallengejava.service;

import com.gbx.gbxchallengejava.dto.UserRequestDto;
import com.gbx.gbxchallengejava.dto.UserResponseDto;
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

class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User user1;
    private User user2;
    private UserRequestDto userRequestDto;
    private UserResponseDto userResponseDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        createUsers();
        createUsersDto();
    }

    @Test
    void shoudUserBeCreatedTest() {
        assertDoesNotThrow(() -> userService.createUser(userRequestDto));
        Mockito.verify(userRepository, Mockito.times(1)).save(any(User.class));
    }

    @Test
    void shouldThrowsExceptionIfErrorCreateUserTest() {
        doThrow(RuntimeException.class).when(userRepository).save(any(User.class));
        assertThrows(UserNotFoundException.class, () -> userService.createUser(userRequestDto));
    }

    @Test
    void shouldUserBeUpdatedTest() {
        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setId("1");
        userResponseDto.setName("Updated User");
        userResponseDto.setAccountNumber("654321");
        userResponseDto.setBalance("200");

        when(userRepository.save(any(User.class))).thenReturn(new User());
        assertDoesNotThrow(() -> userService.updateUser(userResponseDto));
        Mockito.verify(userRepository, Mockito.times(1)).save(any(User.class));
    }

    @Test
    void shouldThrowsExceptionIfErrorUpdateUserTest() {
        doThrow(RuntimeException.class).when(userRepository).save(any(User.class));
        assertThrows(UserNotFoundException.class, () -> userService.updateUser(userResponseDto));
    }

    @Test
    void shouldUsersBeListedTest() {
        when(userRepository.findAll()).thenReturn(Arrays.asList(
                user1,
                user2
        ));
        List<UserResponseDto> userResponseDtoList = userService.listUsers();
        assertNotNull(userResponseDtoList);
        assertEquals(2, userResponseDtoList.size());
    }

    @Test
    void shouldThrowsExceptionIfUserNotListedTest() {
        doThrow(UserNotFoundException.class).when(userRepository).findAll();
        assertThrows(UserNotFoundException.class, () -> userService.listUsers());
    }

    @Test
    void shouldFindUserByIdTest() {
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.of(user1));
        User foundUser = userService.findUserById(userId);
        assertNotNull(foundUser);
        assertEquals(userId, foundUser.getId());
    }

    @Test
    void shouldThrowsExceptionIfFindUserByIdNotFoundTest() {
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userService.findUserById(userId));
    }

    @Test
    void shoudDeleteUserTest() {
        Long userId = 1L;
        assertDoesNotThrow(() -> userService.deleteUser(userId));
        Mockito.verify(userRepository, Mockito.times(1)).deleteById(userId);
    }

    @Test
    void shouldThrowsExceptionIfDeleteUserNotFoundTest() {
        Long userId = 1L;
        doThrow(new EmptyResultDataAccessException(1)).when(userRepository).deleteById(userId);
        assertThrows(UserNotFoundException.class, () -> userService.deleteUser(userId));
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
    }

    public void createUsersDto() {
        userRequestDto = UserRequestDto.builder()
                .name("John Doe")
                .accountNumber("123456")
                .balance("100")
                .build();

        userResponseDto = UserResponseDto.builder()
                .id("1")
                .name("John Doe")
                .accountNumber("123456")
                .balance("100")
                .build();
    }
}