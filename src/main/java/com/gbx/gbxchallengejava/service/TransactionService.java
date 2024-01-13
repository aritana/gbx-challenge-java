package com.gbx.gbxchallengejava.service;

import com.gbx.gbxchallengejava.dto.TransactionDto;
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

    public void createTransaction(TransactionDto transactionDto) {
        try {

            User origin = userService.findUserById(Long.parseLong(transactionDto.getUserOriginId()));
            User destination = userService.findUserById(Long.parseLong(transactionDto.getUserDestinationId()));

            Transaction transaction = Transaction.builder()
                    .origin(origin)
                    .destination(destination)
                    .value(new BigDecimal(transactionDto.getValue()))
                    .date(LocalDateTime.now())
                    .build();
            transactionRepository.save(transaction);

        } catch (DataAccessException e) {
            e.printStackTrace();
            throw new TransactionNotFoundException("Not possible to create Transaction", e);
        }
    }
    public List<TransactionDto> listAllTransactions() {
        try {
            Iterable<Transaction> transactionsIterable = transactionRepository.findAll();
            return StreamSupport.stream(transactionsIterable.spliterator(), false)
                    .map(TransactionDto::convertToDto)
                    .collect(Collectors.toList());
        } catch (DataAccessException e) {
            e.printStackTrace();
            throw new TransactionNotFoundException("Not possible to list Transactions", e);
        }
    }
    public List<TransactionDto> findTransactionsByDestination(Long id) {
        try {
            List<Transaction> transactions = transactionRepository.findByDestination(id);
            return TransactionDto.convertToDtoList(transactions);

        } catch (DataAccessException e) {
            e.printStackTrace();
            throw new TransactionNotFoundException("Not possible to find Transaction", e);
        }
    }

    public List<TransactionDto> findTransactionsByOrigin(Long id) {
        try {
            List<Transaction> transactions = transactionRepository.findByOrigin(id);
            return TransactionDto.convertToDtoList(transactions);

        } catch (DataAccessException e) {
            e.printStackTrace();
            throw new TransactionNotFoundException("Not possible to find Transaction", e);
        }
    }
}