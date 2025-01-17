package com.nrtl.pizza.service;

import com.nrtl.pizza.dto.PizzaOrderSummaryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReportingService {

	public PizzaOrderSummaryDto getSummary() {
		// TASK_09 Assigment
		return new PizzaOrderSummaryDto();
	}

}
