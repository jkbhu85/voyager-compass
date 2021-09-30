package com.jk.vc.exception;

public class VcDataAccessException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public VcDataAccessException() {
		super();
	}

	public VcDataAccessException(String message, Throwable cause) {
		super(message, cause);
	}

	public VcDataAccessException(String message) {
		super(message);
	}

	public VcDataAccessException(Throwable cause) {
		super(cause);
	}

}
