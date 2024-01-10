package com.gbx.gbxchallengejava.repository;

import com.gbx.gbxchallengejava.orm.Transaction;
import org.springframework.data.repository.CrudRepository;

public interface TransactionRepository extends CrudRepository<Transaction, Long> {
}