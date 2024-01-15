package com.gbx.gbxchallengejava.service;

import com.gbx.gbxchallengejava.dto.TransactionRequestDto;
import com.gbx.gbxchallengejava.dto.TransactionResponseDto;
import com.gbx.gbxchallengejava.orm.Transaction;
import com.gbx.gbxchallengejava.orm.User;
import com.gbx.gbxchallengejava.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CrudTransactionServiceTest {
    @Mock
    private TransactionRepository transactionRepository;
    @Mock
    private UserService crudUserService;
    @InjectMocks
    private TransactionService transactionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void shouldCreateTransactionTest() {
        User origin = new User();
        origin.setId(1L);

        User destination = new User();
        destination.setId(2L);

        when(crudUserService.findUserById(1L)).thenReturn(origin);
        when(crudUserService.findUserById(2L)).thenReturn(destination);

        TransactionRequestDto transactionRequestDto = TransactionRequestDto.builder()
                .userOriginId("1")
                .userDestinationId("2")
                .value("100.00").build();

        assertDoesNotThrow(() -> transactionService.createTransaction(transactionRequestDto));

        verify(transactionRepository, times(1)).save(argThat(transaction ->
                transaction.getOrigin().getId().equals(1L) &&
                        transaction.getDestination().getId().equals(2L) &&
                        transaction.getValue().equals(new BigDecimal("100.00"))));
    }

    @Test
    void shouldThrowExceptionOnNotCreateTransactionTest() {
        TransactionRequestDto transactionRequestDto = TransactionRequestDto.builder()
                .userOriginId("1")
                .userDestinationId("2")
                .value("100.00")
                .build();

        when(crudUserService.findUserById(Mockito.anyLong())).thenThrow(RuntimeException.class);
        assertThrows(RuntimeException.class, () -> transactionService.createTransaction(transactionRequestDto));
    }

    @Test
    public void shouldListAllTransactionsTest() {
        Transaction transaction = new Transaction();

        when(transactionRepository.findAll()).thenReturn(Collections.singletonList(transaction));
        List<TransactionResponseDto> result = transactionService.listAllTransactions();
        assertEquals(1, result.size());
    }

    @Test
    void shouldThrowExceptionOnNotListTransactionsTest() {
        TransactionResponseDto transactionResponseDto = new TransactionResponseDto();
        transactionResponseDto.setUserOriginId("1");
        transactionResponseDto.setUserDestinationId("2");
        transactionResponseDto.setValue("100.00");

        when(transactionRepository.findAll()).thenThrow(RuntimeException.class);
        assertThrows(RuntimeException.class, () -> transactionService.listAllTransactions());
    }

    @Test
    void shouldFindTransactionsByDestinationTest() {
        Long destinationUserId = 1L;
        Transaction transaction = new Transaction();
        when(transactionRepository.findByDestination(destinationUserId)).thenReturn(Collections.singletonList(transaction));
        List<TransactionResponseDto> result = transactionService.findTransactionsByDestination(destinationUserId);
        assertEquals(1, result.size());
    }

    @Test
    void shouldThrowExceptionWhenNotFindTransactionsByDestinationTest() {
        Long destinationUserId = 1L;
        when(transactionRepository.findByDestination(Mockito.anyLong())).thenThrow(RuntimeException.class);
        assertThrows(RuntimeException.class, () -> transactionService.findTransactionsByDestination(destinationUserId));
    }

    @Test
    void shouldFindTransactionsByOriginTest() {
        Long destinationUserId = 1L;
        Transaction transaction = new Transaction();
        when(transactionRepository.findByOrigin(destinationUserId)).thenReturn(Collections.singletonList(transaction));
        List<TransactionResponseDto> result = transactionService.findTransactionsByOrigin(destinationUserId);
        assertEquals(1, result.size());
    }

    @Test
    void shouldThrowExceptionWhenNotFindTransactionsByOriginTest() {
        Long destinationUserId = 1L;
        when(transactionRepository.findByOrigin(Mockito.anyLong())).thenThrow(RuntimeException.class);
        assertThrows(RuntimeException.class, () -> transactionService.findTransactionsByOrigin(destinationUserId));
    }
}