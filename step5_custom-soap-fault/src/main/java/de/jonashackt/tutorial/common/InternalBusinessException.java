package de.jonashackt.tutorial.common;

public class InternalBusinessException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public InternalBusinessException(Throwable cause) {
		super(cause);
	}

	public InternalBusinessException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public InternalBusinessException(String message) {
		super(message);
	}

}
