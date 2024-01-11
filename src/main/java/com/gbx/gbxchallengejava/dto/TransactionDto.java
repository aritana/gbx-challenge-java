package com.gbx.gbxchallengejava.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class TransactionDto {
    private String accountOrigin;
    private String accountDestination;
    private String value;
    private String date;
}