package com.nrtl.pizza.controller;

import com.nrtl.pizza.dto.UserAnalyticsDto;
import com.nrtl.pizza.service.AnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/analytics")
@RequiredArgsConstructor
public class AnalyticsController {

	private final AnalyticsService analyticsService;

	@GetMapping("/user")
	public UserAnalyticsDto getUserAnalytics() {
		return analyticsService.calculateUserAnalytics();
	}
}
