package com.nrtl.pizza.controller;

import com.nrtl.pizza.dto.PizzaOrderSummaryDto;
import com.nrtl.pizza.service.ReportingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/reports")
@RequiredArgsConstructor
public class ReportingController {

	private final ReportingService reportingService;

	@GetMapping("/weekly-pizza-orders")
	public PizzaOrderSummaryDto getWeeklyPizzaOrders() {
		return reportingService.getSummary();
	}

}
