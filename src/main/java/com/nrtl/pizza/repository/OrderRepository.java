package com.nrtl.pizza.repository;

import com.nrtl.pizza.domain.OrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends CrudRepository<OrderEntity, Integer> {

	@EntityGraph(attributePaths = {"pizzas"})
	@Query("SELECT o from OrderEntity o WHERE o.client.username=:username ORDER BY o.id DESC")
	Page<OrderEntity> findOrdersByClientUsername(@Param("username") String username, Pageable pageable);

	@Query("SELECT o from OrderEntity o WHERE LOWER(o.address) LIKE %:address% ORDER BY o.id DESC")
	List<OrderEntity> findOrderByAddress(@Param("address") String address);

	@Query("SELECT o from OrderEntity o WHERE o.client.id=:id ORDER BY o.id DESC")
	List<OrderEntity> findOrdersByClientId(@Param("id") Integer id);

	@Query("SELECT o from OrderEntity o ORDER BY o.id DESC")
	List<OrderEntity> findAllOrders();

	@Query("SELECT o FROM OrderEntity o WHERE o.dateCreated BETWEEN :startOfCurrentWeek AND :now")
	List<OrderEntity> findOrdersFromThisWeek(@Param("startOfCurrentWeek") LocalDateTime startOfCurrentWeek,
											 @Param("now") LocalDateTime now);

}
