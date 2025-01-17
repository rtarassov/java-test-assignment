package com.nrtl.pizza.exception;

public class EntityNotFoundException extends RuntimeException {

	public EntityNotFoundException() {
		super();
	}

	public EntityNotFoundException(final Object identifier) {
		super("Entity with identifier [" + identifier + "] was not found.");
	}
}
