package com.gbx.gbxchallengejava.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Builder
public class TransactionRequestDto {
    private String userOriginId;
    private String userDestinationId;
    private String value;

    public TransactionRequestDto(String userOriginId, String userDestinationId, String value) {
        this.userOriginId = userOriginId;
        this.userDestinationId = userDestinationId;
        this.value = value;
    }
}