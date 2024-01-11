package com.gbx.gbxchallengejava.repository;

import com.gbx.gbxchallengejava.orm.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.PropertySource;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@PropertySource("classpath:application-test.properties")
class UserRepositoryTest {

    private User user1;
    private User user2;

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    void setUp() {
        createUsers();
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    public void shouldFindUserByIdTest() {
        Iterable<User> userIterable = userRepository.findAll();
        List<User> users = new ArrayList<>();
        userIterable.forEach(users::add);
        assertNotNull(users);
        assertFalse(users.isEmpty());
        assertEquals(2, users.size());
    }

    @Test
    public void shouldListUsersTest() {
        Optional<User> optionalUser = userRepository.findById(1l);
        assertTrue(optionalUser.isPresent());
        assertEquals("Machado de Assis", optionalUser.get().getName());
        assertEquals(123456, optionalUser.get().getAccountNumber());
        assertEquals(new BigDecimal(100), optionalUser.get().getBalance());
    }

    public void createUsers() {
        user1 = User.builder()
                .name("Machado de Assis")
                .accountNumber(123456)
                .balance(new BigDecimal(100))
                .build();
        user2 = User.builder()
                .name("Diadorim")
                .accountNumber(123457)
                .balance(new BigDecimal(100))
                .build();
        userRepository.saveAll(List.of(user1, user2));
    }
}