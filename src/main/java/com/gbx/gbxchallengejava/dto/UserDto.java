package com.gbx.gbxchallengejava.dto;

import com.gbx.gbxchallengejava.orm.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserDto {
    private String id;
    private String name;
    private String accountNumber;
    private String balance;

    public static UserDto convertToDto(User user) {
        return UserDto.builder()
                .id(String.valueOf(user.getId()))
                .name(user.getName())
                .accountNumber(String.valueOf(user.getAccountNumber()))
                .balance(String.valueOf(user.getBalance()))
                .build();
    }

    public static List<UserDto> convertToDtoList(Iterable<User> usersIterable) {
        return StreamSupport.stream(usersIterable.spliterator(), false)
                .map(UserDto::convertToDto)
                .collect(Collectors.toList());
    }
}