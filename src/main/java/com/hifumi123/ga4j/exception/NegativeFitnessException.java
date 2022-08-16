package com.hifumi123.ga4j.exception;

public class NegativeFitnessException extends Exception {

	private static final long serialVersionUID = 4468654664232539916L;

	public NegativeFitnessException() {
		super();
	}

	public NegativeFitnessException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public NegativeFitnessException(String message, Throwable cause) {
		super(message, cause);
	}

	public NegativeFitnessException(String message) {
		super(message);
	}

	public NegativeFitnessException(Throwable cause) {
		super(cause);
	}
}
