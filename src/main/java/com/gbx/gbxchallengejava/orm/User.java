package com.gbx.gbxchallengejava.orm;

import jakarta.persistence.*;
import lombok.*;

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

    @ToString.Exclude
    @OneToMany(mappedBy = "destination")
    private List<Transaction> transactionsSent;

    @ToString.Exclude
    @OneToMany(mappedBy = "origin")
    private List<Transaction> transactionsReceived;
}