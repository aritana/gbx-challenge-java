package com.gbx.gbxchallengejava.service;

import com.gbx.gbxchallengejava.dto.UserDto;
import com.gbx.gbxchallengejava.exception.UserNotFoundException;
import com.gbx.gbxchallengejava.orm.User;
import com.gbx.gbxchallengejava.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class CrudUserService {
    @Autowired
    UserRepository userRepository;

    private String name;
    private Integer accountNumber;
    private BigDecimal balance;
    private Long id;

    public void createUser(UserDto userDto) {
        try {
            name = userDto.getName();
            accountNumber = Integer.parseInt(userDto.getAccountNumber());
            balance = new BigDecimal(userDto.getBalance());

            User user = User.builder()
                    .name(name)
                    .accountNumber(accountNumber)
                    .balance(balance)
                    .build();
            userRepository.save(user);

        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new UserNotFoundException("Not possible to create User", e);
        }
    }

    public void updateUser(UserDto userDto) {
        try {
            name = userDto.getName();
            accountNumber = Integer.parseInt(userDto.getAccountNumber());
            balance = new BigDecimal(userDto.getBalance());
            id = Long.parseLong(userDto.getId());
            User user = User.builder()
                    .id(id)
                    .name(name)
                    .accountNumber(accountNumber)
                    .balance(balance)
                    .build();
            userRepository.save(user);
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new UserNotFoundException("Not possible to update User", e);
        }
    }

    public List<UserDto> listUsers() {
        try {
            Iterable<User> usersIterable = userRepository.findAll();
            return UserDto.convertToDtoList(usersIterable);
        } catch (DataAccessException e) {
            e.printStackTrace();
            throw new UserNotFoundException("Not possible to list Users", e);
        }
    }

    public User findUserById(Long id) {
        try {
            Optional<User> userOptional = userRepository.findById(id);
            if (userOptional.isPresent()) {
                return userOptional.get();
            } else {
                throw new UserNotFoundException("User not found with ID: " + id);
            }
        } catch (DataAccessException e) {
            e.printStackTrace();
            throw new UserNotFoundException("Not possible to find User", e);
        }
    }

    public void deleteUser(Long id) {
        try {
            userRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new UserNotFoundException("User not found with ID: " + id, e);
        } catch (DataAccessException e) {
            e.printStackTrace();
            throw new UserNotFoundException("Not possible to delete User", e);
        }
    }
}