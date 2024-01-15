package com.gbx.gbxchallengejava.controller;

import com.gbx.gbxchallengejava.dto.TransactionRequestDto;
import com.gbx.gbxchallengejava.dto.TransactionResponseDto;
import com.gbx.gbxchallengejava.service.TransactionService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @PostMapping(value = "/criarTransacao")
    @Transactional
    public ResponseEntity<TransactionResponseDto> createTransaction(@RequestBody TransactionRequestDto transaction) {
        TransactionResponseDto transactionResponseDto = TransactionResponseDto.convertToDto(transactionService.createTransaction(transaction));
        return new ResponseEntity(transactionResponseDto, HttpStatus.CREATED);
    }

    @GetMapping(value = "/ConsultarTransacoes")
    public ResponseEntity<List<TransactionResponseDto>> listTransactions() {
        List<TransactionResponseDto> transactionsDto = transactionService.listAllTransactions();
        return new ResponseEntity<>(transactionsDto, HttpStatus.OK);
    }

    @GetMapping(value = "/encontrarTransacoesUsuarioDestino/{id}")
    public ResponseEntity<List<TransactionResponseDto>> findTransactionsByDestination(@PathVariable("id") Long id) {
        List<TransactionResponseDto> transactionsDto = transactionService.findTransactionsByDestination(id);
        return new ResponseEntity<>(transactionsDto, HttpStatus.OK);
    }

    @GetMapping(value = "/encontrarTransacoesUsuarioOrigem/{id}")
    public ResponseEntity<List<TransactionResponseDto>> findTransactionsByOrigin(@PathVariable("id") Long id) {
        List<TransactionResponseDto> transactionsDto = transactionService.findTransactionsByOrigin(id);
        return new ResponseEntity<>(transactionsDto, HttpStatus.OK);
    }}