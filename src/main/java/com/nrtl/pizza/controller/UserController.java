package com.nrtl.pizza.controller;

import com.nrtl.pizza.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/user")
@RequiredArgsConstructor
public class UserController {
	private final UserService userService;

	@PostMapping("/disable")
	public ResponseEntity<Void> disableUser(@RequestBody final Long userId) {
		return ResponseEntity.noContent().build();
	}
}
