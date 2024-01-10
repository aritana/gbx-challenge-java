package com.gbx.gbxchallengejava.repository;

import com.gbx.gbxchallengejava.orm.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}
