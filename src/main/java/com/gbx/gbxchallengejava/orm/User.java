package com.gbx.gbxchallengejava.orm;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "usuarios")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer accountNumber;
    private BigDecimal balance;

    @OneToMany(mappedBy = "destination")
    private List<Transaction> transactionsSent;

    @OneToMany(mappedBy = "origin")
    private List<Transaction> transactionsReceived;
}