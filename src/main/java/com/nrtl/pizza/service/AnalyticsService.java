package com.nrtl.pizza.service;

import com.nrtl.pizza.domain.UserEntity;
import com.nrtl.pizza.dto.UserAnalyticsDto;
import com.nrtl.pizza.repository.UserRepository;
import com.nrtl.pizza.security.SpringSecuritySecurityContextProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnalyticsService {

	private final UserRepository userRepository;
	private final SpringSecuritySecurityContextProvider securityContextProvider;

	public UserAnalyticsDto calculateUserAnalytics() {
		final UserEntity userEntity = getUserEntity();
		final int id = userEntity.getId();

		return UserAnalyticsDto.builder()
				.userId(id)
				.build();
	}

	private UserEntity getUserEntity() {
		final String username = securityContextProvider.getUser().getUsername();
		return userRepository.findByUsername(username);
	}
}
