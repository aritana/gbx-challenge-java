package com.gbx.gbxchallengejava.service;

import com.gbx.gbxchallengejava.dto.TransactionRequestDto;
import com.gbx.gbxchallengejava.dto.TransactionResponseDto;
import com.gbx.gbxchallengejava.exception.TransactionNotFoundException;
import com.gbx.gbxchallengejava.orm.Transaction;
import com.gbx.gbxchallengejava.orm.User;
import com.gbx.gbxchallengejava.repository.TransactionRepository;
import com.gbx.gbxchallengejava.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    UserService userService;

    public Transaction createTransaction(TransactionRequestDto transactionRequestDto) {
        try {
            User origin = userService.findUserById(Long.parseLong(transactionRequestDto.getUserOriginId()));
            User destination = userService.findUserById(Long.parseLong(transactionRequestDto.getUserDestinationId()));

            Transaction transaction = Transaction.builder()
                    .origin(origin)
                    .destination(destination)
                    .value(new BigDecimal(transactionRequestDto.getValue()))
                    .date(LocalDateTime.now())
                    .build();
            return transactionRepository.save(transaction);

        } catch (DataAccessException e) {
            e.printStackTrace();
            throw new TransactionNotFoundException("Not possible to create Transaction", e);
        }
    }
    public List<TransactionResponseDto> listAllTransactions() {
        try {
            Iterable<Transaction> transactionsIterable = transactionRepository.findAll();
            return StreamSupport.stream(transactionsIterable.spliterator(), false)
                    .map(TransactionResponseDto::convertToDto)
                    .collect(Collectors.toList());
        } catch (DataAccessException e) {
            e.printStackTrace();
            throw new TransactionNotFoundException("Not possible to list Transactions", e);
        }
    }
    public List<TransactionResponseDto> findTransactionsByDestination(Long id) {
        try {
            List<Transaction> transactions = transactionRepository.findByDestination(id);
            return TransactionResponseDto.convertToDtoList(transactions);

        } catch (DataAccessException e) {
            e.printStackTrace();
            throw new TransactionNotFoundException("Not possible to find Transaction", e);
        }
    }

    public List<TransactionResponseDto> findTransactionsByOrigin(Long id) {
        try {
            List<Transaction> transactions = transactionRepository.findByOrigin(id);
            return TransactionResponseDto.convertToDtoList(transactions);

        } catch (DataAccessException e) {
            e.printStackTrace();
            throw new TransactionNotFoundException("Not possible to find Transaction", e);
        }
    }
}