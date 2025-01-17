package com.nrtl.pizza.repository;

import com.nrtl.pizza.domain.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserEntity, Integer> {

	UserEntity findByUsername(String name);

}
