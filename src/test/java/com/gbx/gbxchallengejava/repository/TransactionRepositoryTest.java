package com.gbx.gbxchallengejava.repository;

import com.gbx.gbxchallengejava.orm.Transaction;
import com.gbx.gbxchallengejava.orm.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.PropertySource;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@PropertySource("classpath:application-test.properties")
class TransactionRepositoryTest {

    private User user1;
    private User user2;
    private Transaction transaction1;
    private Transaction transaction2;
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserRepository userRepository;


    @BeforeEach
    void setUp() {
        user1 = User.builder()
                .name("Machado de Assis")
                .accountNumber(123456)
                .balance(new BigDecimal(100))
                .build();

        user2 = User.builder()
                .name("Riobaldo Tatarana")
                .accountNumber(123457)
                .balance(new BigDecimal(200))
                .build();

        userRepository.save(user1);
        userRepository.save(user2);
    }

    @AfterEach
    void tearDown() {
        transactionRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void shouldCreateTransactionTest() {
        Transaction transaction = Transaction.builder()
                .origin(user1)
                .destination(user2)
                .value(new BigDecimal(100))
                .date(LocalDateTime.now())
                .build();
        Transaction persistedTransaction = transactionRepository.save(transaction);
        assertNotNull(persistedTransaction.getId());
    }

    @Test
    public void shouldFindTransactionOriginByUserTest() {
        createTransactions();
        transactionRepository.saveAll(List.of(transaction1, transaction2));

        // Find transactions for user1
        List<Transaction> transactionsForUser1 = transactionRepository.findByOrigin(user1.getId());
        assertEquals(2, transactionsForUser1.size());
    }

    @Test
    public void shouldFindTransactionDestinationByUserTest() {
        createTransactions();
        transactionRepository.saveAll(List.of(transaction1, transaction2));

        // Find transactions for user1
        List<Transaction> transactionsForUser2 = transactionRepository.findByDestination(user2.getId());
        assertEquals(2, transactionsForUser2.size());
    }

    public void createTransactions() {
        transaction1 = Transaction.builder()
                .origin(user1)
                .destination(user2)
                .value(new BigDecimal(100))
                .date(LocalDateTime.now())
                .build();

        transaction2 = Transaction.builder()
                .origin(user1)
                .destination(user2)
                .value(new BigDecimal(200))
                .date(LocalDateTime.now())
                .build();
    }
}