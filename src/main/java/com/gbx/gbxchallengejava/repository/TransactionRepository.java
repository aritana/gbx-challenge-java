package com.gbx.gbxchallengejava.repository;

import com.gbx.gbxchallengejava.orm.Transaction;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Long> {

    @Query(value = "SELECT * FROM transacoes t WHERE t.\"user-origin-id\"= :userId", nativeQuery = true)
    List<Transaction> findByOrigin(@Param("userId") Long userId);

    @Query(value = "SELECT * FROM transacoes t WHERE t.\"user-destination-id\"= :userId", nativeQuery = true)
    List<Transaction> findByDestination(@Param("userId") Long userId);
}