package com.nrtl.pizza.controller;

import com.nrtl.pizza.dto.OrderDto;
import com.nrtl.pizza.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping("/{id}")
    public OrderDto getOrder(@PathVariable final Integer id) {
        return orderService.getById(id);
    }

    @GetMapping("/list")
    public List<OrderDto> getOrders(@RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return orderService.getAllClientOrders(pageable);
    }

    @PostMapping("/find-by-address")
    public List<OrderDto> findOrderByAddress(@RequestBody final String address) {
        return orderService.findOrderByAddress(address);
    }

    @PostMapping
    public ResponseEntity<OrderDto> addOrder(@RequestBody final OrderDto order) {
        final OrderDto createdOrder = orderService.saveOrder(order);
        return ResponseEntity.ok(createdOrder);
    }
}
