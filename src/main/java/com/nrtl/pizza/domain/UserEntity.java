package com.nrtl.pizza.domain;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name = "user")
@RequiredArgsConstructor
public class UserEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "username", nullable = false)
	private String username;

	@Column(name = "enabled", nullable = false)
	private boolean enabled;

	@Column(name = "is_admin", nullable = false)
	private boolean admin;

	@Column(name = "password", nullable = false)
	private String password;

}
