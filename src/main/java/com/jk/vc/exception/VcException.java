package com.jk.vc.exception;

public class VcException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public VcException() {
		super();
	}

	public VcException(String message, Throwable cause) {
		super(message, cause);
	}

	public VcException(String message) {
		super(message);
	}

	public VcException(Throwable cause) {
		super(cause);
	}

}
