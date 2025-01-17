package com.nrtl.pizza.repository;

import com.nrtl.pizza.domain.OrderEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends CrudRepository<OrderEntity, Integer> {

	@Query("SELECT o from OrderEntity o WHERE o.client.username=:username ORDER BY o.id DESC")
	List<OrderEntity> findOrdersByClientId(@Param("username") String username);

	@Query("SELECT o from OrderEntity o WHERE LOWER(o.address) LIKE %:address% ORDER BY o.id DESC")
	List<OrderEntity> findOrderByAddress(@Param("address") String address);

}
