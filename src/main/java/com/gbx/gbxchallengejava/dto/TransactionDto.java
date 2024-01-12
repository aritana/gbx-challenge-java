package com.gbx.gbxchallengejava.dto;

import com.gbx.gbxchallengejava.orm.Transaction;
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
public class TransactionDto {
    private String userOriginId;
    private String userDestinationId;
    private String value;
    private String date;

    public static TransactionDto convertToDto(Transaction transaction) {
        return TransactionDto.builder()
                .userOriginId(String.valueOf(transaction.getOrigin()))
                .userDestinationId(String.valueOf(transaction.getDestination()))
                .value(String.valueOf(transaction.getValue()))
                .date(String.valueOf(transaction.getDate()))
                .build();
    }
    public static List<TransactionDto> convertToDtoList(List<Transaction> transactionIterable) {
        return StreamSupport.stream(transactionIterable.spliterator(), false)
                .map(TransactionDto::convertToDto)
                .collect(Collectors.toList());
    }
}