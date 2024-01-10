package com.gbx.gbxchallengejava.orm;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "transacoes")
public class Transaction {

    //com conta de origem, conta de destino, valor e data da transação.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user-origin-id")
    private User origin;

    @ManyToOne
    @JoinColumn(name = "user-destination-id")
    private User destination;
    private BigDecimal value;
    private LocalDateTime date;
}